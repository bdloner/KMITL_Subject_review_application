package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CommentActivity extends Activity{

    private Button closeBtn, blockSend, avaSend;
    private EditText addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        closeBtn = findViewById(R.id.close_btn);
        blockSend = findViewById(R.id.block_send);
        avaSend = findViewById(R.id.ava_send);
        addComment = findViewById(R.id.add_comment);

        addComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();

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
}
