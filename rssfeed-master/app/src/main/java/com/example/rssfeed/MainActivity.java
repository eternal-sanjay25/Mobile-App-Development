package com.example.rssfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.rssfeed.adapter.RssAdapter;
import com.example.rssfeed.viewmodel.RssViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String RSS_URL = "https://feeds.bbci.co.uk/news/rss.xml";

    private RssViewModel viewModel;
    private RssAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        adapter = new RssAdapter(new ArrayList<>(), item -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RssViewModel.class);

        viewModel.rssItems.observe(this, items -> adapter.updateItems(items));

        viewModel.isLoading.observe(this, loading ->
                swipeRefresh.setRefreshing(loading));

        viewModel.error.observe(this, err -> {
            if (err != null)
                Toast.makeText(this, err, Toast.LENGTH_LONG).show();
        });

        swipeRefresh.setOnRefreshListener(() -> viewModel.loadFeed(RSS_URL));
        viewModel.loadFeed(RSS_URL);
    }
}