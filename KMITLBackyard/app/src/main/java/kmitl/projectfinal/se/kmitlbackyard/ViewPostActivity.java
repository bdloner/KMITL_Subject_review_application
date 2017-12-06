package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class ViewPostActivity extends AppCompatActivity {

    private Button addComment;
    private CustomTextView post_nickname;
    private CustomTextView post_title;
    private CustomTextView post_subject;
    private CustomTextView post_desc;
    private RatingBar post_rating;
    private CustomTextView post_date;
    private String post_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addComment = findViewById(R.id.add_comment);
        post_nickname = findViewById(R.id.post_nickname);
        post_title = findViewById(R.id.post_title);
        post_subject = findViewById(R.id.post_subject);
        post_desc = findViewById(R.id.post_desc);
        post_rating = findViewById(R.id.post_rating);
        post_date = findViewById(R.id.post_date);

        post_nickname.setText(getIntent().getStringExtra("post_nickname"));
        post_title.setText(getIntent().getStringExtra("post_title"));
        post_subject.setText(getIntent().getStringExtra("post_subject"));
        post_desc.setText(getIntent().getStringExtra("post_desc"));
        post_rating.setRating(Float.parseFloat(getIntent().getStringExtra("post_rating")));
        post_date.setText(getIntent().getStringExtra("post_date"));
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            post_id = bundle.getString("post_id");
        }
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
            }
        });
    }
}
