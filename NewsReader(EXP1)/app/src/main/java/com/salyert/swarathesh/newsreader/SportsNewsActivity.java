package com.salyert.swarathesh.newsreader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SportsNewsActivity extends AppCompatActivity {
    private String keyword;
    private final String LOG_TAG = "log.Main";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrieveFeedTask().execute();


    }

    private void updateUI(ArrayList<NewsItem> newsList) {
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new NewsAdapter(getApplicationContext(), newsList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView goUrl = (TextView) view.findViewById(R.id.url_textview);
                Uri webPage = Uri.parse((String) goUrl.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private class RetrieveFeedTask extends AsyncTask {
        ArrayList<NewsItem> newsList = new ArrayList<>();

        @Override
        protected Object doInBackground(Object[] objects) {


            keyword = "sports";
            URL url = null;
            try {
                url = new URL("http://content.guardianapis.com/search?show-fields=thumbnail&q=" + keyword + "&api-key=test");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection connection;
            try {
                connection = url.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    JSONObject json = new JSONObject(builder.toString());
                    Log.d(LOG_TAG, "something messed up json" + json.toString());
                    JSONObject resObject = json.getJSONObject("response");
                    JSONArray results = resObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        NewsItem newsItem = new NewsItem();
                        JSONObject item = results.getJSONObject(i);
                        if (item.has("webTitle")) {
                            String title = item.getString("webTitle");
                            newsItem.setWebTitle(title);
                        }
                        if (item.has("webPublicationDate")) {
                            String date = item.getString("webPublicationDate");
                            newsItem.setWebPublicationDate(date);
                        }
                        if (item.has("webUrl")) {
                            String itemUrl = item.getString("webUrl");
                            newsItem.setWebUrl(itemUrl);
                        }
                        if (item.has("fields")) {
                            JSONObject imageLinks = item.getJSONObject("fields");
                            if (imageLinks.has("thumbnail")) {
                                String imageLink = imageLinks.getString("thumbnail");
                                Log.d(LOG_TAG, "Image URL from json:  " + imageLink);
                                newsItem.setImageUrl(imageLink);
                            }
                        }
                        newsList.add(newsItem);
                        Log.d(LOG_TAG, newsItem.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            updateUI(newsList);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
