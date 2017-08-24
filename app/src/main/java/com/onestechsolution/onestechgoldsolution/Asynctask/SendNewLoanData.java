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
import android.widget.ImageView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.LoanHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by Admin on 5/28/2017.
 */

public class SendNewLoanData extends AsyncTask<NewLoan, Integer, String> {
    private static final float maxHeight = 600.0f; //1280.0f;
    private static final float maxWidth = 600.0f; //1280.0f;
    private static final String TAG = "SendNewLoanData";
    String username, password;
    private Context context;
    private ProgressDialog progressDialog;

    public SendNewLoanData(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading data to server");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected String doInBackground(NewLoan... params) {
        String line = "";
        String data = "";
        String message = "";

        StringBuilder sb = null;
        URL url;
        boolean status = true;

        String link = SetURL.SendNewLoanData;

        HttpURLConnection conn;
        //Params[0] is a complete loan object which is passed from NewLoanActivity
        NewLoan loan = params[0];
        Uri uris[] = params[0].getImageUris();
        Log.i(TAG, "doInBackground: uris[]: " + Arrays.toString(params[0].getImageUris()));
        //uris[]: [file:///storage/emulated/0/Pictures/GBVJewellers/GBV_20170816_123642.jpg, file:///storage/emulated/0/Pictures/GBVJewellers/GBV_20170816_123859.jpg, null, null, null, null, null, null, null]
        Log.i("server log", "doInBackground: loan.getLoanId(): " + loan.getLoanid());

        try {

            BufferedReader reader = null;
            url = new URL(link);

            for (int i = 0; i < uris.length; i++) {
                sb = new StringBuilder();

                if (uris[i] == null) {
                    //uris[i] = Uri.parse("android.resource://"+context.getPackageName()+"/drawable/R.drawable.ic_customer");
                    Log.i(TAG, "doInBackground: Break condition was successfull if(uris[i]==null) ");
                    break; //breaks the for loop
                }
                conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                Log.i(TAG, "doInBackground: uris.length: " + uris.length);
                Log.i(TAG, "doInBackground: uris[i]: " + uris[i]);
                //uris[i]: file:///storage/emulated/0/Pictures/GBVJewellers/GBV_20170816_123642.jpg
                String encodedImage = Utility.getStringImage(compressImage(uris[i]));
                Log.i(TAG, "doInBackground: encodedImage: " + encodedImage);
                String fileUriName = uris[i].getPath();
                Log.i(TAG, "doInBackground: fileUriName: " + fileUriName);
                //fileUriName: /storage/emulated/0/Pictures/GBVJewellers/GBV_20170816_152723.jpg
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
                wr.close();

                InputStream inputStream = conn.getResponseCode()<400 ? conn.getInputStream(): conn.getErrorStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                //reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    //break;
                }
                Log.i(TAG, "doInBackground: sb " + sb);
                JSONObject jsonObject = new JSONObject(sb.toString());

                if ((jsonObject.getBoolean("status"))) {
                    status = false;
                    break;
                }

                Log.i(TAG, "doInBackground: Image data Response: " + sb.toString());

            }

            if (status) {
                sb = new StringBuilder();
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //Log.i(TAG, "doInBackground: response code"+conn.getResponseCode()+ " message: "+conn.getResponseMessage());
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                data = "";
                data += URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
                Log.i(TAG, "doInBackground: username: " + username + " password: " + password);

                data += "&" + URLEncoder.encode("json", "UTF-8")
                        + "=" + URLEncoder.encode(loan.getJSON(), "UTF-8");
                Log.i(TAG, "doInBackground: json: " + loan.getJSON());

                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Log.i(TAG, "doInBackground: sb " + sb);
                JSONObject jsonObject = new JSONObject(sb.toString());
                message = jsonObject.getString("message");

                if (!(jsonObject.getBoolean("status"))) {
                    status = false;
                }

                Log.i(TAG, "doInBackground: Text data Response: " + sb.toString());

            } else {
                sb.append("Data not inserted");
            }

            return message;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (params.length == 0 || params[0] == null) {
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.i("server log", "doInBackground: " + s);
        Intent intent = new Intent(context, LoanHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.i(TAG, "calculateInSampleSize: height: "+height+" width: "+width+" reqWidth: "+reqWidth+" reqHeight: "+reqHeight);
        // calculateInSampleSize: height: 3120 width: 4160 reqWidth: 600 reqHeight: 450
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            Log.i(TAG, "calculateInSampleSize: heightRatio: "+heightRatio+ " widthRatio: "+widthRatio+ " inSampleSize: "+inSampleSize);
            //calculateInSampleSize: heightRatio: 7 widthRatio: 7 inSampleSize: 7
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        Log.i(TAG, "calculateInSampleSize: totalPixels: "+totalPixels+" totalReqPixelsCap: "+totalReqPixelsCap);
        // calculateInSampleSize: totalPixels: 1.29792E7 totalReqPixelsCap: 540000.0
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        Log.i(TAG, "calculateInSampleSize: return inSampleSize: "+inSampleSize);
        //calculateInSampleSize: return inSampleSize: 7
        return inSampleSize;
    }

    private Bitmap compressImage(Uri imageUri) {
        if (!(imageUri == null)) {
            String imagePath = imageUri.getPath();

            //String imagePath = imageUri.uri.getPath();
            Log.i(TAG, "compressImage: imagePath: " + imagePath);
            // imagePath: /storage/emulated/0/Pictures/GBVJewellers/GBV_20170816_123642.jpg
            Bitmap scaledBitmap = null;

            //BitmapFactory.Options is a static class
            BitmapFactory.Options options = new BitmapFactory.Options();
            /*
            * Setting the inJustDecodeBounds property to true while decoding avoids memory allocation,
            * returning null for the bitmap object but setting outWidth, outHeight and outMimeType.
            * This technique allows you to read the dimensions and type of the image data prior to construction (and memory allocation) of the bitmap.
            * */

            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
            //decodeFile(String pathName, BitmapFactory.Options opts):  Decode a file path into a bitmap.

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            String imageType = options.outMimeType;

            Log.i(TAG, "compressImage: actualHeight: "+actualHeight+ " actualWidth: "+actualWidth+" imageType: "+imageType);
            //compressImage: actualHeight: 3120 actualWidth: 4160 imageType: image/jpeg

            float imgRatio = (float) actualWidth / (float) actualHeight;
            Log.i(TAG, "compressImage: imgRatio: "+imgRatio);
            //compressImage: imgRatio: 1.3333334

            float maxRatio = maxWidth / maxHeight;
            Log.i(TAG, "compressImage: maxRatio: "+maxRatio);
            //compressImage: maxRatio: 1.0

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    //Image captured in portrait mode
                    Log.i(TAG, "compressImage: Inside portrait mode");
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    //Image is captured in landscape mode
                    Log.i(TAG, "compressImage: Inside landscape mode");
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    Log.i(TAG, "compressImage: Use default Height and width sizes");
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }

            Log.i(TAG, "compressImage: actualHeight: "+actualHeight+ " actualWidth: "+actualWidth+ " imageType: "+imageType+
            " ImageRatio: "+imgRatio+" maxRatio: "+maxRatio);
            //compressImage: actualHeight: 450 actualWidth: 600 imageType: image/jpeg ImageRatio: 0.14423077 maxRatio: 1.0

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            /*
                inSampleSize:
                If set to a value > 1, requests the decoder to subsample the original image, returning a smaller image to save memory.
            */
            options.inJustDecodeBounds = false;
            /*
                inJustDecodeBounds:
            *   If set to true, the decoder will return null (no bitmap), but the out... fields will still be set,
            *   allowing the caller to query the bitmap without having to allocate the memory for its pixels.
            * */

            /* options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;*/

            options.inTempStorage = new byte[16 * 1024];
            //inTempStorage: Temp storage to use for decoding.

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
       /* FileOutputStream out = null;
        String filepath = getFilename();
        try {
            out = new FileOutputStream(filepath);

            //write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       *//* Log.i(TAG, "compressImage: filePath: "+filepath);*//*
        return filepath;*/
            return scaledBitmap;
        } else {
            //Toast.makeText(context, "Some problem with image Uri. Contact support.", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "compressImage: Images are not captured. Hence uri not found");
            return null;
        }

    }

    /*private String getPathFromUri(String imageUri) {
        Uri contentUri = Uri.parse(imageUri);

        Cursor cursor = context.getContentResolver().query(contentUri, null,null,null,null);
        if(cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);

        }
    }*/

   /* public String getFilename() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files/Compressed");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String mImageName="IMG_"+ String.valueOf(System.currentTimeMillis()) +".jpg";
        String mImageName = "GBV_" + timeStamp + ".jpg";
        String uriString = (mediaStorageDir.getAbsolutePath() + "/" + mImageName);
        //uriString: /storage/emulated/0/Android/data/com.onestechsolution.cameramarshmallow/Files/Compressed/GBV_20170527_112321.jpg
        *//*Log.i(TAG, "getFilename: uriString: "+uriString);*//*
        return uriString;
    }*/


}
