package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by barjord on 12/10/2017 AD.
 */

public class EditTitleReviewActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private Button addImgBtn, showMore;
    private FrameLayout frameLayout;
    private CustomTextView rating_post;
    private CustomTextView desc_post;
    private EditText post_title;
    private static final int CHOOSE_IMAGE = 101;
    private DatabaseReference mDatabase;
    private String post_id;
    HashMap<String, Object> result = new HashMap<>();
    String subject_id;
    Uri uriUploadImage;
    String postImgLink;
    String mtitle;
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
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            mtitle = bundle.getString("post_title");
            post_id = bundle.getString("post_id");
        }
        post_title.setText(mtitle);
        post_title.setSelection(post_title.length());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                showMore.setVisibility(View.INVISIBLE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("แก้ไขโพสรีวิววิชาเรียน");
        rating_post.setText(getIntent().getStringExtra("rating"));
        desc_post.setText(getIntent().getStringExtra("comment"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_edit, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_post:
                Intent intent = new Intent(this, SubjectPostActivity.class);
                
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //add post database
                if(post_title.getText().toString().equals("")){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกหัวข้อการรีวิว", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    break;
                }

                //add post database
                String score = getIntent().getStringExtra("rating");
                String description = getIntent().getStringExtra("comment");
                String title = this.post_title.getText().toString();

                result.put("score", score);
                result.put("description", description);
                result.put("title", title);
                //Log.i("sldfmksdkmfsdf",result+"");
                mDatabase.child("post").child(post_id).updateChildren(result);
                intent.putExtra("subjectSelect", subject_id);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

}

