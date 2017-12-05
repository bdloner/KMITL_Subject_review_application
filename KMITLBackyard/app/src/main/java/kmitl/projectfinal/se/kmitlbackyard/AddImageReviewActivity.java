package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class AddImageReviewActivity extends AppCompatActivity {

    private Button addImgBtn, showMore;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_review);

        addImgBtn = findViewById(R.id.add_img_btn);
        showMore = findViewById(R.id.show_more);
        frameLayout = findViewById(R.id.frameLayout);

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                showMore.setVisibility(View.INVISIBLE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("เขียนรีวิววิชาเรียน");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_post:
                Intent intent = new Intent(this, SubjectPostActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
