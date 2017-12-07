package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private Button engineeringBtn, architectureBtn, educationBtn, agriculturalTechnoBtn,
            scienceBtn, agricultureBtn, itBtn, internationalBtn, nanoTechnoBtn, productionInnoBtn,
            managementBtn, interFlightBtn, liberalArtsBtn, search_btn;

    private AutoCompleteTextView seach_subject;
    ArrayAdapter<String> adapter;
    private Spinner catSubject, semester;
    DatabaseReference databaseReference;
    private ArrayList<String> listItems = new ArrayList<String>();
    private String[] listAllFacs = new String[]{"Engineering", "Architecture", "Education", "Agricultural_techno",
                                                "Science", "Agriculture", "It", "International", "Nano_techno", "Production_inno",
                                                "Management", "Inter_flight", "Liberal_arts"};

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

        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.txtItem, listItems);
        seach_subject.setAdapter(adapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String fac : listAllFacs){
                    Iterable<DataSnapshot> children = dataSnapshot.child("subject").child(fac).getChildren();
                    for (DataSnapshot child: children){
                        String course_name = String.valueOf(child.child("course_name").getValue());
                        String course_id = String.valueOf(child.getKey());
                        listItems.add(course_id + " " + course_name);

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

        return v;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), ListSubjectActivity.class);
        Intent intent2 = new Intent(getActivity(), SubjectPostActivity.class);


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
                intent2.putExtra("subjectSelect", seach_subject.getText().toString());
                startActivity(intent2);
                break;
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
