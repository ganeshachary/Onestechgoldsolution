package com.onestechsolution.onestechgoldsolution.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendAddWorkerData;
import com.onestechsolution.onestechgoldsolution.BuildConfig;
import com.onestechsolution.onestechgoldsolution.Model.Worker;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddWorkerActivity extends AppCompatActivity {
    //just a comment for committing
    private static final String TAG = "AddWorkerActivity";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    TextView tvJoiningDate, tvBirthDate;
    String username, password;
    Uri fileUri, fileUri1;
    String[] permissionRequired = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Calendar calendar = Calendar.getInstance();
    Worker worker;
    ImageView ivWorkerPhoto;
    DatePickerDialog.OnDateSetListener joiningDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateJoiningDateTextView();
        }
    };
    DatePickerDialog.OnDateSetListener dateOfBirth = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateOfBirthTextView();
        }
    };
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        tvJoiningDate = (TextView) findViewById(R.id.tv_JoiningDate_AddWorkerActivity);
        tvBirthDate = (TextView) findViewById(R.id.tv_BirthDate_AddWorkerActivity);
        ivWorkerPhoto = (ImageView) findViewById(R.id.iv_WorkerPhoto_AddWorkerActivity);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: username: " + username + " password: " + password);
        worker = new Worker();
    }

    public void captureImage(View view) {
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

            worker.setWorkerPhoto(fileUri1);
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
                    ivWorkerPhoto.setImageURI(fileUri);

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
                        ActivityCompat.requestPermissions(AddWorkerActivity.this, permissionRequired, PERMISSION_CALLBACK_CONSTANT);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddWorkerActivity.this);

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
                        Toast.makeText(AddWorkerActivity.this, "Go to Permissions to grant camera & storage permission", Toast.LENGTH_SHORT).show();
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

    public void setJoiningDate(View view) {
        if (view == findViewById(R.id.btn_JoiningDate_AddWorkerActivity)) {
            new DatePickerDialog(this, joiningDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == findViewById(R.id.btn_BirthDate_AddWorkerAcitivity)) {
            new DatePickerDialog(this, dateOfBirth, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void getWorkerId(View view) {
        String name = ((EditText) findViewById(R.id.et_Name_AddWorkerActivity)).getText().toString();
        String phone = ((EditText) findViewById(R.id.et_Phone_AddWorkerActivity)).getText().toString();

        String fullName[] = name.split("\\s");
        name = fullName[0];

        if (name.equals(null) || name.isEmpty()) {
            Toast.makeText(this, "Please enter the customerName to generate Id", Toast.LENGTH_SHORT).show();
        } else if (phone.equals(null) || phone.isEmpty()) {
            Toast.makeText(this, "Please enter the phone number to generate Id", Toast.LENGTH_SHORT).show();
        } else {
            String workerId = name + "" + phone + "@gbv";
            ((TextView) findViewById(R.id.tv_WorkerId_AddWorkerActivity)).setText(workerId);
        }
    }

    private void updateJoiningDateTextView() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvJoiningDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void updateDateOfBirthTextView() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvBirthDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void onUpload(View view) {
        Date joinDate=null, birthDate=null;
        /*((EditText) findViewById(R.id.et_Name_AddWorkerActivity)).setText("Mangal");
        ((EditText) findViewById(R.id.et_PAN_AddWorkerActivity)).setText("Pan");
        ((EditText) findViewById(R.id.et_Aadhar_AddWorkerActivity)).setText("Aadhar");
        ((TextView) findViewById(R.id.tv_JoiningDate_AddWorkerActivity)).setText("19/04/2017");
        ((EditText) findViewById(R.id.et_Phone_AddWorkerActivity)).setText("9027900638");
        ((EditText) findViewById(R.id.et_Description_AddWorkerActivity)).setText("Description");
        ((TextView) findViewById(R.id.tv_BirthDate_AddWorkerActivity)).setText("27/08/1992");
        ((EditText) findViewById(R.id.et_Address_AddWorkerActivity)).setText("Address");
        ((TextView) findViewById(R.id.tv_WorkerId_AddWorkerActivity)).setText("Mangal9029700369@gbv");
        ((EditText) findViewById(R.id.et_Salary_AddWorkerActivity)).setText("100000");*/
        //ivWorkerPhoto.setImageResource(R.drawable.ic_user);

        String name = ((EditText) findViewById(R.id.et_Name_AddWorkerActivity)).getText().toString();
        String pan = ((EditText) findViewById(R.id.et_PAN_AddWorkerActivity)).getText().toString();
        String aadhar = ((EditText) findViewById(R.id.et_Aadhar_AddWorkerActivity)).getText().toString();
        String joiningDate = ((TextView) findViewById(R.id.tv_JoiningDate_AddWorkerActivity)).getText().toString();
        String phone = ((EditText) findViewById(R.id.et_Phone_AddWorkerActivity)).getText().toString();
        String description = ((EditText) findViewById(R.id.et_Description_AddWorkerActivity)).getText().toString();
        String dateOfBirth = ((TextView) findViewById(R.id.tv_BirthDate_AddWorkerActivity)).getText().toString();
        String address = ((EditText) findViewById(R.id.et_Address_AddWorkerActivity)).getText().toString();
        String workerId = ((TextView) findViewById(R.id.tv_WorkerId_AddWorkerActivity)).getText().toString();
        String salary = ((EditText) findViewById(R.id.et_Salary_AddWorkerActivity)).getText().toString();

        Log.i(TAG, "onUpload: joiningDate: "+joiningDate+" dateOfBirth: "+dateOfBirth); //joiningDate: 21/07/2017 dateOfBirth: 21/07/2017

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            joinDate = sdf.parse(joiningDate);
            birthDate = sdf.parse(dateOfBirth);
            Log.i(TAG, "onUpload: joinDate: "+joinDate+ " birthDate: "+birthDate);
            Log.i(TAG, "onUpload: joinDate: "+sdf.format(joinDate)+" birthDate: "+sdf.format(birthDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (salary.equals(null) || salary.isEmpty()) {
            salary = "0";
        }

        if (name.equals(null) || name.isEmpty()) {
            Toast.makeText(this, "Please enter name of the worker", Toast.LENGTH_SHORT).show();
        } else if(ivWorkerPhoto.getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.capture_48).getConstantState()) {
            Toast.makeText(this, "Please capture the Workers photo", Toast.LENGTH_SHORT).show();
        }  else if (joiningDate.equals(null) || joiningDate.isEmpty()) {
            Toast.makeText(this, "Please select Joining Date of the worker", Toast.LENGTH_SHORT).show();
        } else if (dateOfBirth.equals(null) || dateOfBirth.isEmpty()) {
            Toast.makeText(this, "Please select Date of Birth of the worker", Toast.LENGTH_SHORT).show();
        } else if (birthDate.compareTo(joinDate) > 0) {
            Log.i(TAG, "onUpload: birthDate is after than joiningDate");
            Toast.makeText(this, "Please select proper date of birth", Toast.LENGTH_SHORT).show();
        } else if (phone.equals(null) || phone.isEmpty() || phone.length()>12 || phone.length()<8) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else if (workerId.equals(null) || workerId.isEmpty()) {
            Toast.makeText(this, "Worker id cannot be empty. Please click on GET ID button", Toast.LENGTH_SHORT).show();
        } else if (address.equals(null) || address.isEmpty()) {
            Toast.makeText(this, "Please enter address of the worker", Toast.LENGTH_SHORT).show();
        }  else {

            worker.setName(name);
            worker.setPan(pan);
            worker.setAadhar(aadhar);
            worker.setSalary(salary);
            worker.setJoiningDate(joiningDate);
            worker.setPhone(phone);
            worker.setDob(dateOfBirth);
            worker.setAddress(address);
            worker.setWorkerId(workerId);
            worker.setDescription(description);

            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded, please once check all the values before uploading")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendAddWorkerData(AddWorkerActivity.this, username, password).execute(worker);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                Log.i(TAG, "onUpload: Worker Details: " + worker.getName() + ", " + worker.getPan() + ", " + worker.getAadhar() + ", " + worker.getAddress() + ", " +
                        worker.getDob() + "," + worker.getJoiningDate() + "," + worker.getPhone() + ", " + worker.getAddress() + ", " + worker.getPan() +
                        ", " + worker.getSalary() + ", " + worker.getWorkerPhoto() + ", " + worker.getDescription() + ", " + worker.getWorkerId());
            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
            /*Toast.makeText(this, "" + worker.getName() + ", " + worker.getPan() + ", " + worker.getAadhar() + ", " + worker.getAddress() + ", " +
                    worker.getDob() + "," + worker.getJoiningDate() + "," + worker.getPhone() + ", " + worker.getAddress() +
                    ", " + worker.getSalary() + ", " + worker.getWorkerPhoto() + ", " + worker.getDescription() + ", " + worker.getWorkerId(), Toast.LENGTH_SHORT).show();*/
        }
    }


}
