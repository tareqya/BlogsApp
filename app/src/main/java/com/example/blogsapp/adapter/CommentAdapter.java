package com.example.blogsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogsapp.R;
import com.example.blogsapp.database.Comment;
import com.example.blogsapp.database.Research;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Comment> comments;
    private Context context;
    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.commentItem_TV_content.setText(comment.getContent());
        String fullName = comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName();
        commentViewHolder.commentItem_TV_name.setText(fullName);
        Timestamp ts = comment.getTimestamp();

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        String formattedTime = sdf.format(ts.toDate());

        commentViewHolder.commentItem_TV_time.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public class CommentViewHolder extends  RecyclerView.ViewHolder {
        public TextView commentItem_TV_content;
        public TextView commentItem_TV_time;
        public TextView commentItem_TV_name;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentItem_TV_name = itemView.findViewById(R.id.commentItem_TV_name);
            commentItem_TV_content = itemView.findViewById(R.id.commentItem_TV_content);
            commentItem_TV_time = itemView.findViewById(R.id.commentItem_TV_time);

        }
    }
}
