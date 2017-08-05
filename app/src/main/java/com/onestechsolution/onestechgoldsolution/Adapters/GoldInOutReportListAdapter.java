package com.onestechsolution.onestechgoldsolution.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Model.GoldInOutReport;
import com.onestechsolution.onestechgoldsolution.Model.Worker;
import com.onestechsolution.onestechgoldsolution.Model.WorkerGold;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OnesTech on 03/08/2017.
 */

public class GoldInOutReportListAdapter extends BaseAdapter implements View.OnClickListener{
    private static LayoutInflater inflater = null;
    private static String TAG = "GoldInOutReportAdapter";
    //private NewLoan newLoan;
    private ArrayList<WorkerGold> workerGoldArrayList;
    //private ArrayList<NewLoan> workerArrayList;
    private Context context;

    public GoldInOutReportListAdapter(Context context, ArrayList<WorkerGold> workerGoldArrayList) {
        this.context = context;
        this.workerGoldArrayList = workerGoldArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (workerGoldArrayList.size() <= 0)
            return 1;
        return workerGoldArrayList.size();
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
            vi = inflater.inflate(R.layout.goldinoutreportlistview, null);
        }
        TextView tvWorkerLoginId = (TextView) vi.findViewById(R.id.tv_WorkerId_GoldInOutReportList);
        TextView tvGoldOut = (TextView) vi.findViewById(R.id.tv_GoldOut_GoldInOutReportList);
        TextView tvGoldIn = (TextView) vi.findViewById(R.id.tv_GoldIn_GoldInOutReportList);
        TextView tvBalance = (TextView) vi.findViewById(R.id.tv_GoldBalance_GoldInOutReportList);

        WorkerGold dataitem = workerGoldArrayList.get(position);
        Log.i(TAG, "getView: goldIn "+dataitem.getGoldIn());
        if(dataitem!=null) {
            tvWorkerLoginId.setText(dataitem.getWorkerLoginId());
            tvGoldOut.setText(dataitem.getGoldOut());
            tvGoldIn.setText(dataitem.getGoldIn());
            tvBalance.setText(dataitem.getBalance());

            Log.i(TAG, "getView: Balance: "+dataitem.getBalance());
        } else {
            Log.i(TAG, "getView: dataitem is null");
        }
        // Setting all values in listview
        

        return vi;
    }

    @Override
    public void onClick(View v) {
    }

}
