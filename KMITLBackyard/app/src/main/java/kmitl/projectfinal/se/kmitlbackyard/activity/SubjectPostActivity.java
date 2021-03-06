package kmitl.projectfinal.se.kmitlbackyard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.projectfinal.se.kmitlbackyard.view.PostAdapter;
import kmitl.projectfinal.se.kmitlbackyard.model.PostModel;
import kmitl.projectfinal.se.kmitlbackyard.R;

public class SubjectPostActivity extends AppCompatActivity {

    private Button writeReviewBtn;

    private String subjectSelect = "";
    private DatabaseReference databaseReference;
    private TextView post_nickname;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private List<PostModel> listPosts;
    private CircleImageView img_nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        writeReviewBtn = findViewById(R.id.write_review_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.list_post);
        img_nickname = findViewById(R.id.img_nickname);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listPosts = new ArrayList<>();
        queryData();


        post_nickname = findViewById(R.id.post_nickname);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        post_nickname.setText(user.getDisplayName());
        if(user.getPhotoUrl()!=null){
            Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).fit().centerCrop().into(img_nickname);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        subjectSelect = getIntent().getStringExtra("subjectSelect");
        MDToast mdToast = MDToast.makeText(getApplicationContext(), subjectSelect, MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
        mdToast.show();


        checkWriteReviewBtn();
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("subjectSelect", subjectSelect);
                startActivity(intent);
            }
        });

        img_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectPostActivity.this, ShowHistoryActivity.class);
                intent.putExtra("uid", user.getDisplayName());
                intent.putExtra("user_key", user.getUid());
                startActivity(intent);
            }
        });

        post_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectPostActivity.this, ShowHistoryActivity.class);
                intent.putExtra("uid", user.getDisplayName());
                intent.putExtra("user_key", user.getUid());
                startActivity(intent);
            }
        });
    }

    private void checkWriteReviewBtn() {
        Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> dataset = (Map<String, Object>) dataSnapshot.getValue();
                if(dataSnapshot.getKey().equals(user.getUid())){
                    if (dataset.get("role").toString().equals("student")){
                        writeReviewBtn.setVisibility(View.VISIBLE);
                    }
                    else if (dataset.get("role").toString().equals("teacher")){
                        writeReviewBtn.setVisibility(View.INVISIBLE);
                    }
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

    private void queryData() {
        Query query = mDatabase.child("post");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPosts.clear();
                Query query00 = mDatabase.child("post");
                query00.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        String post_id = dataSnapshot.getKey();
                        PostModel postItem = new PostModel(
                                newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                                newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                                newPost.get("uid").toString(), post_id, newPost.get("user_key").toString(),subjectSelect, "subjectpost");
                        if(newPost.get("subject_id").toString().equals(subjectSelect)){
                            listPosts.add(0, postItem);
                            adapter = new PostAdapter(listPosts, getApplication());
                            recyclerView.setAdapter(adapter);
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
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
