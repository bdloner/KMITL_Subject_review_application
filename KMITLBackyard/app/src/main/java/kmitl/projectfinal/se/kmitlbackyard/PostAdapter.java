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

import java.util.List;

/**
 * Created by CPCust on 5/12/2560.
 */

class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public PostAdapter(List<PostModel> listPosts, Context context) {
        this.postLists = listPosts;
        this.context = context;
    }
    private Context context;
    List<PostModel> postLists;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PostModel listPost = postLists.get(position);
        holder.post_nickname.setText(listPost.getUid());
        holder.post_title.setText(listPost.getTitle());
        holder.post_subject.setText(listPost.getSubject_id());
        holder.post_desc.setText(listPost.getDescription());
        holder.post_rating.setRating(Float.parseFloat(listPost.getScore()));
        holder.post_date.setText(listPost.getTimeStamp());

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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button postComment;
        private CardView cardView;
        private CustomTextView post_nickname;
        private CustomTextView post_title;
        private CustomTextView post_subject;
        private CustomTextView post_desc;
        private RatingBar post_rating;
        private CustomTextView post_date;

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
        }
    }
}
