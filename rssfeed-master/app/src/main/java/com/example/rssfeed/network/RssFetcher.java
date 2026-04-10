package com.example.rssfeed.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RssFetcher {

    private static final OkHttpClient client = new OkHttpClient();

    public static String fetch(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}