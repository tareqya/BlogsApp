package com.example.blogsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogsapp.R;
import com.example.blogsapp.callback.ResearchAdapterCallBack;
import com.example.blogsapp.database.Research;

import java.util.ArrayList;

public class ResearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Research> researches;
    private Context context;
    private ResearchAdapterCallBack researchAdapterCallBack;

    public ResearchAdapter(ArrayList<Research> researches, Context context){
        this.researches = researches;
        this.context = context;
    }

    public void setResearchAdapterCallBack(ResearchAdapterCallBack researchAdapterCallBack) {
        this.researchAdapterCallBack = researchAdapterCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.research_item, parent, false);
        return new ResearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Research research = researches.get(position);
        ResearchViewHolder researchViewHolder = (ResearchViewHolder) holder;

        researchViewHolder.researchItem_TV_title.setText(research.getTitle());
        researchViewHolder.researchItem_TV_category.setText(research.getCategory());
    }

    @Override
    public int getItemCount() {
        return researches.size();
    }

    public class ResearchViewHolder extends  RecyclerView.ViewHolder {
        public TextView researchItem_TV_title;
        public TextView researchItem_TV_category;

        public ResearchViewHolder(@NonNull View itemView) {
            super(itemView);
            researchItem_TV_title = itemView.findViewById(R.id.researchItem_TV_title);
            researchItem_TV_category = itemView.findViewById(R.id.researchItem_TV_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Research research = researches.get(i);
                    researchAdapterCallBack.onResearchSelected(research);
                }
            });
        }
    }
}
