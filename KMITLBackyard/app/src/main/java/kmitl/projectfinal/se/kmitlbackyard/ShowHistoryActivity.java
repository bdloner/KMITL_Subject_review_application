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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowHistoryActivity extends AppCompatActivity {
    private RecyclerView recycler_history;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private List<PostModel> listPosts;
    private RecyclerView.Adapter adapter;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("uid"), Toast.LENGTH_SHORT).show();
        uid = getIntent().getStringExtra("uid");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recycler_history = findViewById(R.id.recycler_history);
        recycler_history.setLayoutManager(new LinearLayoutManager(this));
        listPosts = new ArrayList<>();
        queryData(uid);
    }

    private void queryData(final String uid) {
        Query query = mDatabase.child("post");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String post_id = dataSnapshot.getKey();
                PostModel postItem = new PostModel(
                        newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                        newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                        newPost.get("uid").toString(), post_id , newPost.get("post_liked").toString(), newPost.get("viewer").toString());
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
