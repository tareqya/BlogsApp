package com.example.blogsapp.database;

import androidx.annotation.Nullable;

import com.example.blogsapp.callback.ResearchCallBack;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ResearchController {
    public static final String RESEARCH_TABLE = "Researches";
    public static final String COMMENTS = "COMMENTS";
    private FirebaseFirestore db;
    private ResearchCallBack researchCallBack;

    public ResearchController() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void setResearchCallBack(ResearchCallBack researchCallBack){
        this.researchCallBack = researchCallBack;
    }

    public void fetchResearches(){
        this.db.collection(RESEARCH_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null){
                    return;
                }

                ArrayList<Research> researches = new ArrayList<>();
                for(int i = 0 ; i < value.getDocuments().size(); i++){
                    DocumentSnapshot doc = value.getDocuments().get(i);
                    Research research = doc.toObject(Research.class);
                    research.setUid(doc.getId());
                    researches.add(research);
                }
                researchCallBack.onFetchResearchesComplete(researches);
            }
        });
    }

    public void updateResearch(Research research){
        this.db.collection(RESEARCH_TABLE).document(research.getUid()).set(research);
    }

    public void addCommentToResearch(String researchId, Comment comment) {
        this.db.collection(RESEARCH_TABLE).document(researchId)
                .collection(COMMENTS)
                .add(comment);
    }

    public void fetchComments(String researchId) {
        this.db.collection(RESEARCH_TABLE).document(researchId).collection(COMMENTS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Comment> comments = new ArrayList<>();
                        if(value == null){
                            researchCallBack.onFetchCommentsComplete(comments);
                            return;
                        }
                        for(int i = 0 ; i < value.getDocuments().size(); i++){
                            DocumentSnapshot doc = value.getDocuments().get(i);
                            Comment comment = doc.toObject(Comment.class);
                            comment.setUid(doc.getId());
                            comments.add(comment);
                        }
                        researchCallBack.onFetchCommentsComplete(comments);
                    }
                });
    }
}
