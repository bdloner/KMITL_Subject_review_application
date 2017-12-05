package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectPostActivity extends AppCompatActivity {

    private Button writeReviewBtn;

    private String subjectSelect = "";
    private String uid = "";
    DatabaseReference databaseReference;
    private TextView post_nickname;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        writeReviewBtn = findViewById(R.id.write_review_btn);

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
                startActivity(intent);
            }
        });
    }
}
