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

public class WriteReviewActivity extends AppCompatActivity {

    private EditText comment;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("เขียนรีวิววิชาเรียน");

        comment = findViewById(R.id.comment);
        ratingBar = findViewById(R.id.ratingBar);
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
                Intent intent = new Intent(this, AddTitleReviewActivity.class);

                intent.putExtra("comment", comment.getText().toString());
                intent.putExtra("rating", String.valueOf(ratingBar.getRating()));
                intent.putExtra("subjectSelect", getIntent().getStringExtra("subjectSelect"));
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
