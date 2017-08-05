package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Model.LoanReport;
import com.onestechsolution.onestechgoldsolution.Model.StockReport;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.w3c.dom.Text;

public class LoanReportActivity extends AppCompatActivity {
    private static String TAG = "LoanReportActivity";
    LoanReport loanReport;
    TextView tvNewLoans, tvClosedLoans, tvCustomers, tvAmount, tvPrinciplePaid,
    tvPrinciplePending, tvPenaltyPaid, tvEMIPaid, tvEMIPending, tvEMIPending3,
    tvEMIPending6, tvDiscount, tvGrossWeight, tvNetWeight, tvRing, tvStud, tvChain,
    tvNecklace, tvEarrings, tvBracelet, tvKalamani, tvEarchain, tvDollar, tvHaar,
    tvBangles, tvOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_report);
        loanReport = new LoanReport();
        loanReport = getIntent().getExtras().getParcelable("loanReport");
        Log.i(TAG, "onCreate: loanReport.getTotalBangles: " + loanReport.getTotalBangles());
        tvNewLoans = (TextView) findViewById(R.id.tv_NewLoan_LoanReportActivity);
        tvClosedLoans = (TextView) findViewById(R.id.tv_ClosedLoan_LoanReportActivity);
        tvCustomers = (TextView) findViewById(R.id.tv_TotalCustomers_LoanReportActivity);
        tvAmount = (TextView) findViewById(R.id.tv_LoanAmount_LoanReportActivity);
        tvPrinciplePaid = (TextView) findViewById(R.id.tv_PrinciplePaid_LoanReportActivity);
        tvPrinciplePending = (TextView) findViewById(R.id.tv_PrinciplePending_LoanReportActivity);
        tvPenaltyPaid = (TextView) findViewById(R.id.tv_PenaltyPaid_LoanReportActivity);
        tvEMIPaid = (TextView) findViewById(R.id.tv_EMIPaid_LoanReportActivity);
        tvEMIPending = (TextView) findViewById(R.id.tv_EMIPending_LoanReportActivity);
        tvEMIPending3 = (TextView) findViewById(R.id.tv_EMIPending3_LoanReportActivity);
        tvEMIPending6 = (TextView) findViewById(R.id.tv_EMIPending6_LoanReportActivity);
        tvDiscount = (TextView) findViewById(R.id.tv_Discount_LoanReportActivity);
        tvGrossWeight = (TextView) findViewById(R.id.tv_GrossWeight_LoanReportActivity);
        tvNetWeight = (TextView) findViewById(R.id.tv_NetWeight_LoanReportActivity);
        tvRing = (TextView) findViewById(R.id.tv_RingCount_LoanReportActivity);
        tvStud = (TextView) findViewById(R.id.tv_StudCount_LoanReportActivity);
        tvChain = (TextView) findViewById(R.id.tv_ChainCount_LoanReportActivity);
        tvNecklace = (TextView) findViewById(R.id.tv_NecklaceCount_LoanReportActivity);
        tvEarrings = (TextView) findViewById(R.id.tv_EarringsCount_LoanReportActivity);
        tvBracelet = (TextView) findViewById(R.id.tv_BraceletCount_LoanReportActivity);
        tvKalamani = (TextView) findViewById(R.id.tv_KalamaniCount_LoanReportActivity);
        tvEarchain = (TextView) findViewById(R.id.tv_EarchainCount_LoanReportActivity);
        tvDollar = (TextView) findViewById(R.id.tv_DollarCount_LoanReportActivity);
        tvHaar = (TextView) findViewById(R.id.tv_HaarCount_LoanReportActivity);
        tvBangles = (TextView) findViewById(R.id.tv_BanglesCount_LoanReportActivity);
        tvOthers = (TextView) findViewById(R.id.tv_OthersCount_LoanReportActivity);
        setTextViewValues();
    }

    public void setTextViewValues() {
        tvNewLoans.setText(loanReport.getTotalNewLoans());
        tvClosedLoans.setText(loanReport.getTotalClosedLoan());
        tvCustomers.setText(loanReport.getTotalCustomer());
        tvAmount.setText(loanReport.getTotalLoanAmount());
        tvPrinciplePaid.setText(loanReport.getTotalPrinciplePaid());
        tvPrinciplePending.setText(loanReport.getTotalPrinciplePending());
        tvPenaltyPaid.setText(loanReport.getTotalPenaltyAmountPaid());
        tvEMIPaid.setText(loanReport.getTotalEMIPaid());
        tvEMIPending.setText(loanReport.getTotalEMIAmountPending());
        tvEMIPending3.setText(loanReport.getTotalEMIMonthsPending3());
        tvEMIPending6.setText(loanReport.getTotalEMIMonthsPending6());
        tvDiscount.setText(loanReport.getTotalDiscount());
        tvGrossWeight.setText(loanReport.getTotalGrossWeight());
        tvNetWeight.setText(loanReport.getTotalNetWeight());
        tvRing.setText(loanReport.getTotalRings());
        tvChain.setText(loanReport.getTotalChains());
        tvStud.setText(loanReport.getTotalStuds());
        tvNecklace.setText(loanReport.getTotalNecklaces());
        tvEarrings.setText(loanReport.getTotalEarrings());
        tvBracelet.setText(loanReport.getTotalBracelets());
        tvKalamani.setText(loanReport.getTotalKalamanis());
        tvEarrings.setText(loanReport.getTotalEarrings());
        tvDollar.setText(loanReport.getTotalDollars());
        tvHaar.setText(loanReport.getTotalHaars());
        tvBangles.setText(loanReport.getTotalBangles());
        tvOthers.setText(loanReport.getTotalOthers());
    }

    public void openLoanEntryReport(View view) {
        if(Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, LoanEntryReportActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }

    }
}
