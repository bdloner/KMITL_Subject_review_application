package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.w3c.dom.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends Activity{

    private Button closeBtn, blockSend, avaSend;
    private EditText addComment;
    private String value, timeStamp, post_id ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private List<CommentModel> listComments;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FrameLayout frameLayout5;
    private String key_commment, title_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        closeBtn = findViewById(R.id.close_btn);
        blockSend = findViewById(R.id.block_send);
        avaSend = findViewById(R.id.ava_send);
        addComment = findViewById(R.id.add_comment);
        timeStamp = getCurrentTime();
        recyclerView = findViewById(R.id.comment_listview);
        frameLayout5 = findViewById(R.id.frameLayout5);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        ableToSendComment();
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            post_id = bundle.getString("post_id");
        }
        listComments = new ArrayList<>();
        queryData();
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                key_commment = listComments.get(item.getGroupId()).getKey();
                title_comment = listComments.get(item.getGroupId()).getContent();
                Intent intent = new Intent(getApplicationContext(), EditCommentActivity.class);
                intent.putExtra("key_commment", key_commment);
                intent.putExtra("title_comment", title_comment);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
                break;
            case 1:
                key_commment = listComments.get(item.getGroupId()).getKey();
                final AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                builder.setMessage("คุณยืนยันจะลบคอมเมนต์นี้หรือไม่?");
                builder.setCancelable(true);
                builder.setNegativeButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child("comment").child(post_id).child(key_commment).removeValue();
                        MDToast mdToast = MDToast.makeText(getApplicationContext(), "คอมเมนต์ถูกลบแล้ว", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                        mdToast.show();
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
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void ableToSendComment() {
        Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> dataset = (Map<String, Object>) dataSnapshot.getValue();
                if(dataSnapshot.getKey().equals(user.getUid())){
                    if (dataset.get("role").toString().equals("student")){
                        frameLayout5.setVisibility(View.VISIBLE);
                    }
                    else if (dataset.get("role").toString().equals("teacher")){
                        frameLayout5.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void sendComment(View view) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("content", value);
        //result.put("post_id", post_id);
        result.put("timeStamp", timeStamp);
        result.put("uid", user.getDisplayName());
        result.put("user_key", user.getUid());
        mDatabase.child("comment").child(post_id).push().setValue(result);
        value = "";
        addComment.setText("");
    }
    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void queryData() {
        Query query = mDatabase.child("comment").child(post_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listComments.clear();
                Query query1 = mDatabase.child("comment").child(post_id);
                query1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newComment = (Map<String, Object>) dataSnapshot.getValue();
                        String key = dataSnapshot.getKey();
                        CommentModel commentItem = new CommentModel(
                                newComment.get("content").toString(),
                                newComment.get("timeStamp").toString(), newComment.get("uid").toString(), key, newComment.get("user_key").toString()
                        );
                        listComments.add(commentItem);
                        adapter = new CommentAdapter(listComments, getApplication());
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                adapter = new CommentAdapter(listComments, getApplication());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
