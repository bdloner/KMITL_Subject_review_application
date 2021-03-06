package kmitl.projectfinal.se.kmitlbackyard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;

import com.valdesekamdem.library.mdtoast.MDToast;

import kmitl.projectfinal.se.kmitlbackyard.R;

public class EditPostActivity extends AppCompatActivity {

    private EditText comment;
    private RatingBar ratingBar;
    private String post_id, num_star, mTitle, mSubject, mDesc, mDate, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("แก้ไขโพส");

        Bundle bundle =getIntent().getExtras();
        if (bundle != null){
            post_id = bundle.getString("post_id");
            num_star = bundle.getString("post_rating");
            mTitle = bundle.getString("post_title");
            mSubject = bundle.getString("post_subject");
            mDesc = bundle.getString("post_desc");
            mDate = bundle.getString("post_date");
            type = bundle.getString("type");
        }

        comment = findViewById(R.id.comment);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(num_star));
        comment.setText(mDesc);
        comment.setSelection(comment.length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_next:
                if(comment.getText().toString().equals("")){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลการรีวิว", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    break;
                }
                Intent intent = new Intent(this, EditTitleReviewActivity.class);
                intent.putExtra("comment", comment.getText().toString());
                intent.putExtra("rating", String.valueOf(ratingBar.getRating()));
                intent.putExtra("subjectSelect", getIntent().getStringExtra("subjectSelect"));
                intent.putExtra("post_title", mTitle);
                intent.putExtra("post_id", post_id);
                intent.putExtra("type", type);
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


