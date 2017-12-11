package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddTitleReviewActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button showMore;
    private FrameLayout frameLayout;
    private CustomTextView rating_post;
    private CustomTextView desc_post;
    private EditText post_title;
    private DatabaseReference mDatabase;
    private String key;
    private HashMap<String, Object> result = new HashMap<>();
    private String subject_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title_review);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        showMore = findViewById(R.id.show_more);
        frameLayout = findViewById(R.id.frameLayout);
        rating_post = findViewById(R.id.rating_post);
        desc_post = findViewById(R.id.desc_post);
        post_title= findViewById(R.id.post_title);
        subject_id = getIntent().getStringExtra("subjectSelect");


        mDatabase = FirebaseDatabase.getInstance().getReference();
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                showMore.setVisibility(View.INVISIBLE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("เขียนรีวิววิชาเรียน");
        rating_post.setText(getIntent().getStringExtra("rating"));
        desc_post.setText(getIntent().getStringExtra("comment"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_post:
                Intent intent = new Intent(this, SubjectPostActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if(this.post_title.getText().toString().equals("")){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกหัวข้อการรีวิว", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    break;
                }

                addPostDatabase();

                intent.putExtra("subjectSelect", subject_id);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void addPostDatabase() {
        key = mDatabase.push().getKey();

        String score = getIntent().getStringExtra("rating");
        String description = getIntent().getStringExtra("comment");
        String title = this.post_title.getText().toString();
        String uid = user.getDisplayName();
        String timeStamp = getCurrentTime();
        String score_num = "0";

        result.put("score", score);
        result.put("description", description);
        result.put("title", title);
        result.put("uid", uid);
        result.put("subject_id", subject_id);
        result.put("timeStamp", timeStamp);
        result.put("score_num", score_num);
        result.put("user_key", user.getUid());
        mDatabase.child("post").child(key).setValue(result);
        HashMap<String, Object> result1 = new HashMap<>();
        result1.put("viewer", "0");
        mDatabase.child("view").child(key).setValue(result1);
    }

}
