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

import com.onestechsolution.onestechgoldsolution.Asynctask.SendNewLoanData;
import com.onestechsolution.onestechgoldsolution.BuildConfig;
import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.LoanIdGenerator;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.util.Arrays;

public class NewLoanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "NewLoanActivity";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String mCurrentPhotoPath;
    String[] permissionRequired = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ImageView currentImage;
    NewLoan newLoan;
    Uri fileUri, fileUri1;
    String username, password;
    TextView tv_loanid;
    Spinner spinner;
    int linearLayout[] = new int[]{
            R.id.ll_Item1_NewLoanActivity, R.id.ll_Item2_NewLoanActivity, R.id.ll_Item3_NewLoanActivity, R.id.ll_Item4_NewLoanActivity,
            R.id.ll_Item5_NewLoanActivity, R.id.ll_Item6_NewLoanActivity, R.id.ll_Item7_NewLoanActivity, R.id.ll_Item8_NewLoanActivity
    };
    int itemTypeSpinner[] = new int[]{
            R.id.spnr_item1_itemlist_loan_activity, R.id.spnr_item2_itemlist_loan_activity, R.id.spnr_item3_itemlist_loan_activity, R.id.spnr_item4_itemlist_loan_activity,
            R.id.spnr_item5_itemlist_loan_activity, R.id.spnr_item6_itemlist_loan_activity, R.id.spnr_item7_itemlist_loan_activity, R.id.spnr_item8_itemlist_loan_activity
    };
    int itemCountSpinner[] = new int[]{
            R.id.spnr_item1_type_count_loan_activity, R.id.spnr_item2_type_count_loan_activity, R.id.spnr_item3_type_count_loan_activity, R.id.spnr_item4_type_count_loan_activity,
            R.id.spnr_item5_type_count_loan_activity, R.id.spnr_item6_type_count_loan_activity, R.id.spnr_item7_type_count_loan_activity, R.id.spnr_item8_type_count_loan_activity
    };
    int percentageSpinner = R.id.spnr_Percentage_NewLoanActivity;
    int itemTypeCountSpinner = R.id.spnr_ItemTypeCount_NewLoanActivity;
    int editItemsWeightIds[] = {
            R.id.et_Item1Weight_NewLoanActivity, R.id.et_Item2Weight_NewLoanActivity, R.id.et_Item3Weight_NewLoanActivity, R.id.et_Item4Weight_NewLoanActivity,
            R.id.et_Item5Weight_NewLoanActivity, R.id.et_Item6Weight_NewLoanActivity, R.id.et_Item7Weight_NewLoanActivity, R.id.et_Item8Weight_NewLoanActivity
    };
    int etCurrentGoldRate = R.id.et_CurrentGoldRate_NewLoanActivity;
    int etName = R.id.et_Name_NewLoanActivity;
    int etContact = R.id.et_Contact_NewLoanActivity;
    int etAmount = R.id.et_Amount_NewLoanActivity;
    int tvLoanId = R.id.tv_Loanid_NewLoanActivity;
    int tvDate = R.id.tv_Date_NewLoanActivity;
    int tvLoanRateValue = R.id.tv_LoanRateValue_NewLoanActivity;
    int etLoanRate = R.id.et_LoanRate_NewLoanActivity;
    int etNetWeight = R.id.et_NetWeight_NewLoanActivity;
    int etGrossWeight = R.id.et_GrossWeight_NewLoanActivity;
    int etDescription = R.id.et_Description_NewLoanActivity;
    int etAddress = R.id.et_Address_NewLoanActivity;
    int etPan = R.id.et_PAN_NewLoanActivity;
    int etAadhar = R.id.et_Aadhar_NewLoanActivity;
    int ivImages[] = {R.id.iv_CustomerPhoto_NewLoanActivity, R.id.iv_Item1_NewLoanActivity, R.id.iv_Item2_NewLoanActivity, R.id.iv_Item3_NewLoanActivity, R.id.iv_Item4_NewLoanActivity,
            R.id.iv_Item5_NewLoanActivity, R.id.iv_Item6_NewLoanActivity, R.id.iv_Item7_NewLoanActivity, R.id.iv_Item8_NewLoanActivity};
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan);
        newLoan = new NewLoan();
        setLoanId();
        setDate();
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        setSpinnerDataAndListener();
    }

    private void setDate() {
        ((TextView) findViewById(R.id.tv_Date_NewLoanActivity)).setText(Utility.getDate());
    }

    public void setSpinnerDataAndListener() {
        setSpinnerAdapter(itemTypeCountSpinner, R.array.item_type_count);

        setSpinnerAdapter(percentageSpinner, R.array.loan_percentage);

        for (int spinnerId : itemTypeSpinner) {
            setSpinnerAdapter(spinnerId, R.array.item_type);
        }

        for (int spinnerId : itemCountSpinner) {
            setSpinnerAdapter(spinnerId, R.array.item_count);
        }
    }

    public void setSpinnerAdapter(int spinnerid, int arrayid) {
        //*******for item_type_count spinner********
        spinner = (Spinner) findViewById(spinnerid);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayid, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void setLoanId() {
        tv_loanid = (TextView) findViewById(R.id.tv_Loanid_NewLoanActivity);
        tv_loanid.setText(LoanIdGenerator.generateUUId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == itemTypeCountSpinner) {
            for (int i = 0; i < linearLayout.length; i++) {
                if (i <= position) {
                    findViewById(linearLayout[i]).setVisibility(View.VISIBLE);
                } else {
                    findViewById(linearLayout[i]).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isImageCaptured() {

        for (int i = 0; i < linearLayout.length; i++) {
            if (findViewById(linearLayout[i]).getVisibility() == View.VISIBLE) {
                //Log.i(TAG, "isImageCaptured: linearyLayout[i]: " + linearLayout[i]);
                if (((ImageView) findViewById(ivImages[i + 1])).getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.capture_48).getConstantState()) {
                    //Toast.makeText(this, "Please capture all the necessary images", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "isImageCaptured: ivImages[i+1]: " + ivImages[i + 1]);
                    return false;
                }
            } else {
                Log.i(TAG, "isImageCaptured: else part i.e. Linear layout is not visible");
                return true;
            }
        }
        return true;
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

    public boolean calculateLoanValue(View view) {

        boolean status = true;
        if (((EditText) findViewById(etCurrentGoldRate)).getText().toString().isEmpty() || ((EditText) findViewById(etCurrentGoldRate)).getText().toString().equals(null)) {
            Toast.makeText(this, "Please enter the current gold rate", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (((EditText) findViewById(etLoanRate)).getText().toString().isEmpty() || ((EditText) findViewById(etLoanRate)).getText().toString().equals(null)) {
            Toast.makeText(this, "Please enter the loan rate", Toast.LENGTH_SHORT).show();
            status = false;
        }
        if (status) {
            double goldRate = Double.parseDouble(((EditText) findViewById(etCurrentGoldRate)).getText().toString());
            double loanRate = Double.parseDouble(((EditText) findViewById(etLoanRate)).getText().toString());
            int loanValue = (int) (goldRate * (loanRate / 100));
            ((TextView) findViewById(R.id.tv_LoanRateValue_NewLoanActivity)).setText(loanValue + "");
        }

        return status;
    }

    public void onUpload(View view) {
        int size = 1;
        String checkSize;
        boolean status = true;
        String contact = ((EditText) findViewById(etContact)).getText().toString();

        //status = calculateLoanValue(view);

        /*newLoan = new NewLoan();*/
        if (((TextView) findViewById(tvLoanId)).getText().toString().isEmpty() || ((TextView) findViewById(tvLoanId)).getText().toString().equals(null)) {
            Toast.makeText(this, "Loan Id cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (((EditText) findViewById(etName)).getText().toString().isEmpty() || ((EditText) findViewById(etName)).getText().toString().equals(null)) {
            Toast.makeText(this, "Please enter the customer name.", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (((ImageView) findViewById(ivImages[0])).getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.capture_48).getConstantState()) {
            Toast.makeText(this, "Please capture the Customers photo", Toast.LENGTH_SHORT).show();
            status = false;
        } else if ((contact.isEmpty() || contact.equals(null) || contact.length() > 15 || contact.length() < 8)) {
            Toast.makeText(this, "Please enter a valid contact number.", Toast.LENGTH_SHORT).show();
            status = false;
        }  else if(!calculateLoanValue(view)) {
            status = false;
        }  else if (((EditText) findViewById(etAmount)).getText().toString().isEmpty() || ((EditText) findViewById(etAmount)).getText().toString().equals(null)) {
            Toast.makeText(this, "Please enter the amount", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (((TextView) findViewById(tvDate)).getText().toString().isEmpty()) {
            Toast.makeText(this, "Date not found. Please contact support.", Toast.LENGTH_SHORT).show();
        } else if (((EditText) findViewById(etNetWeight)).getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter net weight.", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (((EditText) findViewById(etGrossWeight)).getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter gross weight.", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (!isImageCaptured()) {
            Toast.makeText(this, "Please capture all the necessary images. Click on camera icon on the screen to capture images.", Toast.LENGTH_SHORT).show();
            status = false;
        } else if (status) {
            checkSize = (((Spinner) findViewById(R.id.spnr_ItemTypeCount_NewLoanActivity)).getSelectedItem().toString());
            size = Integer.parseInt(checkSize);
            for (int i = 0; i < size; i++) {
                if (((EditText) findViewById(editItemsWeightIds[i])).getText().toString().equals(null) || ((EditText) findViewById(editItemsWeightIds[i])).getText().toString().isEmpty()) {
                    status = false;
                    Toast.makeText(this, "Please enter the Item weights for item: " + ((Spinner) findViewById(itemTypeSpinner[i])).getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }


        if (status) {
            newLoan.setLoanTime(Utility.getTime());
            newLoan.setAddress(((EditText) findViewById(etAddress)).getText().toString());
            newLoan.setPan(((EditText) findViewById(etPan)).getText().toString());
            newLoan.setAadhar(((EditText) findViewById(etAadhar)).getText().toString());
            newLoan.setLoanDate(((TextView) findViewById(tvDate)).getText().toString());
            newLoan.setLoanid(((TextView) findViewById(tvLoanId)).getText().toString());
            newLoan.setCustomerName(((EditText) findViewById(etName)).getText().toString());
            newLoan.setPhone(((EditText) findViewById(etContact)).getText().toString());
            newLoan.setAmount(((EditText) findViewById(etAmount)).getText().toString());
            newLoan.setDescription(((TextView) findViewById(etDescription)).getText().toString());
            newLoan.setPercentage(((Spinner) findViewById(percentageSpinner)).getSelectedItem().toString());
            newLoan.setTypesOfItems(((Spinner) findViewById(itemTypeCountSpinner)).getSelectedItem().toString());
            newLoan.setGrossWeight(((EditText) findViewById(etGrossWeight)).getText().toString());
            newLoan.setNetWeight(((EditText) findViewById(etNetWeight)).getText().toString());
            newLoan.setCurrentGoldRate(((EditText) findViewById(etCurrentGoldRate)).getText().toString());


            String itemWeightValues[] = new String[8];
            for (int i = 0; i < size; i++) {
                itemWeightValues[i] = ((EditText) findViewById(editItemsWeightIds[i])).getText().toString();
                Log.i(TAG, "onSave: Weight[" + i + "]: " + itemWeightValues[i]);
            }
            newLoan.setItemWeight(itemWeightValues);
            Log.i(TAG, "onSave: Item Weight: " + Arrays.toString(newLoan.getItemWeight()));

            String itemTypeValues[] = new String[8];
            for (int i = 0; i < size; i++) {
                itemTypeValues[i] = ((Spinner) findViewById(itemTypeSpinner[i])).getSelectedItem().toString();
                Log.i(TAG, "onSave: Item type[" + i + "]: " + itemTypeValues[i]);
            }
            newLoan.setItemType(itemTypeValues);
            Log.i(TAG, "onSave: Item type: " + Arrays.toString(newLoan.getItemType()));

            String itemCountValues[] = new String[8];
            for (int i = 0; i < size; i++) {
                itemCountValues[i] = ((Spinner) findViewById(itemCountSpinner[i])).getSelectedItem().toString();
                Log.i(TAG, "onSave: Item Count[" + i + "]: " + itemCountValues[i]);
            }
            newLoan.setItemCount(itemCountValues);

            Log.i(TAG, "onSave: Item Count: " + Arrays.toString(newLoan.getItemCount()));

            Log.i(TAG, "onSave: Loan Id: " + newLoan.getLoanid()); //1
            Log.i(TAG, "onSave: Customer Name: " + newLoan.getCustomerName()); //2
            Log.i(TAG, "onSave: Phone: " + newLoan.getPhone()); //3
            Log.i(TAG, "onSave: Amount: " + newLoan.getAmount()); //4
            Log.i(TAG, "onSave: Percentage: " + newLoan.getPercentage()); //5
            Log.i(TAG, "onSave: Description: " + newLoan.getDescription()); //6
            Log.i(TAG, "onSave: No of types of items: " + newLoan.getTypesOfItems()); //7
            Log.i(TAG, "onSave: ImageUris: " + Arrays.toString(newLoan.getImageUris())); //8
            Log.i(TAG, "onSave: Item counts: " + Arrays.toString(newLoan.getItemCount())); //9
            Log.i(TAG, "onSave: Item Weights: " + Arrays.toString(newLoan.getItemWeight())); //10
            Log.i(TAG, "onSave: Item Types: " + Arrays.toString(newLoan.getItemType())); //11
            Log.i(TAG, "onSave: Loan Date: " + newLoan.getLoanDate());
            Log.i(TAG, "onSave: Time: " + newLoan.getLoanTime());
            Log.i(TAG, "onSave: Gross Weight: " + newLoan.getGrossWeight());
            Log.i(TAG, "onUpload: Current Gold Rate: " + newLoan.getCurrentGoldRate());

            if (Utility.isNetworkAvailable(this)) {

                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendNewLoanData(NewLoanActivity.this, username, password).execute(newLoan);
                                //new ImageUpload(this).execute(newLoan);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void captureImage(View view) {
        Log.i(TAG, "captureImage: permissionsGranted: " + askForPermissions());
        if (askForPermissions()) {
            proceedAfterPermission();

            //imageposition was used, so that we can passed it to newLoan.setLoanUrisPosition. Check it below, in this method itself.
            int imageposition = 0;
            for (int i = 0; i < ivImages.length; i++) {
                if (view == findViewById(ivImages[i])) {
                    imageposition = i;
                    currentImage = (ImageView) findViewById(ivImages[i]);
                    Log.i(TAG, "takePicture: currentImage: " + currentImage);
                }
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            /*
                    FileProvider is a special subclass of ContentProvider that facilitates secure sharing of files associated with an app
                    by creating a content:// Uri for a file instead of a file:/// Uri.
                    A content URI allows you to grant read and write access using temporary access permissions.
            */
            // getUriForFile - Return a content URI for a given File.
            fileUri = FileProvider.getUriForFile(NewLoanActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    Utility.getOutputMediaFile());
            Log.i(TAG, "captureImage: fileUri: " + fileUri);
            // fileUri -> content://com.onestechsolution.onestechgoldsolution.provider/external_files/Pictures/GBVJewellers/GBV_20170810_160949.jpg

            /*
                Creates a Uri from a file. The URI has the form "file://". Encodes path characters with the exception of '/'.
                Example: "file:///tmp/android.txt"
            */
            fileUri1 = Uri.fromFile(Utility.getOutputMediaFile());
            Log.i(TAG, "captureImage: fileUri1: " + fileUri1);
            // fileUri1 -> file:///storage/emulated/0/Pictures/GBVJewellers/GBV_20170810_160940.jpg

            newLoan.setLoanUrisPosition(imageposition, fileUri1);
            Log.i(TAG, "captureImage: imageposition: "+imageposition);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri1);
            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } else {
            Log.i(TAG, "captureImage: Permissions are not granted for the app to run. ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(NewLoanActivity.this, permissionRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: currentImage: " + currentImage);
                    if (currentImage.getId() == R.id.iv_CustomerPhoto_NewLoanActivity) {
                        currentImage.setImageURI(fileUri);
                    } else {
                        //after capturing item images set the checked image
                        currentImage.setImageResource(R.drawable.saved_48);
                    }
                    //imageView1.setImageURI(fileUri1);
                    Log.i(TAG, "onActivityResult: fileUri: " + fileUri);
                    Log.i(TAG, "onActivityResult: fileUri1: " + fileUri1);
                    Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
                    //new ImageCompression(this).execute(fileUri.toString());

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Image was not saved", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private boolean askForPermissions() {

        /*ActivityCompat.checkSelfPermission() or ContextCompat.checkSelfPermission() determines whether you have been granted a particular permission
            Above method returns PackageManager.PERMISSION_GRANTED if the permissions are granted. else PERMISSION_DENIED
            Protected Constructor -> ContextCompat():
                This class should not be instantiated, but the constructor must be visible for the class to be extended (ex. in ActivityCompat).
        */

        if (ActivityCompat.checkSelfPermission(NewLoanActivity.this, permissionRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(NewLoanActivity.this, permissionRequired[1]) != PackageManager.PERMISSION_GRANTED) {

            /*
                if the permissions was denied then to help find situations where the user might need an explanation,
                Android provides a utility method, shouldShowRequestPermissionRationale().
                This method returns true if the app has requested this permission previously and the user denied the request.
                The method also returns false if a device policy prohibits the app from having that permission.
            */
            if (ActivityCompat.shouldShowRequestPermissionRationale(NewLoanActivity.this, permissionRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(NewLoanActivity.this, permissionRequired[1])) {
                Log.i(TAG, "askForPermissions: Inside shouldShowRequestPermissionRationale");
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoanActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera & Storage permissions to capture and store images related to Gold Items. Please grant the permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(NewLoanActivity.this, permissionRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } /*else if (permissionStatus.getBoolean(permissionRequired[0], false)) {
                Log.i(TAG, "askForPermissions: Inside permissionStatus.getBoolean(permissionRequired[0], false)");
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoanActivity.this);

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
                        Toast.makeText(NewLoanActivity.this, "Go to Permissions to grant camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }*/ else {
                ActivityCompat.requestPermissions(NewLoanActivity.this, permissionRequired, PERMISSION_CALLBACK_CONSTANT);
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
            } else {
                if(askForPermissions()) {
                    Toast.makeText(this, "Permissions are granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: Permissions are not granted");
                    //Toast.makeText(this, "Unable to get Permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(NewLoanActivity.this, permissionRequired[0]) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(NewLoanActivity.this, permissionRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                proceedAfterPermission();
            }
        }
    }


}
