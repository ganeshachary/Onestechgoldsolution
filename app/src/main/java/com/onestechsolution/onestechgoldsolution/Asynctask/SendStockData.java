package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.StockHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.Stock;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/8/2017.
 */

public class SendStockData extends AsyncTask<Stock, Integer, String> {
    private static final float maxHeight = 800.0f;
    private static final float maxWidth = 800.0f;
    private static String TAG = "SendStockData";
    String message;
    private Context context;
    private String username, password;
    private ProgressDialog progressDialog;

    public SendStockData(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading data to the server..");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Stock... params) {
        String line = "";
        String data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/uploadStockDetails.php";
        SetURL setURL = new SetURL(context);
        String link = setURL.SendStockData;

        //String link =SetURL.SendStockData;
        boolean status = true;
        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        Stock stock = params[0];
        Uri uris = stock.getItemPhoto();
        //Log.i(TAG, "doInBackground: stock.getItemPhoto(): "+stock.getItemPhoto());
        BufferedReader reader = null;

        try {
            url = new URL(link);
            sb = new StringBuilder();
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            String encodedImage = Utility.getStringImage(compressImage(uris));
            Log.i(TAG, "doInBackground: encodedImage: " + encodedImage);
            String fileUriName = uris.getPath();
            Log.i(TAG, "doInBackground: fileUriName: " + fileUriName);
            fileUriName = fileUriName.substring(fileUriName.indexOf('G'));
            fileUriName = fileUriName.substring(fileUriName.indexOf('/') + 1);

            data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(encodedImage, "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8") + "=" +
                    URLEncoder.encode(fileUriName, "UTF-8");

            wr.write(data);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                //break;
            }
            Log.i(TAG, "doInBackground: Response after uploading image, sb: " + sb);

            JSONObject jsonObject = new JSONObject(sb.toString());

            if ((jsonObject.getBoolean("status"))) {
                status = false;

            }

            Log.i(TAG, "doInBackground: Image data Response: " + sb.toString());

            if(status) {


                url = new URL(link);
                sb = new StringBuilder();
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

                data = "";
                data += URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
                //Log.i(TAG, "doInBackground: username: " + username+ " password: "+password);
                data += "&" + URLEncoder.encode("json", "UTF-8")
                        + "=" + URLEncoder.encode(stock.getJSON(), "UTF-8");
                Log.i(TAG, "doInBackground: json: " + stock.getJSON());

                outputStreamWriter.write(data);
                outputStreamWriter.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                Log.i(TAG, "doInBackground: sb: " + sb);

                JSONObject jsonObject2 = new JSONObject(sb.toString());
                message = jsonObject2.getString("message");

                Log.i(TAG, "doInBackground: message: " + message);
            } else {
                sb.append("Data not inserted");
            }

            return message;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPostExecute: s: " + s);
        Intent intent = new Intent(context, StockHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, StockHomeActivity.class));
    }


    private Bitmap compressImage(Uri imageUri) {
        if (imageUri != null) {
            String imagePath = imageUri.getPath();

            //String imagePath = imageUri.uri.getPath();
            Log.i(TAG, "compressImage: imagePath: " + imagePath);
            //compressImage: imagePath: /external_files/Pictures/CameraDemo/IMG_20170524_170346.jpg
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;

            float imgRatio = (float) actualWidth / (float) actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(imagePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

            if (bmp != null) {
                bmp.recycle();
            }

            ExifInterface exif;
            try {
                exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return scaledBitmap;
        } else {
            //Toast.makeText(context, "Some problem with image Uri. Contact support.", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "compressImage: Images are not captured. Hence uri not found");
            return null;
        }

    }
}
