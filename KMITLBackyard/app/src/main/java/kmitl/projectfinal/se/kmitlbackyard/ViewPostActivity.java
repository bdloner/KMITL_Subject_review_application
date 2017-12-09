package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPostActivity extends AppCompatActivity {

    private Button addComment;
    private CustomTextView post_nickname;
    private CustomTextView post_title;
    private CustomTextView post_subject;
    private CustomTextView post_desc;
    private RatingBar post_rating;
    private CustomTextView post_date;
    private CircleImageView image_icon;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private Button edit_post, delete_post;
    private String post_id, post_img, subject_id;
    private Context context;
    private String num_star, mnickname, mtitle, msubject, mdesc, mdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        addComment = findViewById(R.id.add_comment);
        post_nickname = findViewById(R.id.post_nickname);
        post_title = findViewById(R.id.post_title);
        post_subject = findViewById(R.id.post_subject);
        post_desc = findViewById(R.id.post_desc);
        post_rating = findViewById(R.id.post_rating);
        post_date = findViewById(R.id.post_date);
        image_icon = findViewById(R.id.image_icon);
        edit_post = findViewById(R.id.edit_post);
        delete_post = findViewById(R.id.delete_post);

        subject_id = getIntent().getStringExtra("subjectSelect");

        if(user.getUid().equals(getIntent().getStringExtra("user_key"))){
            edit_post.setVisibility(View.VISIBLE);
            delete_post.setVisibility(View.VISIBLE);
        }
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            post_id = bundle.getString("post_id");
            num_star = bundle.getString("post_rating");
            mnickname = bundle.getString("post_nickname");
            mtitle = bundle.getString("post_title");
            msubject = bundle.getString("post_subject");
            mdesc = bundle.getString("post_desc");
            mdate = bundle.getString("post_date");

        }
        post_nickname.setText(mnickname);
        post_title.setText(mtitle);
        post_subject.setText(msubject);
        post_desc.setText(mdesc);
        post_rating.setRating(Float.parseFloat(num_star));
        post_date.setText(mdate);

        if(getIntent().getStringExtra("post_profile_link") != null && !getIntent().getStringExtra("post_profile_link").equals("")){
            Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("post_profile_link")).fit().centerCrop().into(image_icon);
        }



        edit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditPostActivity.class);

                intent.putExtra("subjectSelect", subject_id);
                intent.putExtra("post_rating", num_star);
                intent.putExtra("post_id", post_id);
                intent.putExtra("post_nickname", mnickname);
                intent.putExtra("post_title", mtitle);
                intent.putExtra("post_subject", msubject);
                intent.putExtra("post_desc", mdesc);
                intent.putExtra("post_date", mdate);

                startActivity(intent);
                finish();
            }
        });
        delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this);
                builder.setMessage("คุณยืนยันที่จะลบโพสนี้หรือไม่?");
                builder.setCancelable(true);
                builder.setNegativeButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child("post").child(post_id).removeValue();
                        mDatabase.child("Likes").child(post_id).removeValue();
                        mDatabase.child("comment").child(post_id).removeValue();
                        //Intent intent = new Intent(ViewPostActivity.this, MainActivity.class);
                        //startActivity(intent);
                        //finish();
                        Intent intent = new Intent(ViewPostActivity.this, SubjectPostActivity.class);
                        intent.putExtra("subjectSelect", subject_id);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
            }
        });

        post_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowHistoryActivity.class);
                intent.putExtra("uid", getIntent().getStringExtra("post_nickname"));
                startActivity(intent);
            }
        });

        image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowHistoryActivity.class);
                intent.putExtra("uid", getIntent().getStringExtra("post_nickname"));
                startActivity(intent);
            }
        });

    }


}
