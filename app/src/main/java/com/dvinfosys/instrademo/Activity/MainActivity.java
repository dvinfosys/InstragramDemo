package com.dvinfosys.instrademo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dvinfosys.instrademo.Adapter.PostAdapter;
import com.dvinfosys.instrademo.Model.PostModel;
import com.dvinfosys.instrademo.R;
import com.dvinfosys.instrademo.Utils.Constanst;
import com.dvinfosys.widgets.ImageView.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserName;
    private ArrayList<PostModel> list;
    private RecyclerView rvPost;
    private CircleImageView imgProfile;
    private TextView tvFullName, tvBiography, tvPostCount, tvFollowers, tvFollowing,tvNoPost;
    private String imgProfileUrl, userName, FullName, Biography, PostCount, Followers, Following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        tvUserName = findViewById(R.id.tv_username);
        tvFullName = findViewById(R.id.tv_full_name);
        rvPost = findViewById(R.id.rv_post);
        imgProfile = findViewById(R.id.img_profile);
        tvBiography = findViewById(R.id.tv_biography);
        tvPostCount = findViewById(R.id.tv_post_count);
        tvFollowers = findViewById(R.id.tv_followers_count);
        tvFollowing = findViewById(R.id.tv_following_count);
        tvNoPost = findViewById(R.id.tv_no_post);

        rvPost.setLayoutManager(new GridLayoutManager(this, 3));
        rvPost.setHasFixedSize(true);
        rvPost.setItemAnimator(new DefaultItemAnimator());

        AndroidNetworking.get(Constanst.Instagram_API_LINK)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject user = response.getJSONObject("graphql").getJSONObject("user");
                            FullName = user.getString("full_name");
                            userName = user.getString("username");
                            Biography = user.getString("biography");
                            Followers = user.getJSONObject("edge_followed_by").getString("count");
                            Following = user.getJSONObject("edge_follow").getString("count");
                            imgProfileUrl = user.getString("profile_pic_url");
                            PostCount = user.getJSONObject("edge_owner_to_timeline_media").getString("count");
                            JSONArray array = response.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONArray("edges");
                            list = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                JSONObject node = jsonObject.getJSONObject("node");
                                String image = node.getString("display_url");
                                PostModel model = new PostModel();
                                model.setUrl(image);
                                list.add(model);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (!list.isEmpty()){
                            tvNoPost.setVisibility(View.GONE);
                            rvPost.setVisibility(View.VISIBLE);
                        }else {
                            tvNoPost.setVisibility(View.VISIBLE);
                            rvPost.setVisibility(View.GONE);
                        }

                        PostAdapter adapter = new PostAdapter(MainActivity.this, list);
                        rvPost.setAdapter(adapter);
                        tvUserName.setText(userName);
                        tvFullName.setText(FullName);
                        tvBiography.setText(Biography);
                        tvPostCount.setText(PostCount);
                        tvFollowing.setText(Following);
                        tvFollowers.setText(Followers);
                        Glide.with(MainActivity.this)
                                .load(imgProfileUrl)
                                .apply(new RequestOptions().placeholder(R.drawable.imagenotavailable).error(R.drawable.imagenotavailable))
                                .into(imgProfile);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Error : ", anError.getErrorBody());
                    }
                });
    }
}
