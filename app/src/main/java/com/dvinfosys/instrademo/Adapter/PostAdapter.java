package com.dvinfosys.instrademo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dvinfosys.instrademo.Model.PostModel;
import com.dvinfosys.instrademo.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostItem> {

    private Context context;
    private ArrayList<PostModel> list;

    public PostAdapter(Context context, ArrayList<PostModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PostItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, viewGroup, false);
        return new PostItem(view);
    }

    @Override
    public void onBindViewHolder(PostItem holder, int i) {
        PostModel model = list.get(i);
        Glide.with(context)
                .load(model.getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).error(R.drawable.imagenotavailable))
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostItem extends RecyclerView.ViewHolder {
        ImageView postImage;

        public PostItem(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
        }
    }
}
