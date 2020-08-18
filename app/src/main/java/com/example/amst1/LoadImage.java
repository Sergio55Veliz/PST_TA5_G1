package com.example.amst1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public LoadImage(ImageView imgItem){
        this.imageView = imgItem;
    }

    @Override
    protected Bitmap doInBackground(String... strings){
        String urlLink = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(urlLink).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

}