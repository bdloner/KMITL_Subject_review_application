package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
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
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
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
    private DatabaseReference mDatabaseLike;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Context context;
    private boolean mProcessLike =false;
    List<PostModel> postLists;
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
        holder.post_nickname.setText(listPost.getUid());
        holder.post_title.setText(listPost.getTitle());
        holder.post_subject.setText(listPost.getSubject_id());
        holder.post_desc.setText(listPost.getDescription());
        holder.post_rating.setRating(Float.parseFloat(listPost.getScore()));
        holder.post_date.setText(listPost.getTimeStamp());
        holder.setLikeBtn(listPost.getPost_id());
        holder.post_id = listPost.getPost_id();
        holder.post_liked = listPost.getPost_liked();
        holder.num_comment = 0;
        holder.amount_comment.setText(String.valueOf(0));
        holder.amount_love.setText(listPost.getPost_liked());
        holder.amount_view.setText(listPost.getViewer());
        Query query4 = mDatabase.child("post").child(listPost.getPost_id());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    listPost.setViewer(newPost.get("viewer").toString());
                    holder.amount_view.setText(newPost.get("viewer").toString());
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query1 = mDatabase.child("post").child(listPost.getPost_id());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    holder.amount_love.setText(newPost.get("post_liked").toString());
                }catch (Exception e){
                    MDToast mdToast = MDToast.makeText(context, "โพสต์ถูกลบแล้ว", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                menu.add(holder.getAdapterPosition(), 0, 0, "แก้ไข");
                menu.add(holder.getAdapterPosition(), 1, 0, "ลบ");
                menu.add(holder.getAdapterPosition(), 2, 0, "ยกเลิก");
            }
        });

        Query query = mDatabase.child("user");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> imgUser = (Map<String, Object>) dataSnapshot.getValue();
                if (imgUser.get("nickname").toString().equals(listPost.getUid()) && !imgUser.get("profileImgLink").equals("null")) {
                    if (imgUser.get("profileImgLink").toString() != null || !imgUser.get("profileImgLink").toString().equals("")){
                        holder.post_profile_link = imgUser.get("profileImgLink").toString();
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
               // holder.amount_comment.setText(String.valueOf(holder.num_comment));
                Query query2 = mDatabase.child("comment").child(holder.post_id);
                query2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newComment = (Map<String, Object>) dataSnapshot.getValue();

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listPost.setViewer(String.valueOf(Integer.parseInt(listPost.getViewer())+1));
                Map<String, Object> like = new HashMap<String, Object>();
                like.put("viewer", Integer.parseInt(listPost.getViewer()));
                mDatabase.child("post").child(listPost.getPost_id()).updateChildren(like);
                Intent intent = new Intent(context, ViewPostActivity.class);
                intent.putExtra("post_nickname", listPost.getUid());
                intent.putExtra("post_title", listPost.getTitle());
                intent.putExtra("post_subject", listPost.getSubject_id());
                intent.putExtra("post_desc", listPost.getDescription());
                intent.putExtra("post_rating", listPost.getScore());
                intent.putExtra("post_date", listPost.getTimeStamp());
                intent.putExtra("post_profile_link", holder.post_profile_link);
                intent.putExtra("post_ImgLink", listPost.getViewer());
                intent.putExtra("post_id",  listPost.getPost_id());
                intent.putExtra("user_key", listPost.getUser_key());
                intent.putExtra("subjectSelect", listPost.getSubjectSelect());
                context.startActivity(intent);
            }
        });
        holder.postLove.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                listPost.setPost_liked(String.valueOf(Integer.parseInt(listPost.getPost_liked())+1));
                Map<String, Object> like = new HashMap<String, Object>();
                like.put("post_liked", Integer.parseInt(listPost.getPost_liked()));
                mDatabase.child("post").child(listPost.getPost_id()).updateChildren(like);
               // holder.amount_love.setText(listPost.getPost_liked());
               holder.queryLike();

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                listPost.setPost_liked(String.valueOf(Integer.parseInt(listPost.getPost_liked())-1));
                Map<String, Object> like = new HashMap<String, Object>();
                like.put("post_liked", Integer.parseInt(listPost.getPost_liked()));
                mDatabase.child("post").child(listPost.getPost_id()).updateChildren(like);
               // holder.amount_love.setText(listPost.getPost_liked());
                holder.queryLike();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        private String post_profile_link, post_id;
        private Context context;
        private int num_comment;
        DatabaseReference mdatabaseLike;
        FirebaseAuth mAuth;
        public String post_liked;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            postComment = itemView.findViewById(R.id.post_comment);
            amount_comment = itemView.findViewById(R.id.amount_comment);
            amount_view = itemView.findViewById(R.id.amount_view);
            postLove = itemView.findViewById(R.id.post_love);
            num_comment = 0;
            cardView = itemView.findViewById(R.id.card_view);
            amount_love = itemView.findViewById(R.id.amount_love);
            post_nickname = itemView.findViewById(R.id.post_nickname);
            post_title = itemView.findViewById(R.id.post_title);
            post_subject = itemView.findViewById(R.id.post_subjet);
            post_desc = itemView.findViewById(R.id.post_desc);
            post_rating = itemView.findViewById(R.id.post_rating);
            post_date = itemView.findViewById(R.id.post_date);
            image_icon = itemView.findViewById(R.id.image_icon);
            mdatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
            mAuth = FirebaseAuth.getInstance();

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