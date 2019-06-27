package com.dvinfosys.instrademo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dvinfosys.instrademo.Model.PostModel;
import com.dvinfosys.instrademo.R;
import com.dvinfosys.widgets.ImageView.CircleImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.PostItem> {

    private Context context;
    private ArrayList<PostModel> list;

    public AllPostAdapter(Context context, ArrayList<PostModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PostItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_post_item, viewGroup, false);
        return new PostItem(view);
    }

    @Override
    public void onBindViewHolder(PostItem holder, final int i) {
        final PostModel model = list.get(i);
        Glide.with(context)
                .load(model.getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).error(R.drawable.imagenotavailable))
                .into(holder.postImage);

        Glide.with(context)
                .load(model.getUserimage())
                .apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).error(R.drawable.imagenotavailable))
                .into(holder.imgProfile);

        long foo = Long.parseLong(model.getTimestamp()) * 1000;
        Date date = new Date(foo);
        DateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");
        holder.tvDate.setText(formatter.format(date));
        holder.tvLike.setText(model.getLiked() + " likes");
        holder.tvLocation.setText(model.getLocation());
        if (model.getLocation().equals("")) {
            holder.tvLocation.setVisibility(View.GONE);
        }
        holder.tvCaption.setText(model.getMediaCaption());
        holder.tvComments.setText("View All " + model.getComment() + " Comments");
        holder.tvUserName.setText(model.getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostItem extends RecyclerView.ViewHolder {
        ImageView postImage;
        CircleImageView imgProfile;
        TextView tvDate, tvLike, tvLocation, tvUserName, tvComments, tvCaption;

        public PostItem(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
            imgProfile = itemView.findViewById(R.id.img_user_profile);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            tvLike = itemView.findViewById(R.id.tv_item_like);
            tvLocation = itemView.findViewById(R.id.tv_item_location);
            tvUserName = itemView.findViewById(R.id.tv_item_username);
            tvComments = itemView.findViewById(R.id.tv_item_comments);
            tvCaption = itemView.findViewById(R.id.tv_item_caption);
        }
    }
}
