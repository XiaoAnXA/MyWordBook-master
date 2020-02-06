package com.mask.mywordbook.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MusicHttpUtil {


    public static void requestMusic(String sort, Callback callback){
        String URL = "https://api.uomg.com/api/rand.music?sort="+sort+"&format=json";
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request  = new Request.Builder()
                .url(URL)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
