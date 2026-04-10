package com.example.miniproject1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView mainCard;
    private View uploadArea;
    private RecyclerView recyclerView;
    private View progressLayout;
    private LinearProgressIndicator mainProgressBar;
    private TextView tvStatus;
    private MaterialButton btnUpload, btnCopyLink;

    private FileAdapter adapter;
    private List<FileModel> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        mainCard = findViewById(R.id.mainCard);
        uploadArea = findViewById(R.id.uploadArea);
        recyclerView = findViewById(R.id.recyclerView);
        progressLayout = findViewById(R.id.progressLayout);
        mainProgressBar = findViewById(R.id.mainProgressBar);
        tvStatus = findViewById(R.id.tvStatus);
        btnUpload = findViewById(R.id.btnUpload);
        btnCopyLink = findViewById(R.id.btnCopyLink);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileAdapter(fileList);
        recyclerView.setAdapter(adapter);

        // Initial Fade-in Animation
        mainCard.setAlpha(0f);
        mainCard.setTranslationY(100f);
        mainCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(800)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        // Listeners
        btnUpload.setOnClickListener(v -> simulateUpload());

        uploadArea.setOnClickListener(v -> {
            Toast.makeText(this, "File Selection Opened", Toast.LENGTH_SHORT).show();
        });

        btnCopyLink.setOnClickListener(v -> {
            Toast.makeText(this, "Link copied to clipboard!", Toast.LENGTH_SHORT).show();
        });
    }

    private void simulateUpload() {
        if (btnUpload.getText().toString().equalsIgnoreCase("Reset")) {
            resetUI();
            return;
        }

        btnUpload.setEnabled(false);
        progressLayout.setVisibility(View.VISIBLE);
        tvStatus.setText("Uploading...");
        mainProgressBar.setProgress(0);

        // Animate progress from 0 to 100
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mainProgressBar, "progress", 0, 100);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onUploadCompleted();
            }
        });
        progressAnimator.start();
    }

    private void onUploadCompleted() {
        tvStatus.setText("Completed!");
        btnUpload.setEnabled(true);
        btnUpload.setText("Reset");
        btnCopyLink.setVisibility(View.VISIBLE);
        
        // Add dummy file to list
        fileList.add(new FileModel("Project_Presentation.pdf", "12.4 MB"));
        recyclerView.setVisibility(View.VISIBLE);
        adapter.notifyItemInserted(fileList.size() - 1);
        
        // Simple scale animation for the card when finished
        mainCard.animate()
                .scaleX(1.02f)
                .scaleY(1.02f)
                .setDuration(200)
                .withEndAction(() -> mainCard.animate().scaleX(1f).scaleY(1f).setDuration(200).start())
                .start();
    }

    private void resetUI() {
        fileList.clear();
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
        btnCopyLink.setVisibility(View.GONE);
        btnUpload.setText("Send Files");
        mainProgressBar.setProgress(0);
    }

    // Simple Data Model
    static class FileModel {
        String name;
        String size;

        FileModel(String name, String size) {
            this.name = name;
            this.size = size;
        }
    }

    // RecyclerView Adapter
    static class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
        private final List<FileModel> files;

        FileAdapter(List<FileModel> files) {
            this.files = files;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            FileModel file = files.get(position);
            holder.fileName.setText(file.name);
            holder.fileSize.setText(file.size);
            holder.progressBar.setProgress(100); 
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView fileName, fileSize;
            LinearProgressIndicator progressBar;

            ViewHolder(View itemView) {
                super(itemView);
                fileName = itemView.findViewById(R.id.fileName);
                fileSize = itemView.findViewById(R.id.fileSize);
                progressBar = itemView.findViewById(R.id.itemProgressBar);
            }
        }
    }
}
