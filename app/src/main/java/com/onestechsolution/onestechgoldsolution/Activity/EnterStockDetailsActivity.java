package com.onestechsolution.onestechgoldsolution.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendStockData;
import com.onestechsolution.onestechgoldsolution.BuildConfig;
import com.onestechsolution.onestechgoldsolution.Model.Stock;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONException;

public class EnterStockDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EnterStockDetails";
    int spnrItemType = R.id.spnr_Type_EnterStockDetailsActivity;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    Uri fileUri, fileUri1;
    ImageView ivStockPhoto;
    String[] permissionRequired = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //int spnrNoOfStone = R.id.spnr_NoOfStones_EnterStockDetailsActivity;
    String stockId;
    Stock stock;
    private Spinner spinner;
    private String username, password;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_stock_details);

        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        ivStockPhoto = (ImageView) findViewById(R.id.iv_ItemPhoto_EnterStockDetailsActivity);
        stockId = getIntent().getStringExtra("stockId");
        Log.i(TAG, "onCreate: stockId: " + stockId);
        setSpinnerData();
        setDate();
        setSku();
        stock = new Stock();

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
        if(noOfStones.equalsIgnoreCase(null) || noOfStones.isEmpty()) {
            noOfStones = "0";
        }


        if(grossWeight.isEmpty() || grossWeight.equals(null)) {
            Toast.makeText(this, "Please enter the value for gross weight", Toast.LENGTH_SHORT).show();
        } else if(ivStockPhoto.getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.capture_48).getConstantState()) {
            Toast.makeText(this, "Please capture the Item photo", Toast.LENGTH_SHORT).show();
        } else if(date.isEmpty() || date.equals(null)) {
            Toast.makeText(this, "Date cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else if(itemType.isEmpty() || itemType.equalsIgnoreCase("Select")) {
            Toast.makeText(this, "Please select the type of the item.", Toast.LENGTH_SHORT).show();
        } else if(netWeight.isEmpty() || netWeight.equals(null)) {
            Toast.makeText(this, "Please enter the value for Net weight.", Toast.LENGTH_SHORT).show();
        } else if(SKU.isEmpty() || SKU.equals(null)) {
            Toast.makeText(this, "SKU cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else {

            Log.i(TAG, "onUpload: grossWeight: " + grossWeight + " netWeight: " + netWeight + " date: " + date + " itemType: " + itemType +
                    " stoneWeight: " + stoneWeight + " SKU: " + SKU + " size: " + size + " noOfStones: " + noOfStones +
                    " description: " + description);



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

    public void captureItemImage(View view) {

        Log.i(TAG, "captureImage: permissionsGranted: " + askForPermissions());
        if (askForPermissions()) {
            proceedAfterPermission();


            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //fileUri = Uri.fromFile(getOutputMediaFile());
            fileUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    Utility.getOutputMediaFile());
            Log.i(TAG, "captureImage: fileUri: " + fileUri);
            fileUri1 = Uri.fromFile(Utility.getOutputMediaFile());
            Log.i(TAG, "captureImage: fileUri1: " + fileUri1);

            stock.setItemPhoto(fileUri1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri1);
            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } else {
            Toast.makeText(this, "Permissions are not granted for the app to run.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.i(TAG, "onActivityResult: data: "+data.toString());
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this, permissionRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
                if (resultCode == RESULT_OK) {

                    //Setting bitmap on the ImageView
                    ivStockPhoto.setImageURI(fileUri);

                    Log.i(TAG, "onActivityResult: fileUri: " + fileUri);
                    Log.i(TAG, "onActivityResult: fileUri1: " + fileUri1);
                    Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Image was not saved", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private boolean askForPermissions() {
        if (ActivityCompat.checkSelfPermission(this, permissionRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRequired[1])) {
                Log.i(TAG, "askForPermissions: Inside shouldShowRequestPermissionRationale");
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera & Storage permissions to capture and store images related to Gold Items. Please grant the permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(EnterStockDetailsActivity.this, permissionRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionRequired[0], false)) {
                Log.i(TAG, "askForPermissions: Inside permissionStatus.getBoolean(permissionRequired[0], false)");
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(EnterStockDetailsActivity.this);

                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Storage Permissions to capture and store images related to Gold Items. Please grant the required permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(EnterStockDetailsActivity.this, "Go to Permissions to grant camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, permissionRequired, PERMISSION_CALLBACK_CONSTANT);
            }
            //Permission Required
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionRequired[0], true);
            editor.apply();

        } else {
            proceedAfterPermission();
            return true;
        }
        return false;
    }

    private void proceedAfterPermission() {
        Log.i(TAG, "proceedAfterPermission: All permissions are granted");
        //Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {

            //check if all permissions are granted
            boolean allgranted = false;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (askForPermissions()) {
                proceedAfterPermission();
            } else {
                Toast.makeText(this, "Unable to get Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this, permissionRequired[0]) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, permissionRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }


}
