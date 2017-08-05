package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendStockData;
import com.onestechsolution.onestechgoldsolution.Model.Stock;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONException;

public class EnterStockDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EnterStockDetails";
    int spnrItemType = R.id.spnr_Type_EnterStockDetailsActivity;
    //int spnrNoOfStone = R.id.spnr_NoOfStones_EnterStockDetailsActivity;
    String stockId;
    Stock stock;
    private Spinner spinner;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_stock_details);

        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        stockId = getIntent().getStringExtra("stockId");
        Log.i(TAG, "onCreate: stockId: " + stockId);
        setSpinnerData();
        setDate();
        setSku();

    }

    private void setSku() {
        ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity)).setText(Utility.getSKU());
    }

    private void setDate() {
        ((TextView) findViewById(R.id.tv_Date_EnterStockDetailsActivity)).setText(Utility.getDate());
    }

    private void setSpinnerData() {
        setSpinnerAdapter(spnrItemType, R.array.item_type);
        //setSpinnerAdapter(spnrNoOfStone, R.array.no_of_stones);
    }

    public void setSpinnerAdapter(int spinnerid, int arrayid) {
        spinner = (Spinner) findViewById(spinnerid);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spnrItemType) {
            String itemType = ((Spinner) findViewById(R.id.spnr_Type_EnterStockDetailsActivity)).getSelectedItem().toString();
            TextView tvSKU = ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity));
            if (itemType.equalsIgnoreCase("Ring")) {
                tvSKU.setText("R"  + stockId);
            } else if (itemType.equalsIgnoreCase("Chain")) {
                tvSKU.setText("C"  + stockId);
            } else if (itemType.equalsIgnoreCase("Stud")) {
                tvSKU.setText("S" + stockId);
            } else if (itemType.equalsIgnoreCase("Necklace")) {
                tvSKU.setText("N"  + stockId);
            } else if (itemType.equalsIgnoreCase("Earrings")) {
                tvSKU.setText("E" + stockId);
            } else if (itemType.equalsIgnoreCase("Earchain")) {
                tvSKU.setText("EC"  + stockId);
            } else if (itemType.equalsIgnoreCase("Bracelet")) {
                tvSKU.setText("BR"  + stockId);
            } else if (itemType.equalsIgnoreCase("Kalamani")) {
                tvSKU.setText("K"  + stockId);
            } else if (itemType.equalsIgnoreCase("Dollar")) {
                tvSKU.setText("D"  + stockId);
            } else if (itemType.equalsIgnoreCase("Bangles")) {
                tvSKU.setText("BL"  + stockId);
            } else if (itemType.equalsIgnoreCase("Haar")) {
                tvSKU.setText("H"  + stockId);
            } else if (itemType.equalsIgnoreCase("Others")) {
                tvSKU.setText("O"  + stockId);
            }
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_attention)
                .setTitle("Are you sure you want to go back?")
                .setMessage("All the changes will be lost")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onUpload(View view) throws JSONException {
        String grossWeight = ((EditText) findViewById(R.id.et_GrossWeight_EnterStockDetailsActivity)).getText().toString();
        String date = ((TextView) findViewById(R.id.tv_Date_EnterStockDetailsActivity)).getText().toString();
        String itemType = ((Spinner) findViewById(R.id.spnr_Type_EnterStockDetailsActivity)).getSelectedItem().toString();
        String stoneWeight = ((EditText) findViewById(R.id.et_StoneWeight_EnterStockDetailsActivity)).getText().toString();
        String SKU = ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity)).getText().toString();
        String size = ((EditText) findViewById(R.id.et_Size_EnterStockDetailsActivity)).getText().toString();
        String noOfStones = ((EditText) findViewById(R.id.et_NoOfStones_EnterStockDetailsActivity)).getText().toString();
        String description = ((EditText) findViewById(R.id.et_Description_EnterStockDetailsActivity)).getText().toString();
        String netWeight = ((EditText) findViewById(R.id.et_NetWeight_EnterStockDetailsActivity)).getText().toString();

        if (size.equalsIgnoreCase(null) || size.isEmpty()) {
            size = "0.0";
        }
        if (stoneWeight.equalsIgnoreCase(null) || stoneWeight.isEmpty()) {
            stoneWeight = "0.0";
        }

        if(grossWeight.isEmpty() || grossWeight.equals(null)) {
            Toast.makeText(this, "Please enter the value for gross weight", Toast.LENGTH_SHORT).show();
        } else if(date.isEmpty() || date.equals(null)) {
            Toast.makeText(this, "Date cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else if(netWeight.isEmpty() || netWeight.equals(null)) {
            Toast.makeText(this, "Please enter the value for Net weight.", Toast.LENGTH_SHORT).show();
        } else if(SKU.isEmpty() || SKU.equals(null)) {
            Toast.makeText(this, "SKU cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else {

            Log.i(TAG, "onUpload: grossWeight: " + grossWeight + " netWeight: " + netWeight + " date: " + date + " itemType: " + itemType +
                    " stoneWeight: " + stoneWeight + " SKU: " + SKU + " size: " + size + " noOfStones: " + noOfStones +
                    " description: " + description);


            stock = new Stock();
            stock.setGrossWeight(grossWeight);
            stock.setNetWeight(netWeight);
            stock.setDate(date);
            stock.setType(itemType);
            stock.setStoneWeight(stoneWeight);
            stock.setSku(SKU);
            stock.setSize(size);
            stock.setNoOfStones(noOfStones);
            stock.setDescription(description);
            //String JSON = stock.getJSON();
            //Log.i(TAG, "onUpload: JSON: "+JSON);

            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded. Please once check all the values before uploading.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendStockData(EnterStockDetailsActivity.this, username, password).execute(stock);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
