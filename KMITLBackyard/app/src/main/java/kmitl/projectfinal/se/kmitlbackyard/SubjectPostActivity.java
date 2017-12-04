package kmitl.projectfinal.se.kmitlbackyard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectPostActivity extends AppCompatActivity {

    private String subjectSelect = "";
    private String uid = "";
    DatabaseReference databaseReference;
    private TextView post_nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);

        post_nickname = findViewById(R.id.post_nickname);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        subjectSelect = getIntent().getStringExtra("subjectSelect");
        uid = getIntent().getStringExtra("uid");
        Toast.makeText(getApplicationContext(), subjectSelect+ " " +uid, Toast.LENGTH_SHORT).show();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> children = dataSnapshot.child("user").child(uid).getChildren();
//                for (DataSnapshot child: children){
//                    if(child.getKey().equals("nickname")){
//                        post_nickname.setText(String.valueOf(child.getValue()));
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
