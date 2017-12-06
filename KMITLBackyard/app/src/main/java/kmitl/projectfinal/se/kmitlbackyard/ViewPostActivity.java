package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewPostActivity extends AppCompatActivity {

    private Button addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addComment = findViewById(R.id.add_comment);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                startActivity(intent);
            }
        });
    }
}
