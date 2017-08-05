package com.onestechsolution.onestechgoldsolution.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
import com.onestechsolution.onestechgoldsolution.R;

import java.util.ArrayList;

/**
 * Created by Admin on 6/7/2017.
 */


public class GoldInOutListAdapter extends BaseAdapter implements View.OnClickListener {
    private static LayoutInflater inflater = null;
    private static String TAG = "GoldInOutListAdapter";

    private ArrayList<GoldInOut> goldInOutArrayList;
    //private ArrayList<NewLoan> workerArrayList;
    private Context context;

    public GoldInOutListAdapter(Context context, ArrayList<GoldInOut> goldInOutArrayList) {
        this.context = context;
        this.goldInOutArrayList = goldInOutArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (goldInOutArrayList.size() <= 0)
            return 1;
        return goldInOutArrayList.size();
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
            vi = inflater.inflate(R.layout.gold_in_out_listview, null);
        }
        TextView BillNo = (TextView) vi.findViewById(R.id.tv_BillNo_GoldInOutListView);
        TextView BillBook = (TextView) vi.findViewById(R.id.tv_BillBook_GoldInOutListView);
        TextView loginId = (TextView) vi.findViewById(R.id.tv_LoginId_GoldInOutListView);
        TextView deliveryDate = (TextView) vi.findViewById(R.id.tv_DeliverDate_GoldInOutListView);
        TextView goldIn = (TextView) vi.findViewById(R.id.tv_GoldIn_GoldInOutListView);
        TextView goldOut = (TextView) vi.findViewById(R.id.tv_GoldOut_GoldInOutListView);
        GoldInOut dataitem = goldInOutArrayList.get(position);

        // Setting all values in listview
        BillNo.setText(dataitem.getBillNo());
        BillBook.setText(dataitem.getBillBook());
        loginId.setText(dataitem.getWorkerLoginId());
        deliveryDate.setText(dataitem.getDeliveryDate());
        goldIn.setText(dataitem.getGoldIn());
        goldOut.setText(dataitem.getGoldOut());

        Log.i(TAG, "getView: dataItem.getBillNo: " + dataitem.getBillNo());

        return vi;
    }

    @Override
    public void onClick(View v) {
    }


}

