package com.example.blogsapp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.blogsapp.R;
import com.example.blogsapp.adapter.ResearchAdapter;
import com.example.blogsapp.callback.ResearchAdapterCallBack;
import com.example.blogsapp.callback.ResearchCallBack;
import com.example.blogsapp.database.Comment;
import com.example.blogsapp.database.Research;
import com.example.blogsapp.database.ResearchController;
import com.example.blogsapp.database.User;

import java.util.ArrayList;
import java.util.Arrays;


public class ResearchFragment extends Fragment {
    public static final String RESEARCH_KEY = "RESEARCH";
    public static final String USER_KEY = "USER";
    public static final String[] ResearchCategories = {"شعر", "تدبر لغة القرآن", "اللغة العربية" , "نقاط جدلية", "تحليل نفسي", "الكل"};
    private RecyclerView freg_RV_researches;
    private SearchView freg_search_view;
    private Spinner freg_spinner_category;
    private ArrayList<Research> allResearches;
    private ResearchController researchController;
    private Context context;
    private User user;

    public ResearchFragment(Context context) {
        researchController = new ResearchController();
        this.context = context;
        allResearches = new ArrayList<>();
    }

    public void setUser(User user){
        this.user = user;
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
        freg_spinner_category = view.findViewById(R.id.freg_spinner_category);
        freg_search_view = view.findViewById(R.id.freg_search_view);
        freg_RV_researches = view.findViewById(R.id.freg_RV_researches);
    }

    private void initVars() {
        researchController.setResearchCallBack(new ResearchCallBack() {
            @Override
            public void onFetchResearchesComplete(ArrayList<Research> researches) {
                // set the data to research adapter
                allResearches = researches;
                setResearchesInRecycleView(researches);
            }

            @Override
            public void onFetchCommentsComplete(ArrayList<Comment> comments) {

            }
        });

        researchController.fetchResearches();

        // init spinner with categories
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                ResearchCategories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freg_spinner_category.setAdapter(adapter);
        int defaultIndex = Arrays.asList(ResearchCategories).indexOf("الكل");
        if (defaultIndex >= 0) {
            freg_spinner_category.setSelection(defaultIndex);
        }
        // handle on category changed
        freg_spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                String selectedCategory = ResearchCategories[index];
                if (selectedCategory.equals("الكل")) {
                    // show all researches
                    setResearchesInRecycleView(allResearches);
                } else {
                    //filter the researches by category
                    ArrayList<Research> filteredResearches = new ArrayList<>();
                    for (int i = 0; i < allResearches.size(); i++) {
                        if (allResearches.get(i).getCategory().equals(selectedCategory)) {
                            filteredResearches.add(allResearches.get(i));
                        }
                    }
                    setResearchesInRecycleView(filteredResearches);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // handle on search
        freg_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                //filter the researches by title
                ArrayList<Research> filteredResearches = new ArrayList<>();
                for (int i = 0; i < allResearches.size(); i++) {
                    String title = normalizeArabic(allResearches.get(i).getTitle());
                    if (title.startsWith(s)) {
                        filteredResearches.add(allResearches.get(i));
                    }
                }
                setResearchesInRecycleView(filteredResearches);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });
    }

    private void setResearchesInRecycleView(ArrayList<Research> researches){
        ResearchAdapter researchAdapter = new ResearchAdapter(researches, context);
        researchAdapter.setResearchAdapterCallBack(new ResearchAdapterCallBack() {
            @Override
            public void onResearchSelected(Research research) {
                Intent intent = new Intent(context, ResearchActivity.class);
                intent.putExtra(RESEARCH_KEY, research);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }
        });

        freg_RV_researches.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        freg_RV_researches.setHasFixedSize(true);
        freg_RV_researches.setItemAnimator(new DefaultItemAnimator());
        freg_RV_researches.setAdapter(researchAdapter);

    }

    private String normalizeArabic(String text) {
        if (text == null) return "";

        // Remove Arabic diacritics (Tashkeel)
        text = text.replaceAll("[\\u064B-\\u065F\\u0670\\u06D6-\\u06ED]", "");

        // Normalize common Arabic letter variants
        text = text.replace("أ", "ا")
                .replace("إ", "ا")
                .replace("آ", "ا")
                .replace("ى", "ي")
                .replace("ة", "ه");

        return text.trim();
    }

};