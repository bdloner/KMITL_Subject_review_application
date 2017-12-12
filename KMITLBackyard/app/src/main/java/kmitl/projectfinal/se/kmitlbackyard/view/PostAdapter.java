package kmitl.projectfinal.se.kmitlbackyard.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.projectfinal.se.kmitlbackyard.model.PostModel;
import kmitl.projectfinal.se.kmitlbackyard.R;
import kmitl.projectfinal.se.kmitlbackyard.activity.CommentActivity;
import kmitl.projectfinal.se.kmitlbackyard.activity.ShowHistoryActivity;
import kmitl.projectfinal.se.kmitlbackyard.activity.ViewPostActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public PostAdapter(List<PostModel> listPosts, Context context) {
        this.postLists = listPosts;
        this.context = context;
    }
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLike;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Context context;
    private boolean mProcessLike =false;
    private List<PostModel> postLists;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_post, parent, false);
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseLike.keepSynced(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PostModel listPost = postLists.get(position);

        holder.post_title.setText(listPost.getTitle());
        holder.post_subject.setText(listPost.getSubject_id());
        holder.post_desc.setText(listPost.getDescription());
        holder.post_rating.setRating(Float.parseFloat(listPost.getScore()));
        holder.post_date.setText(listPost.getTimeStamp());
        holder.setLikeBtn(listPost.getPost_id());
        holder.post_id = listPost.getPost_id();
        holder.num_like = 0;
        holder.num_comment = 0;

        final Query query4 = mDatabase.child("view").child(holder.post_id);
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> result = (Map<String, Object>) dataSnapshot.getValue();
                holder.viewer = result.get("viewer").toString();
                holder.amount_view.setText(String.valueOf(holder.viewer));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> imgUser = (Map<String, Object>) dataSnapshot.getValue();
                if (dataSnapshot.getKey().equals(listPost.getUser_key())) {
                    holder.post_profile_link = imgUser.get("profileImgLink").toString();
                    holder.post_nickname.setText(imgUser.get("nickname").toString());
                    if (!imgUser.get("profileImgLink").equals("null") || imgUser.get("profileImgLink") != null){
                        Picasso.with(holder.context).load(imgUser.get("profileImgLink").toString()).fit().centerCrop().into(holder.image_icon);
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
        Query query3 = mDatabase.child("comment");
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.num_comment=0;
                Query query2 = mDatabase.child("comment").child(holder.post_id);
                query2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        holder.num_comment += 1;
                        holder.amount_comment.setText(String.valueOf(holder.num_comment));
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
                holder.amount_comment.setText(String.valueOf(holder.num_comment));
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
                intent.putExtra("post_profile_link", holder.post_profile_link);
                intent.putExtra("post_id",  listPost.getPost_id());
                intent.putExtra("user_key", listPost.getUser_key());
                intent.putExtra("subjectSelect", listPost.getSubjectSelect());
                intent.putExtra("viewer", String.valueOf(holder.viewer));
                intent.putExtra("type", listPost.getType());
                context.startActivity(intent);
            }
        });
        holder.postLove.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
               holder.queryLike();

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                holder.queryLike();
            }
        });
        Query queryNewLike = mDatabase.child("Likes");
        queryNewLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.num_like=0;
                Query queryNewLike2 = mDatabase.child("Likes").child(holder.post_id);
                queryNewLike2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        holder.num_like += 1;
                        holder.amount_love.setText(String.valueOf(holder.num_like));

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
                holder.amount_love.setText(String.valueOf(holder.num_like));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("post_id",  listPost.getPost_id());
                context.startActivity(intent);
            }
        });

        holder.post_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowHistoryActivity.class);
                intent.putExtra("uid", listPost.getUid());
                intent.putExtra("user_key", listPost.getUser_key());
                context.startActivity(intent);
            }
        });

        holder.image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowHistoryActivity.class);
                intent.putExtra("uid", listPost.getUid());
                intent.putExtra("user_key", listPost.getUser_key());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Button postComment;
        private LikeButton postLove;
        private CardView cardView;
        private CustomTextView post_nickname;
        private CustomTextView post_title;
        private CustomTextView post_subject;
        private CustomTextView post_desc;
        private CustomTextView amount_love, amount_comment, amount_view;
        private RatingBar post_rating;
        private CustomTextView post_date;
        private CircleImageView image_icon;
        private String post_profile_link, post_id, viewer;
        private Context context;
        private int num_comment, num_like;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            postComment = itemView.findViewById(R.id.post_comment);
            amount_comment = itemView.findViewById(R.id.amount_comment);
            amount_view = itemView.findViewById(R.id.amount_view);
            postLove = itemView.findViewById(R.id.post_love);
            num_comment = 0;
            num_like = 0;
            cardView = itemView.findViewById(R.id.card_view);
            amount_love = itemView.findViewById(R.id.amount_love);
            post_nickname = itemView.findViewById(R.id.post_nickname);
            post_title = itemView.findViewById(R.id.post_title);
            post_subject = itemView.findViewById(R.id.post_subjet);
            post_desc = itemView.findViewById(R.id.post_desc);
            post_rating = itemView.findViewById(R.id.post_rating);
            post_date = itemView.findViewById(R.id.post_date);
            image_icon = itemView.findViewById(R.id.image_icon);

            mDatabaseLike.keepSynced(true);
        }

        public void setLikeBtn(final String post_key){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_id).hasChild(user.getUid())){
                        postLove.setLiked(true);
                    }else {
                        postLove.setLiked(false);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        public void queryLike(){
            mProcessLike = true;
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(mProcessLike) {
                        if (dataSnapshot.child(post_id).hasChild(user.getUid())) {
                            mDatabaseLike.child(post_id).child(user.getUid()).removeValue();
                            mProcessLike = false;

                        }else {
                            mDatabaseLike.child(post_id).child(user.getUid()).setValue("uid");
                            mProcessLike = false;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}