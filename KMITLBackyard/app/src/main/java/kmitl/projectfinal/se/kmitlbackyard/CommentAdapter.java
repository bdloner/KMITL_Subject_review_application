package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by CPCust on 7/12/2560.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    List<CommentModel> itemList;
    private Context context;
    private DatabaseReference mDatabase;

    public CommentAdapter(List<CommentModel> itemList, Context context) {
     this.itemList = itemList;
     this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_comment, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommentModel listComment = itemList.get(position);
        holder.comment_desc.setText(listComment.getContent());
        holder.comment_nickname.setText(listComment.getUid());

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
                if (imgUser.get("nickname").toString().equals(listComment.getUid()) && !imgUser.get("profileImgLink").equals("null")) {
                    if (imgUser.get("profileImgLink").toString() != null || !imgUser.get("profileImgLink").toString().equals("")){
                        //holder.post_profile_link = imgUser.get("profileImgLink").toString();
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
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private CustomTextView comment_nickname, comment_desc;
    private CircleImageView image_icon;
    private Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            comment_nickname = itemView.findViewById(R.id.comment_nickname);
            comment_desc = itemView.findViewById(R.id.comment_desc);
            image_icon = itemView.findViewById(R.id.image_icon);
        }
    }
}
