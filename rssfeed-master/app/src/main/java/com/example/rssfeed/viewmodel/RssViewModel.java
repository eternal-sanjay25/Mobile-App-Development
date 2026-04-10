package com.example.rssfeed.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.rssfeed.model.RssItem;
import com.example.rssfeed.network.RssFetcher;
import com.example.rssfeed.parser.RssParser;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RssViewModel extends ViewModel {

    public MutableLiveData<List<RssItem>> rssItems  = new MutableLiveData<>();
    public MutableLiveData<Boolean>       isLoading = new MutableLiveData<>();
    public MutableLiveData<String>        error     = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void loadFeed(String url) {
        isLoading.postValue(true);
        error.postValue(null);

        executor.execute(() -> {
            try {
                String xml = RssFetcher.fetch(url);
                if (xml != null) {
                    List<RssItem> items = RssParser.parse(xml);
                    rssItems.postValue(items);
                } else {
                    error.postValue("Failed to fetch RSS feed.");
                }
            } catch (Exception e) {
                error.postValue("Error: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}