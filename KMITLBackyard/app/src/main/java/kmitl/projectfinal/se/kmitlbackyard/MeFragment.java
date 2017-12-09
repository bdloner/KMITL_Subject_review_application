package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santalu.emptyview.EmptyView;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    CustomTextView profile_email;
    EditText profile_nickname;
    String uid;
    Uri uriProfileImage;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button logout_btn;
    ImageView plus;
    String profileImageUrl;
    CircleImageView circleImageView2,history_img;
    private static final int CHOOSE_IMAGE = 101;
    String role;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    Button save_btn;

    private EmptyView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        logout_btn = v.findViewById(R.id.logout_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profile_email = v.findViewById(R.id.profile_email);
        circleImageView2 = v.findViewById(R.id.circleImageView2);
        plus = v.findViewById(R.id.plus);
        profile_nickname = v.findViewById(R.id.profile_nickname);
        save_btn =  v.findViewById(R.id.save_btn);
        emptyView = v.findViewById(R.id.layout_content_container);
        history_img = v.findViewById(R.id.history_img);
        queryRole();
        user = firebaseAuth.getCurrentUser();
        setImageProfilePic();
        profile_email.setText(user.getEmail());
        profile_nickname.setText(user.getDisplayName());
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        circleImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebaseStorage();
                String nick = profile_nickname.getText().toString();
                UserProfileChangeRequest userProfileChangeRequest =  new UserProfileChangeRequest.Builder()
                        .setDisplayName(nick)
                        .build();
                emptyView.showLoading();
                user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        emptyView.showContent();

                        MDToast mdToast = MDToast.makeText(getContext(), "อัพเดทข้อมูลสำเร็จ", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                    }
                });
                HashMap<String, Object> result = new HashMap<>();
                result.put("email", String.valueOf(profile_email.getText().toString()));
                result.put("nickname", String.valueOf(nick));
                result.put("role", role);
                if (uriProfileImage ==  null){
                    if(user.getPhotoUrl() == null || user.getPhotoUrl().toString().equals("")){
                        result.put("profileImgLink", "null");
                    }
                    else{
                        result.put("profileImgLink", user.getPhotoUrl().toString());
                    }
                }
                else {
                    result.put("profileImgLink", profileImageUrl);
                }
                databaseReference.child("user").child(user.getUid()).setValue(result);
            }
        });
        history_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowHistoryActivity.class);
                intent.putExtra("uid", user.getDisplayName());
                intent.putExtra("user_key", user.getUid());
                startActivity(intent);
            }
        });
        return v;
    }

    private void queryRole() {
        Query query = databaseReference.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> setUser = (Map<String, Object>) dataSnapshot.getValue();

                if (dataSnapshot.getKey().equals(firebaseAuth.getUid())){
                    if(setUser.get("role").toString().equals("student")){
                        role = "student";
                    }
                    else if (setUser.get("role").toString().equals("teacher")){
                        role = "teacher";
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setImageProfilePic() {
        if(user.getPhotoUrl() != null){
            Picasso.with(getContext()).load(user.getPhotoUrl()).fit().centerCrop().into(circleImageView2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            uriProfileImage = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriProfileImage);
                circleImageView2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+firebaseAuth.getCurrentUser().getEmail()+".jpg");
        if(uriProfileImage != null){
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                    changeImageUrlInAuth(taskSnapshot.getDownloadUrl());
                    Picasso.with(getContext()).load(profileImageUrl).fit().centerCrop().into(circleImageView2);
                    databaseReference.child("user").child(user.getUid()).child("profileImgLink").setValue(profileImageUrl);
//                    Toast.makeText(getContext(), "Success to upload profile image", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "อัพโหลดภาพไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void changeImageUrlInAuth(Uri downloadUrl) {
        UserProfileChangeRequest userProfileChangeRequest =  new UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build();
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error change URL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture profile"), CHOOSE_IMAGE);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
