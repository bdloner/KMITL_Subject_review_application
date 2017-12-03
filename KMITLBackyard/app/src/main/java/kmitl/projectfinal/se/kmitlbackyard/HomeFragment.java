package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private Button engineeringBtn, architectureBtn, educationBtn, agriculturalTechnoBtn,
            scienceBtn, agricultureBtn, itBtn, internationalBtn, nanoTechnoBtn, productionInnoBtn,
            managementBtn, interFlightBtn, liberalArtsBtn;

    private Spinner catSubject, semester;

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

        return v;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), ListSubjectActivity.class);


        switch (view.getId()){

            case R.id.engineering_btn:
                startActivity(intent);
                break;
            case R.id.architecture_btn:
                startActivity(intent);
                break;
            case R.id.education_btn:
                startActivity(intent);
                break;
            case R.id.agricultural_techno_btn:
                startActivity(intent);
                break;
            case R.id.science_btn:
                startActivity(intent);
                break;
            case R.id.agriculture_btn:
                startActivity(intent);
                break;
            case R.id.it_btn:
                startActivity(intent);
                break;
            case R.id.international_btn:
                startActivity(intent);
                break;
            case R.id.nano_techno_btn:
                startActivity(intent);
                break;
            case R.id.production_inno_btn:
                startActivity(intent);
                break;
            case R.id.management_btn:
                startActivity(intent);
                break;
            case R.id.inter_flight_btn:
                startActivity(intent);
                break;
            case R.id.liberal_arts_btn:
                startActivity(intent);
                break;

    }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
