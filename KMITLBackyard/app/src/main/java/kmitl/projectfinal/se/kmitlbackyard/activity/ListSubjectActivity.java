package kmitl.projectfinal.se.kmitlbackyard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kmitl.projectfinal.se.kmitlbackyard.R;

public class ListSubjectActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    DatabaseReference databaseReference;
    String fac, type;
    private String[] listAllType = new String[]{"รายวิชาทั่วไป (ทุกสาขา&ทุกชั้นปี)", "วิชาเลือกกลุ่มวิชาภาษา", "วิชาเลือกกลุ่มวิชามนุษย์ศาสตร์", "วิชาเลือกกลุ่มวิชาสังคมศาสตร์", "วิชาทั่วไปกลุ่มวิชาวิทยาศาสตร์และคณิตศาสตร์",
            "วิชาเลือกทางสาขา", "วิชาเลือกเสรี", "กลุ่มเวลาเรียนของรายวิชา", "วิชาภาษาอังกฤษ",
            "วิชาวิทยาศาสตร์กับคณิตศาสตร์", "วิชาเลือกกลุ่มคุณค่าแห่งชีวิต", "วิชาเลือกลุ่มวิถีแห่งสังคม", "วิชาเลือกกลุ่มศาสตร์แห่งการคิด",
            "วิชาเลือกกลุ่มศิลปะแห่งการจัดการ", "วิชาเลือกกลุ่มภาษาและการสื่อสาร","ทุกหมวดวิชา"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_subject);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.list_view);
        editText = findViewById(R.id.txt_search);
        listView.setOnItemClickListener(this);
        fac = getIntent().getStringExtra("fac");
        type = getIntent().getStringExtra("type");
        initList();
        getSupportActionBar().setTitle(fac);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!type.equals("ทุกหมวดวิชา")){
                    Iterable<DataSnapshot> children = dataSnapshot.child("subject").child(fac).child(type).getChildren();
                    for (DataSnapshot child: children){
                        String course_name = String.valueOf(child.child("course_name").getValue());
                        String course_id = String.valueOf(child.getKey());
                        listItems.add(course_id + " " + course_name);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    for (String type1: listAllType){
                        Iterable<DataSnapshot> children = dataSnapshot.child("subject").child(fac).child(type1).getChildren();
                        for (DataSnapshot child: children){
                            String course_name = String.valueOf(child.child("course_name").getValue());
                            String course_id = String.valueOf(child.getKey());
                            listItems.add(course_id + " " + course_name);
                        }
                        adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ListSubjectActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void initList(){
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, listItems);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SubjectPostActivity.class);
        intent.putExtra("subjectSelect", listItems.get(position));
        intent.putExtra("type", "home");
        startActivities(new Intent[]{intent});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
