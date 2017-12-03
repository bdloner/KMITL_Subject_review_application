package kmitl.projectfinal.se.kmitlbackyard;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListSubjectActivity extends AppCompatActivity {
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    AutoCompleteTextView editText;
    DatabaseReference databaseReference;
    String fac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        listView = findViewById(R.id.list_view);
        editText = findViewById(R.id.txt_search);
        fac = getIntent().getStringExtra("fac");
        initList();
        //get firebase db reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("subject").child(fac).getChildren();
                for (DataSnapshot child: children){
                    String course_name = String.valueOf(child.child("course_name").getValue());
                    String course_id = String.valueOf(child.getKey());
                    listItems.add(course_id + " " + course_name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initList(){
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, listItems);
        editText.setAdapter(adapter);
        listView.setAdapter(adapter);
    }
}
