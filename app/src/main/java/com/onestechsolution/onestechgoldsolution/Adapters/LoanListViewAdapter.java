package com.onestechsolution.onestechgoldsolution.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OnesTech on 20/05/2017.
 */

public class LoanListViewAdapter extends BaseAdapter implements View.OnClickListener {
    private static LayoutInflater inflater = null;
    private static String TAG = "LoanListViewAdapter";
    //private NewLoan newLoan;
    //private ArrayList<LoanListItem> data;
    private ArrayList<NewLoan> data;
    private Context context;

    public LoanListViewAdapter(Context context, ArrayList<NewLoan> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
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
            vi = inflater.inflate(R.layout.loanlistview, null);
        }
        //Log.i(TAG, "getView: position: "+position);
        TextView customerName = (TextView) vi.findViewById(R.id.tv_WorkerName_WorkerListView); //
        TextView phone = (TextView) vi.findViewById(R.id.tv_Phone_WorkerListView); //
        TextView loanid = (TextView) vi.findViewById(R.id.tv_LoginId_WorkerListView); //
        TextView amount = (TextView) vi.findViewById(R.id.tv_Address_WorkerListView); //
        ImageView custPhoto = (ImageView) vi.findViewById(R.id.iv_CustPhoto_LoanListView); //
        NewLoan dataitem;

        dataitem = data.get(position);
        // Setting all values in listview
        customerName.setText(dataitem.getCustomerName());
        phone.setText(dataitem.getPhone());
        loanid.setText(dataitem.getLoanid());
        amount.setText(dataitem.getAmount());

        Log.i(TAG, "getView: dataItem.getPhotourl: " + dataitem.getCustPhotoUri());
        //new DownloadImageTask(custPhoto).execute("https://qsf.ec.quoracdn.net/-3-images.new_grid.profile_pic_default_small.png-26-902da2b339fedf49.png");
        if(dataitem.getCustPhotoUri()!=null)
            //new DownloadImageTask(custPhoto).execute(SetURL.URLUploads+dataitem.getCustPhotoUri());
            Picasso.with(context)
                    .load(SetURL.URLUploads + dataitem.getCustPhotoUri())
                    //.placeholder(R.drawable.ic_user)   // optional
                    .noPlaceholder()
                    .error(R.drawable.ic_customer)      // optional
                    .resize(50, 50)                        // optional
                    .into(custPhoto);
        else
            custPhoto.setImageResource(R.drawable.error);
        /*Picasso.with(context)
                .load(SetURL.URLUploads+dataitem.getCustPhotoUri())
                .placeholder(R.drawable.ic_user)   // optional
                .error(R.drawable.error)      // optional
                .resize(50, 50)                        // optional
                .into(custPhoto);*/

        return vi;


    }

    @Override
    public void onClick(View v) {
    }


}
