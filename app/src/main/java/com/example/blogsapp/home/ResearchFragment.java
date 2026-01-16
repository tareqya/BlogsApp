package com.example.blogsapp.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blogsapp.R;
import com.example.blogsapp.callback.ResearchCallBack;
import com.example.blogsapp.database.Research;
import com.example.blogsapp.database.ResearchController;

import java.util.ArrayList;


public class ResearchFragment extends Fragment {
    public static final String[] ResearchCategories = {"شعر", "تدبر لغة القرآن", "اللغة العربية" , "نقاط جدلية", "تحليل نفسي"};

    private ResearchController researchController;
    private Context context;
    public ResearchFragment(Context context) {
        researchController = new ResearchController();
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_research, container, false);
        initViews(view);
        initVars();
        return  view;
    }
    private void initViews(View view) {

    }

    private void initVars() {
        researchController.setResearchCallBack(new ResearchCallBack() {
            @Override
            public void onFetchResearchesComplete(ArrayList<Research> researches) {
                Toast.makeText(context, "researches fetched " + researches.size(), Toast.LENGTH_SHORT).show();
            }
        });

        researchController.fetchResearches();
    }




}