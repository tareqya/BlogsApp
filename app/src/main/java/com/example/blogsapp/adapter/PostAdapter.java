package com.example.blogsapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogsapp.R;
import com.example.blogsapp.database.Post;
import com.example.blogsapp.utils.SharedMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Post> posts ;
    private Context context;
    public PostAdapter(Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = this.posts.get(position);
        PostViewHolder postViewHolder = (PostViewHolder) holder;
        postViewHolder.post_TV_title.setText(post.getTitle());
        postViewHolder.post_TV_description.setText(post.getDescription());
        if (post.getImage() != null) {
            postViewHolder.post_IV_image.setVisibility(View.VISIBLE);
            Bitmap img = SharedMethods.base64ToBitmap(post.getImage());
            postViewHolder.post_IV_image.setImageBitmap(img);
        }
        Date date = post.getTimestamp().toDate();

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        postViewHolder.post_TV_createTime.setText(sdf.format(date));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends  RecyclerView.ViewHolder {
        public  TextView post_TV_title;
        public TextView post_TV_description;
        public ImageView post_IV_image;
        public TextView post_TV_createTime;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            post_TV_title = itemView.findViewById(R.id.post_TV_title);
            post_TV_description = itemView.findViewById(R.id.post_TV_description);
            post_IV_image = itemView.findViewById(R.id.post_IV_image);
            post_TV_createTime = itemView.findViewById(R.id.post_TV_createTime);
        }
    }
}
