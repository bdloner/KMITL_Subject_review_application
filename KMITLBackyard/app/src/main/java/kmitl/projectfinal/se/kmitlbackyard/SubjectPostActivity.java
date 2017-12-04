package kmitl.projectfinal.se.kmitlbackyard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SubjectPostActivity extends AppCompatActivity {

    private String subjectSelect = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_post);

        subjectSelect = getIntent().getStringExtra("subjectSelect");
        Toast.makeText(getApplicationContext(), subjectSelect, Toast.LENGTH_SHORT).show();
    }
}
