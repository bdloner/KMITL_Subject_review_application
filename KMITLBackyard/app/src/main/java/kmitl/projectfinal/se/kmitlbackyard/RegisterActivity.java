package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.emptyview.EmptyView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    boolean check = false;
    private EditText regEmail, regPassword, regConfirmPassword, regNickname;
    private Button btnRegister, btnBack;
    private String sRegEmail, sRegPassword, sRegConfirmPassword, sRegNickname;
    private Firebase mFirebase;
    private List<String> mChats = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;
    private EmptyView emptyView;

    String checkEmail;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[kmitl.-]{5}+\\.[ac]{2}+\\.[th]{2}$", Pattern.CASE_INSENSITIVE);
    int cnt_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebase = new Firebase("https://kmitlbackyard.firebaseio.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regConfirmPassword = findViewById(R.id.reg_confirm_password);
        regNickname = findViewById(R.id.reg_nickname);

        emptyView = findViewById(R.id.layout_content_container);

        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_back);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sRegEmail = regEmail.getText().toString();
                sRegPassword = regPassword.getText().toString();
                sRegConfirmPassword  = regConfirmPassword.getText().toString();
                sRegNickname = regNickname.getText().toString();
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(sRegEmail);
                //sameEmail();
                if(sRegEmail.equals("") || sRegPassword.equals("") || sRegConfirmPassword.equals("") || sRegNickname.equals("")){
                    Toast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else if(!sRegPassword.equals(sRegConfirmPassword)){
                    Toast.makeText(getApplicationContext(), "กรุณากรอกพาสเวิร์ดใหม่", Toast.LENGTH_SHORT).show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else if(!matcher.find()){
                    Toast.makeText(getApplicationContext(), "กรุณากรอกเมลล์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else {
                    emptyView.showLoading();
                    registerUser();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addData() {
//        Map<String, Object> his = new HashMap();
//        his.put("email", String.valueOf(sRegEmail));
//        his.put("password", String.valueOf(sRegPassword));
//        his.put("nickname", String.valueOf(sRegNickname));
//        his.put("image", "image");
//        mFirebase.child("user").push().setValue(his);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(sRegNickname)
                .build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Add Data success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        HashMap<String, Object> result = new HashMap<>();
        result.put("email", String.valueOf(sRegEmail));
        result.put("nickname", String.valueOf(sRegNickname));
        result.put("profileImgLink", "null");
        if(sRegEmail.split("@")[0].length() == 8 && !sRegEmail.contains("[a-zA-Z]+")){
            result.put("role", "student");
        }
        else {
            result.put("role", "teacher");
        }
        mDatabase.child("user").child(user.getUid()).setValue(result);
    }

    public void registerUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth.createUserWithEmailAndPassword(sRegEmail.trim(),sRegPassword.trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            addData();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), task.getResult().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
