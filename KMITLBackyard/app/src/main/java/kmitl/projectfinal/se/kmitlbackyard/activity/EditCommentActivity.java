package kmitl.projectfinal.se.kmitlbackyard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;

import kmitl.projectfinal.se.kmitlbackyard.R;

public class EditCommentActivity extends Activity {

    private EditText oldComment;
    private Button editBtn, cancelBtn, backBtn;
    private String key_comment, title_comment, post_id;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);

        oldComment = findViewById(R.id.old_comment);
        editBtn = findViewById(R.id.edit_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
        backBtn = findViewById(R.id.close_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            key_comment = bundle.getString("key_comment");
            title_comment = bundle.getString("title_comment");
            post_id = bundle.getString("post_id");
        }

        oldComment.setText(title_comment);
        oldComment.setSelection(oldComment.length());
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCommentActivity.this, CommentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                HashMap<String, Object> result = new HashMap<>();
                result.put("content", oldComment.getText().toString());
                mDatabase.child("comment").child(post_id).child(key_comment).updateChildren(result);
                MDToast mdToast = MDToast.makeText(getApplicationContext(), "คอมเมนต์ถูกแก้ไขแล้ว", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                mdToast.show();
                intent.putExtra("post_id", post_id);
                startActivity(intent);
                finish();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
