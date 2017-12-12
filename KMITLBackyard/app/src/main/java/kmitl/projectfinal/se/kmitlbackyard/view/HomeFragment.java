package kmitl.projectfinal.se.kmitlbackyard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import kmitl.projectfinal.se.kmitlbackyard.R;
import kmitl.projectfinal.se.kmitlbackyard.activity.ListSubjectActivity;
import kmitl.projectfinal.se.kmitlbackyard.activity.SubjectPostActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button engineeringBtn, architectureBtn, educationBtn, agriculturalTechnoBtn,
            scienceBtn, agricultureBtn, itBtn, internationalBtn, nanoTechnoBtn, productionInnoBtn,
            managementBtn, interFlightBtn, liberalArtsBtn, search_btn, clearBtn;

    private AutoCompleteTextView seach_subject;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private String value, fac_spin, type_spin;
    private ArrayList<String> listItems = new ArrayList<String>();
    private String[] listAllFacs = new String[]{"Engineering", "Architecture", "Education", "Agricultural_techno",
                                                "Science", "Agriculture", "It", "International", "Nano_techno", "Production_inno",
                                                "Management", "Inter_flight", "Liberal_arts"};
    private String[] listAllType = new String[]{"รายวิชาทั่วไป (ทุกสาขา&ทุกชั้นปี)", "วิชาเลือกกลุ่มวิชาภาษา", "วิชาเลือกกลุ่มวิชามนุษย์ศาสตร์", "วิชาเลือกกลุ่มวิชาสังคมศาสตร์", "วิชาทั่วไปกลุ่มวิชาวิทยาศาสตร์และคณิตศาสตร์",
                                                "วิชาเลือกทางสาขา", "วิชาเลือกเสรี", "กลุ่มเวลาเรียนของรายวิชา", "วิชาภาษาอังกฤษ",
                                                "วิชาวิทยาศาสตร์กับคณิตศาสตร์", "วิชาเลือกกลุ่มคุณค่าแห่งชีวิต", "วิชาเลือกลุ่มวิถีแห่งสังคม", "วิชาเลือกกลุ่มศาสตร์แห่งการคิด",
                                                "วิชาเลือกกลุ่มศิลปะแห่งการจัดการ", "วิชาเลือกกลุ่มภาษาและการสื่อสาร","ทุกหมวดวิชา"};
    private Spinner spiner_fac, spinner_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        engineeringBtn = v.findViewById(R.id.engineering_btn);
        architectureBtn = v.findViewById(R.id.architecture_btn);
        educationBtn = v.findViewById(R.id.education_btn);
        agriculturalTechnoBtn = v.findViewById(R.id.agricultural_techno_btn);
        scienceBtn = v.findViewById(R.id.science_btn);
        agricultureBtn = v.findViewById(R.id.agriculture_btn);
        itBtn = v.findViewById(R.id.it_btn);
        internationalBtn = v.findViewById(R.id.international_btn);
        nanoTechnoBtn = v.findViewById(R.id.nano_techno_btn);
        productionInnoBtn = v.findViewById(R.id.production_inno_btn);
        managementBtn = v.findViewById(R.id.management_btn);
        interFlightBtn = v.findViewById(R.id.inter_flight_btn);
        liberalArtsBtn = v.findViewById(R.id.liberal_arts_btn);
        seach_subject = v.findViewById(R.id.seach_subject);
        search_btn = v.findViewById(R.id.search_btn);
        clearBtn = v.findViewById(R.id.clear_btn);
        spinner_type = v.findViewById(R.id.spiner_type);

        adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, listAllType);
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        type_spin = "รายวิชาทั่วไป (ทุกสาขา&ทุกชั้นปี)";
                        break;
                    case 1:
                        type_spin = "วิชาเลือกกลุ่มวิชาภาษา";
                        break;
                    case 2:
                        type_spin = "วิชาเลือกกลุ่มวิชามนุษย์ศาสตร์";
                        break;
                    case 3:
                        type_spin = "วิชาเลือกกลุ่มวิชาสังคมศาสตร์";
                        break;
                    case 4:
                        type_spin = "วิชาทั่วไปกลุ่มวิชาวิทยาศาสตร์และคณิตศาสตร์";
                        break;
                    case 5:
                        type_spin = "วิชาเลือกทางสาขา";
                        break;
                    case 6:
                        type_spin = "วิชาเลือกเสรี";
                        break;
                    case 7:
                        type_spin = "กลุ่มเวลาเรียนของรายวิชา";
                        break;
                    case 8:
                        type_spin = "วิชาภาษาอังกฤษ";
                        break;
                    case 9:
                        type_spin = "วิชาวิทยาศาสตร์กับคณิตศาสตร์";
                        break;
                    case 10:
                        type_spin = "วิชาเลือกกลุ่มคุณค่าแห่งชีวิต";
                        break;
                    case 11:
                        type_spin = "วิชาเลือกลุ่มวิถีแห่งสังคม";
                        break;
                    case 12:
                        type_spin = "วิชาเลือกกลุ่มศาสตร์แห่งการคิด";
                        break;
                    case 13:
                        type_spin = "วิชาเลือกกลุ่มศิลปะแห่งการจัดการ";
                        break;
                    case 14:
                        type_spin = "วิชาเลือกกลุ่มภาษาและการสื่อสาร";
                        break;
                    case 15:
                        type_spin = "ทุกหมวดวิชา";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter(getContext(), R.layout.list_item, R.id.txtItem, listItems);
        seach_subject.setAdapter(adapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        seach_subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                value = s.toString();

                if (value.equals("")) {
                    clearBtn.setVisibility(View.INVISIBLE);

                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count){

            }

        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String fac : listAllFacs){
                    for  (String type: listAllType){
                        Iterable<DataSnapshot> children = dataSnapshot.child("subject").child(fac).child(type).getChildren();
                        for (DataSnapshot child: children){
                            String course_name = String.valueOf(child.child("course_name").getValue());
                            String course_id = String.valueOf(child.getKey());
                            if (listItems.indexOf(course_id + " " + course_name) == -1){
                                listItems.add(course_id + " " + course_name);
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        engineeringBtn.setOnClickListener(this);
        architectureBtn.setOnClickListener(this);
        educationBtn.setOnClickListener(this);
        agriculturalTechnoBtn.setOnClickListener(this);
        scienceBtn.setOnClickListener(this);
        agricultureBtn.setOnClickListener(this);
        itBtn.setOnClickListener(this);
        internationalBtn.setOnClickListener(this);
        nanoTechnoBtn.setOnClickListener(this);
        productionInnoBtn.setOnClickListener(this);
        managementBtn.setOnClickListener(this);
        interFlightBtn.setOnClickListener(this);
        liberalArtsBtn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), ListSubjectActivity.class);
        Intent intent2  = new Intent(getActivity(), SubjectPostActivity.class);
        intent.putExtra("type", type_spin);
        switch (view.getId()){

            case R.id.engineering_btn:
                intent.putExtra("fac","Engineering");
                startActivity(intent);
                break;
            case R.id.architecture_btn:
                intent.putExtra("fac","Architecture");
                startActivity(intent);
                break;
            case R.id.education_btn:
                intent.putExtra("fac","Education");
                startActivity(intent);
                break;
            case R.id.agricultural_techno_btn:
                intent.putExtra("fac","Agricultural_techno");
                startActivity(intent);
                break;
            case R.id.science_btn:
                intent.putExtra("fac","Science");
                startActivity(intent);
                break;
            case R.id.agriculture_btn:
                intent.putExtra("fac","Agriculture");
                startActivity(intent);
                break;
            case R.id.it_btn:
                intent.putExtra("fac","It");
                startActivity(intent);
                break;
            case R.id.international_btn:
                intent.putExtra("fac","International");
                startActivity(intent);
                break;
            case R.id.nano_techno_btn:
                intent.putExtra("fac","Nano_techno");
                startActivity(intent);
                break;
            case R.id.production_inno_btn:
                intent.putExtra("fac","Production_inno");
                startActivity(intent);
                break;
            case R.id.management_btn:
                intent.putExtra("fac","Management");
                startActivity(intent);
                break;
            case R.id.inter_flight_btn:
                intent.putExtra("fac","Inter_flight");
                startActivity(intent);
                break;
            case R.id.liberal_arts_btn:
                intent.putExtra("fac","Liberal_arts");
                startActivity(intent);
                break;
            case R.id.search_btn:
                if(!seach_subject.getEditableText().toString().equals("") ){
                    boolean temp = false;
                    for (String item: listItems){
                        if (item.equals(seach_subject.getEditableText().toString())){
                            temp = true;
                        }
                    }
                    if (temp){
                        intent2.putExtra("subjectSelect", seach_subject.getText().toString());
                        startActivity(intent2);
                    }
                    else {
                        MDToast mdToast = MDToast.makeText(getActivity(), "กรุณากรอกชื่อวิชาและรหัสวิชาให้ถูกต้อง", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }
                else {
                    MDToast mdToast = MDToast.makeText(getActivity(), "กรุณากรอกชื่อวิชาและรหัสวิชาในช่องว่าง", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                }

                break;
            case R.id.clear_btn:
                seach_subject.setText("");
                break;
        }

    }
}
