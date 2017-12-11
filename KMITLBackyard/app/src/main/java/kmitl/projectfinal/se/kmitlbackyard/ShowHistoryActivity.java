package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowHistoryActivity extends Activity {
    private RecyclerView recycler_history;
    private DatabaseReference mDatabase;
    private List<PostModel> listPosts;
    private RecyclerView.Adapter adapter;
    private String uid;
    private String user_key;
    private CircleImageView history_img_profile;
    private CustomTextView history_nickname;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        uid = getIntent().getStringExtra("uid");
        user_key = getIntent().getStringExtra("user_key");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recycler_history = findViewById(R.id.recycler_history);
        recycler_history.setLayoutManager(new LinearLayoutManager(this));
        listPosts = new ArrayList<>();
        history_img_profile = findViewById(R.id.history_img_profile);
        history_nickname = findViewById(R.id.history_nickname);
        backBtn = findViewById(R.id.back_btn);

        queryPost(uid);
        queryUserInfo(user_key);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void queryUserInfo(final String uid) {
        Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();

                if(dataSnapshot.getKey().equals(uid)){
                    String profileImgLink = userData.get("profileImgLink").toString();
                    String nick = userData.get("nickname").toString();
                    if (profileImgLink!=null || !profileImgLink.equals("null") || !profileImgLink.equals("")){
                        Picasso.with(getApplicationContext()).load(profileImgLink).fit().centerCrop().into(history_img_profile);
                    }
                    history_nickname.setText(nick);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
        }

    private void queryPost(final String uid) {
        Query query = mDatabase.child("post");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPosts.clear();
                Query query1 = mDatabase.child("post");
                query1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        String post_id = dataSnapshot.getKey();
                        PostModel postItem = new PostModel(
                                newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                                newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                                newPost.get("uid").toString(), post_id , newPost.get("user_key").toString(), "","history");
                        if(newPost.get("uid").toString().equals(uid)){
                            listPosts.add(0, postItem);
                            adapter = new PostAdapter(listPosts, getApplication());
                            recycler_history.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                adapter = new PostAdapter(listPosts, getApplication());
                recycler_history.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
