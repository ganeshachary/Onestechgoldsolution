package com.onestechsolution.onestechgoldsolution.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Asynctask.DownloadImageTask;
import com.onestechsolution.onestechgoldsolution.Model.Worker;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 6/6/2017.
 */

public class WorkerListAdapter extends BaseAdapter implements View.OnClickListener {
    private static LayoutInflater inflater = null;
    private static String TAG = "LoanListViewAdapter";
    //private NewLoan newLoan;
    private ArrayList<Worker> workerArrayList;
    //private ArrayList<NewLoan> workerArrayList;
    private Context context;

    public WorkerListAdapter(Context context, ArrayList<Worker> workerArrayList) {
        this.context = context;
        this.workerArrayList = workerArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (workerArrayList.size() <= 0)
            return 1;
        return workerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.workerlistview, null);
        }
        TextView workerName = (TextView) vi.findViewById(R.id.tv_WorkerName_WorkerListView); //
        TextView phone = (TextView) vi.findViewById(R.id.tv_Phone_WorkerListView); // a
        TextView loginId = (TextView) vi.findViewById(R.id.tv_LoginId_WorkerListView); //
        TextView address = (TextView) vi.findViewById(R.id.tv_Address_WorkerListView); //
        ImageView workerPhoto = (ImageView) vi.findViewById(R.id.iv_WorkerPhoto_WorkerListView); // thumb image
        Worker dataitem = workerArrayList.get(position);

        // Setting all values in listview
        workerName.setText(dataitem.getName());
        phone.setText(dataitem.getPhone());
        loginId.setText(dataitem.getWorkerId());
        address.setText(dataitem.getAddress());

        Log.i(TAG, "getView: dataItem.getPhotourl: " + dataitem.getImageNameForList());
        //new DownloadImageTask(thumb_image).execute("https://qsf.ec.quoracdn.net/-3-images.new_grid.profile_pic_default_small.png-26-902da2b339fedf49.png");
        //new DownloadImageTask(thumb_image).execute("http://192.168.0.103/GBVJewellers/ImageTesting/uploads/GBV_20170616_113211.jpg");
        if(dataitem.getImageNameForList()!=null) {
            //new DownloadImageTask(thumb_image).execute(SetURL.URLUploads+dataitem.getWorkerPhoto());
            Picasso.with(context)
                    .load(SetURL.URLUploads + dataitem.getImageNameForList())
                    //.placeholder(R.drawable.ic_user)   // optional
                    .noPlaceholder()
                    .error(R.drawable.error)      // optional
                    .resize(50, 50)                        // optional
                    .into(workerPhoto);
        }
        else {
            workerPhoto.setImageResource(R.drawable.error);
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
    }
}

