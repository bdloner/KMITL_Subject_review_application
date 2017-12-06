package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CPCust on 5/12/2560.
 */

class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public PostAdapter(List<PostModel> listPosts, Context context) {
        this.postLists = listPosts;
        this.context = context;
    }
    private DatabaseReference mDatabase;

    private Context context;
    private String post_profile_link;
    List<PostModel> postLists;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_post, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PostModel listPost = postLists.get(position);
        holder.post_nickname.setText(listPost.getUid());
        holder.post_title.setText(listPost.getTitle());
        holder.post_subject.setText(listPost.getSubject_id());
        holder.post_desc.setText(listPost.getDescription());
        holder.post_rating.setRating(Float.parseFloat(listPost.getScore()));
        holder.post_date.setText(listPost.getTimeStamp());
        Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> imgUser = (Map<String, Object>) dataSnapshot.getValue();
                if (imgUser.get("nickname").toString().equals(listPost.getUid()) && !imgUser.get("profileImgLink").equals("null")) {
                    if (imgUser.get("profileImgLink").toString() != null || !imgUser.get("profileImgLink").toString().equals("")){
                        Picasso.with(holder.context).load(imgUser.get("profileImgLink").toString()).fit().centerCrop().into(holder.image_icon);
                        post_profile_link = imgUser.get("profileImgLink").toString();
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewPostActivity.class);
                intent.putExtra("post_nickname", listPost.getUid());
                intent.putExtra("post_title", listPost.getTitle());
                intent.putExtra("post_subject", listPost.getSubject_id());
                intent.putExtra("post_desc", listPost.getDescription());
                intent.putExtra("post_rating", listPost.getScore());
                intent.putExtra("post_date", listPost.getTimeStamp());
                intent.putExtra("post_profile_link", post_profile_link);
                //intent.putExtra("post_ImgLink", listPost.getPostImgLink());
                context.startActivity(intent);
            }
        });

        holder.postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button postComment;
        private CardView cardView;
        private CustomTextView post_nickname;
        private CustomTextView post_title;
        private CustomTextView post_subject;
        private CustomTextView post_desc;
        private RatingBar post_rating;
        private CustomTextView post_date;
        private CircleImageView image_icon;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            postComment = itemView.findViewById(R.id.post_comment);
            cardView = itemView.findViewById(R.id.card_view);

            post_nickname = itemView.findViewById(R.id.post_nickname);
            post_title = itemView.findViewById(R.id.post_title);
            post_subject = itemView.findViewById(R.id.post_subjet);
            post_desc = itemView.findViewById(R.id.post_desc);
            post_rating = itemView.findViewById(R.id.post_rating);
            post_date = itemView.findViewById(R.id.post_date);
            image_icon = itemView.findViewById(R.id.image_icon);


        }
    }
}