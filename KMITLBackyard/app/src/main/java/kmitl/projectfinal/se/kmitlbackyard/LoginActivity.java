package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.emptyview.EmptyView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    private EditText loginEmail, loginPassword;
    private Button btnLogin, btnRegister;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String inpemail;
    private String inppassword;
    private EmptyView emptyView;
    private CustomTextView forgotPass;
    private Dialog dialog;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[kmitl.-]{5}+\\.[ac]{2}+\\.[th]{2}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth =FirebaseAuth.getInstance();
//        firebaseAuth.signOut();

        emptyView = findViewById(R.id.layout_content_container);

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        forgotPass = findViewById(R.id.forgot_pass);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        //get firebase db reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_forgot_pass);
                dialog.setCancelable(true);
                dialog.show();

                EditText editEmail = dialog.findViewById(R.id.edit_email);

                Button resetEmailBtn = dialog.findViewById(R.id.reset_email_btn);

                resetEmailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void login() {
        inpemail = loginEmail.getText().toString();
        inppassword = loginPassword.getText().toString();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(inpemail);
        if (inppassword.equals("") || inpemail.equals("")){
            Toast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!matcher.find()){
            Toast.makeText(getApplicationContext(), "กรุณากรอกเมลล์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            loginPassword.setText("");
            return;
        }
        emptyView.showLoading();
        firebaseAuth.signInWithEmailAndPassword(inpemail, inppassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(intent);
//                     Log.i("authasda", firebaseAuth.getCurrentUser().getEmail()+  " "+ firebaseAuth.getCurrentUser().getDisplayName());
                     finish();
                }
            }
        });
    }

}
