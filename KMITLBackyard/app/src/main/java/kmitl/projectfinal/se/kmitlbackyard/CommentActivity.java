package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CommentActivity extends Activity{

    private Button closeBtn, blockSend, avaSend;
    private EditText addComment;
    private  String value, timeStamp, post_id ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        closeBtn = findViewById(R.id.close_btn);
        blockSend = findViewById(R.id.block_send);
        avaSend = findViewById(R.id.ava_send);
        addComment = findViewById(R.id.add_comment);
        timeStamp = getCurrentTime();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            post_id = bundle.getString("post_id");
        }
        addComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                 value = s.toString();

                if (value.equals("")) {
                    blockSend.setVisibility(View.VISIBLE);
                    avaSend.setVisibility(View.INVISIBLE);

                } else {
                    avaSend.setVisibility(View.VISIBLE);
                    blockSend.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count){

            }

        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void sendComment(View view) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("content", value);
        result.put("post_id", post_id);
        result.put("timeStamp", timeStamp);
        result.put("uid", user.getDisplayName());
        mDatabase.child("comment").push().setValue(result);
        value = "";
        addComment.setText("");
    }
    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
