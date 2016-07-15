package com.salyert.swarathesh.newsreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by swarathesh on 3.07.16.
 */
public class NewsAdapter extends BaseAdapter {

    private static final String TAG = "Log_adapter";
    private ArrayList newsList;
    private LayoutInflater layoutInflater;
    public NewsAdapter(Context context, ArrayList<NewsItem> newsList) {
        this.newsList = newsList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return newsList.size();
    }
    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.title_textview);
            holder.descTextView = (TextView) convertView.findViewById(R.id.time_textview);
            holder.urlTextView = (TextView) convertView.findViewById(R.id.url_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsItem currentNews = (NewsItem) getItem(position);
        holder.nameTextView.setText(currentNews.getWebTitle());
        holder.descTextView.setText(currentNews.getWebPublicationDate());
        holder.urlTextView.setText(currentNews.getWebUrl());
        String url = currentNews.getImageUrl();
        if (holder.imageView != null) {
            new DownloadImagesTask(url).execute(holder.imageView);
        }
        return convertView;
    }
    private class ViewHolder {
        TextView nameTextView;
        TextView descTextView;
        TextView urlTextView;
        ImageView imageView;
    }


    class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        ImageView imageView = null;
        String url;

        public DownloadImagesTask(String url) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(this.imageView);
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            Log.d(TAG, "DownloadImagesTask doInBackground starts");

            this.imageView = imageViews[0];
            return downloadBitmap(url);
        }

        // Download and decode image in background.
        private Bitmap downloadBitmap(String url) {
            Log.d(TAG, "URL downloadBitmap: " + url);
            HttpURLConnection urlConnection = null;
            URL urlImage;

            try {
                urlImage = new URL(url);

                urlConnection = (HttpURLConnection) urlImage.openConnection();
                int statusCode = urlConnection.getResponseCode();
                Log.d(TAG, "statusCode image urlConnection: " + statusCode);
                if (statusCode == HttpURLConnection.HTTP_OK) {

                    InputStream inputStream = urlConnection.getInputStream();

                    if (inputStream != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    }

                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.d(TAG, "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (imageViewReference != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Log.d(TAG, "onPostExecute: imageView is null");
                Drawable placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.ic_launcher);
                imageView.setImageDrawable(placeholder);
            }


        }
    }
}
