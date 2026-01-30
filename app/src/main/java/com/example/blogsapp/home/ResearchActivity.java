package com.example.blogsapp.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blogsapp.R;
import com.example.blogsapp.database.Comment;
import com.example.blogsapp.database.Research;
import com.example.blogsapp.database.ResearchController;

import java.time.LocalDateTime;

public class ResearchActivity extends AppCompatActivity {
    private TextView research_TV_title;
    private TextView research_TV_category;
    private TextView research_TV_description;
    private Button research_BTN_AddComment;
    private Research research;
    private ResearchController researchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_research);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        research = (Research) intent.getSerializableExtra(ResearchFragment.RESEARCH_KEY);
        
        initViews();
        initVars();
    }

    private void initViews() {
        research_TV_title = findViewById(R.id.research_TV_title);
        research_TV_category = findViewById(R.id.research_TV_category);
        research_TV_description = findViewById(R.id.research_TV_description);
        research_BTN_AddComment = findViewById(R.id.research_BTN_AddComment);

    }

    private void initVars() {
        researchController = new ResearchController();

        research_TV_title.setText(research.getTitle());
        research_TV_category.setText(research.getCategory());
        research_TV_description.setText(research.getDescription());

        research_BTN_AddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCommentDialog();
            }
        });
    }

    private void showAddCommentDialog() {

        // Container
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 30, 40, 10);

        // Text area
        EditText etComment = new EditText(this);
        etComment.setHint("اكتب تعليقك هنا");
        etComment.setMinLines(4);
        etComment.setGravity(Gravity.TOP | Gravity.START);
        etComment.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        layout.addView(etComment,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("إضافة تعليق")
                .setView(layout)
                .setPositiveButton("حفظ", null)
                .setNegativeButton("إلغاء", (d, w) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            Button saveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            saveBtn.setOnClickListener(v -> {
                String comment = etComment.getText().toString().trim();

                if (comment.isEmpty()) {
                    etComment.setError("التعليق فارغ");
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // TODO: add the user
                    LocalDateTime timestamp = LocalDateTime.now();
                    Comment comment1 = new Comment()
                            .setContent(comment)
                            .setTimestamp(timestamp)
                            .setAuthor(null);
                    research.getComments().add(comment1);
                    researchController.updateResearch(research);
                    Toast.makeText(this, "تم حفظ التعليق", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            });
        });

        dialog.show();
    }


}