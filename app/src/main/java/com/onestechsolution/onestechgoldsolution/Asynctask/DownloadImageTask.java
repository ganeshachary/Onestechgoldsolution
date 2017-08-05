package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.onestechsolution.onestechgoldsolution.R;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Admin on 09-Jun-17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static String TAG = "DownloadImageTask";
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        //here we are getting the id of the bitmap
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {

        String link = urls[0];
        Log.i(TAG, "doInBackground: url: "+urls[0]);
        URL url = null;

        Bitmap image = null;
        try {
            url = new URL(link);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        Log.i(TAG, "onPostExecute: result"+result);
        if(result!=null)
            bmImage.setImageBitmap(result);
        else
            bmImage.setImageResource(R.drawable.error);
    }
}
