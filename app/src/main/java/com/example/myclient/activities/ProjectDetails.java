package com.example.myclient.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.wang.avi.AVLoadingIndicatorView;

public class ProjectDetails extends AppCompatActivity {


    private TextView partyName;
    private TextView accountNo;
    private TextView description;
    private TextView dateOfCompletion;
    private TextView dateOfDelivery;
    private TextView statustxt;
    private TextView hasVideotxt;
    private TextView hasTitletxt;
    private TextView difficultytxt;
    private TextView amounttxt;
    private TextView quantitytxt;
    private TextView discounttxt;
    private TextView totaltxt;
    private TextView bookingdatetxt;
    private TextView projectId;
    private TextView delTo;
    private Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);


        projectId = findViewById(R.id.id);
        partyName = findViewById(R.id.myCname);
        accountNo = findViewById(R.id.myaccno);
        description = findViewById(R.id.myPDesc);
        dateOfCompletion = findViewById(R.id.comdatetxt);
        dateOfDelivery = findViewById(R.id.deldatetxt);
        statustxt = findViewById(R.id.mStatus);
        hasVideotxt = findViewById(R.id.videodata);
        hasTitletxt = findViewById(R.id.titledata);
        difficultytxt = findViewById(R.id.diff);
        bookingdatetxt = findViewById(R.id.myDateBook);
        amounttxt = findViewById(R.id.amount);
        quantitytxt = findViewById(R.id.quantity);
        discounttxt = findViewById(R.id.discount);
        totaltxt = findViewById(R.id.total);
        delTo = findViewById(R.id.deliveryto);

        getIntent = getIntent();



        if (getIntent != null){

            String mpartyName = getIntent.getStringExtra("part_name");
            String stat = getIntent.getStringExtra("status");
            String maccountNo = getIntent.getStringExtra("accountNo");
            String projectDesc = getIntent.getStringExtra("projectDesc");
            String vstat = getIntent.getStringExtra("vstat");
            String tstat = getIntent.getStringExtra("tastat");
            String diff = getIntent.getStringExtra("diff");
            String amount = getIntent.getStringExtra("amount");
            String quant = getIntent.getStringExtra("quant");
            String discount = getIntent.getStringExtra("disc");
            String mprojectId = getIntent.getStringExtra("projectId");
            String bookingdate = getIntent.getStringExtra("dateOfBook");
            String deliveryTo = getIntent.getStringExtra("delTo");
            final String totalamnt = getIntent.getStringExtra("total");
            String compldate = getIntent.getStringExtra("com");
            String delidte = getIntent.getStringExtra("deldate");


            description.setText(projectDesc);
            bookingdatetxt.setText(bookingdate);
            statustxt.setText(stat);
            partyName.setText(mpartyName);
            accountNo.setText(maccountNo);
            hasVideotxt.setText(vstat);
            hasTitletxt.setText(tstat);
            difficultytxt.setText(diff);
            amounttxt.setText(amount);
            quantitytxt.setText(quant);
            discounttxt.setText(discount);
            projectId.setText(String.valueOf(mprojectId));
            delTo.setText(deliveryTo);
            totaltxt.setText(totalamnt);
            dateOfCompletion.setText(compldate);
            dateOfDelivery.setText(delidte);

        }

    }
}
