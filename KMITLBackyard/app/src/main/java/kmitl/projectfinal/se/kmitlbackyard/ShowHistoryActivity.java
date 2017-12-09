package kmitl.projectfinal.se.kmitlbackyard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowHistoryActivity extends AppCompatActivity {
    private RecyclerView recycler_history;
    private DatabaseReference mDatabase;
    private List<PostModel> listPosts;
    private RecyclerView.Adapter adapter;
    private String uid;
    private String user_key;
    private CircleImageView history_img_profile;
    private CustomTextView history_nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
//        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("uid"), Toast.LENGTH_SHORT).show();
        uid = getIntent().getStringExtra("uid");
        user_key = getIntent().getStringExtra("user_key");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recycler_history = findViewById(R.id.recycler_history);
        recycler_history.setLayoutManager(new LinearLayoutManager(this));
        listPosts = new ArrayList<>();
        history_img_profile = findViewById(R.id.history_img_profile);
        history_nickname = findViewById(R.id.history_nickname);
        queryPost(uid);
        queryUserInfo(user_key);
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
                    String email = userData.get("email").toString();
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
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String post_id = dataSnapshot.getKey();
                PostModel postItem = new PostModel(
                        newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                        newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                        newPost.get("uid").toString(), post_id , newPost.get("post_liked").toString(), newPost.get("viewer").toString(),
                        newPost.get("user_key").toString(), "");
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
    }
}
