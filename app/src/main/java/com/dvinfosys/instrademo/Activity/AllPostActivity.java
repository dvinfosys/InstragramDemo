package com.dvinfosys.instrademo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dvinfosys.instrademo.Adapter.AllPostAdapter;
import com.dvinfosys.instrademo.Adapter.PostAdapter;
import com.dvinfosys.instrademo.Model.PostModel;
import com.dvinfosys.instrademo.R;
import com.dvinfosys.instrademo.Utils.Constanst;

import java.util.ArrayList;

public class AllPostActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvAllPost;
    private PostModel model;
    private ArrayList<PostModel> list = new ArrayList<>();
    private String position = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        context = this;

        rvAllPost = findViewById(R.id.rv_all_post);
        rvAllPost.setItemAnimator(new DefaultItemAnimator());
        rvAllPost.setHasFixedSize(true);
        rvAllPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constanst.POST);
        position = intent.getStringExtra(Constanst.POST_POSITION);
        list = (ArrayList<PostModel>) bundle.getSerializable(Constanst.POST);
        AllPostAdapter adapter = new AllPostAdapter(this, list);
        rvAllPost.setAdapter(adapter);
        rvAllPost.scrollToPosition(Integer.parseInt(position));

    }
}
