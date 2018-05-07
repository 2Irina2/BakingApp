package com.example.android.bakingapp.Utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class RequestUtils {

    public static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public String makeHttpRequest(String stringUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(stringUrl)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }


}
