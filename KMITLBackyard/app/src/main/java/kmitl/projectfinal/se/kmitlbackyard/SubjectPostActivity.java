package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPostActivity extends AppCompatActivity {

    private Button writeReviewBtn;

    private String subjectSelect = "";
    private String uid = "";
    DatabaseReference databaseReference;
    private TextView post_nickname;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private List<PostModel> listPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        writeReviewBtn = findViewById(R.id.write_review_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.list_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listPosts = new ArrayList<>();
        queryData();


        post_nickname = findViewById(R.id.post_nickname);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        post_nickname.setText(user.getDisplayName());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        subjectSelect = getIntent().getStringExtra("subjectSelect");
        Toast.makeText(getApplicationContext(), subjectSelect, Toast.LENGTH_SHORT).show();

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("subjectSelect", subjectSelect);
                startActivity(intent);
                finish();
            }
        });


    }

    private void queryData() {
        Query query = mDatabase.child("post");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String post_id = dataSnapshot.getKey();
                PostModel postItem = new PostModel(
                        newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                        newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                        newPost.get("uid").toString(), post_id

                );
                if(newPost.get("subject_id").toString().equals(subjectSelect)){ // ดึงเลขวิชาจากหน้าที่กดมา แล้วดักว่าจะโชววิชาอะไร ตรงนี้นะ
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
    }
}
