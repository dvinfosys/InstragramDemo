package com.dvinfosys.instrademo.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dvinfosys.instrademo.Activity.AllPostActivity;
import com.dvinfosys.instrademo.Model.PostModel;
import com.dvinfosys.instrademo.R;
import com.dvinfosys.instrademo.Utils.Constanst;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public void onBindViewHolder(PostItem holder, final int i) {
        final PostModel model = list.get(i);
        Glide.with(context)
                .load(model.getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).error(R.drawable.imagenotavailable))
                .into(holder.postImage);


        holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = "https://www.instagram.com/p/" + model.getShortcode();
                long foo = Long.parseLong(model.getTimestamp()) * 1000;
                Date date = new Date(foo);
                DateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");
                //Toast.makeText(context, "Date : " + formatter.format(date), Toast.LENGTH_SHORT).show();
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constanst.POST, (Serializable)list);
                    Intent intent = new Intent(context, AllPostActivity.class);
                    intent.putExtra(Constanst.POST_POSITION, String.valueOf(i));
                    intent.putExtra(Constanst.POST, bundle);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
                    context.startActivity(intent);
                }
            }
        });
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
