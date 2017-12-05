package kmitl.projectfinal.se.kmitlbackyard;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PostModel listPost = postLists.get(position);
        holder.textview1.setText(listPost.getDescription());
    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textview1;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textview1 = itemView.findViewById(R.id.txtItem);
        }
    }
}
