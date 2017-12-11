package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.emptyview.EmptyView;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    private EditText regEmail, regPassword, regConfirmPassword, regNickname;
    private Button btnRegister, btnBack;
    private String sRegEmail, sRegPassword, sRegConfirmPassword, sRegNickname;
    private Firebase mFirebase;
    private EmptyView emptyView;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[kmitl.-]{5}+\\.[ac]{2}+\\.[th]{2}$", Pattern.CASE_INSENSITIVE);

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
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลให้ครบ", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else if(!sRegPassword.equals(sRegConfirmPassword)){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกพาสเวิร์ดใหม่", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else if(!matcher.find()){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณากรอกเมลล์ให้ถูกต้อง", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                    regPassword.setText("");
                    regConfirmPassword.setText("");
                }
                else if(sRegPassword.length() < 6){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "พาสเวิร์ดต้องมีมากกว่า 6 ตัว", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
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
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(sRegNickname)
                .build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "เพิ่มข้อมูลลงฐานข้อมูลสำเร็จ", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
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
        firebaseAuth.createUserWithEmailAndPassword(sRegEmail.trim(),sRegPassword.trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            addData();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "กรุณายืนยันอีเมลล์ที่: " + firebaseAuth.getCurrentUser().getEmail(), MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                                    mdToast.show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "การส่งอีเมลล์ผิดพลาด กรุณากดส่งอีเมลอีกรอบ", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                                    mdToast.show();
                                }
                            });

                            FirebaseAuth.getInstance().signOut();
                        }else{
                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "อีเมลล์ของท่านมีอยู่ในระบบอยู่แล้ว", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                            mdToast.show();
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
