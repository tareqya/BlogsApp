package com.example.blogsapp.callback;

import com.example.blogsapp.database.Research;

import java.util.ArrayList;

public interface ResearchCallBack {
    void onFetchResearchesComplete(ArrayList<Research> researches);
}
