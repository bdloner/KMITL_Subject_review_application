package kmitl.projectfinal.se.kmitlbackyard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private List<PostModel> listPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = v.findViewById(R.id.list_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listPosts = new ArrayList<>();
        queryData();

        return v;
    }

    private void queryData() {
        Query query = mDatabase.child("post");
        query.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String post_id = dataSnapshot.getKey();
                PostModel postItem = new PostModel(
                        newPost.get("description").toString(), newPost.get("score").toString(), newPost.get("score_num").toString(),
                        newPost.get("subject_id").toString(), newPost.get("timeStamp").toString(), newPost.get("title").toString(),
                        newPost.get("uid").toString(), post_id, newPost.get("post_liked").toString(), newPost.get("viewer").toString(),
                        newPost.get("user_key").toString(), "");
                    listPosts.add(0, postItem);
                    adapter = new PostAdapter(listPosts, getContext());
                    recyclerView.setAdapter(adapter);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
