package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    CustomTextView profile_email;
    EditText profile_nickname;
    String uid;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        profile_email = v.findViewById(R.id.profile_email);
        profile_nickname = v.findViewById(R.id.profile_nickname);
//        uid = getActivity().getIntent().getStringExtra("uid");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> children = dataSnapshot.child("user").child(uid).getChildren();
//                for (DataSnapshot child: children){
//                    if (child.getKey().equals("email")){
//                        profile_email.setText(String.valueOf(child.getValue()));
//                    }
//                    else if(child.getKey().equals("nickname")){
//                        profile_nickname.setText(String.valueOf(child.getValue()));
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        return v;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
