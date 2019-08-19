package com.example.myclient.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclient.models.DataModel;
import com.example.myclient.R;
import com.example.myclient.adapters.SearchAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class OTP extends AppCompatActivity {

    ProgressDialog progress;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private EditText phoneNoEdt;
    private FloatingActionButton b1;
    private CoordinatorLayout coordinatorLayout;
    private EditText accNo;
    public static EditText cName;
    private RecyclerView mySearchedList;
    private ArrayList<String> clientNameList;
    private ImageView okBtnName;
    private Animation animation;
    private Animation animation2;
    private TextView nametxt;
    private TextView enterdettxt;

    private FirebaseFirestore firestore= FirebaseFirestore.getInstance();
    private CollectionReference clientReference = firestore.collection("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_otp);


        animation = AnimationUtils.loadAnimation(this,R.anim.slideleft);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.slideright);


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        phoneNoEdt =  findViewById(R.id.Phonenoedittext);
        b1 = findViewById(R.id.PhoneVerify);
        accNo = findViewById(R.id.accNo);
        coordinatorLayout = findViewById(R.id.coord);
        mySearchedList = findViewById(R.id.searchList);
        cName = findViewById(R.id.clientName);
        okBtnName = findViewById(R.id.okBtn2);
        nametxt = findViewById(R.id.name);
        enterdettxt = findViewById(R.id.entertxt);
        nametxt.startAnimation(animation);
        enterdettxt.startAnimation(animation2);

        clientNameList = new ArrayList<>();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS},10);

        }

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
                Snackbar.make(coordinatorLayout,"Verification complete",Snackbar.LENGTH_SHORT).show();
                progress.hide();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTP.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                    progress.hide();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTP.this, "Something went wrong...please try again", Toast.LENGTH_SHORT).show();
                    progress.hide();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                progress.hide();

            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String myPhoneNumber = phoneNoEdt.getText().toString().trim();
                if(myPhoneNumber.isEmpty() || accNo.getText().toString().isEmpty() || cName.getText().toString().isEmpty()){
                    Snackbar.make(coordinatorLayout,"Empty fields !",Snackbar.LENGTH_SHORT).show();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(OTP.this);
                    builder.setTitle("Alert !");
                    builder.setCancelable(true);
                    builder.setMessage("Are you sure you want to proceed ? Please double check account number !");
                    builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progress = ProgressDialog.show(OTP.this, "Please Wait..",
                                    "Authenticating...", false,true);
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91"+myPhoneNumber,
                                    60,
                                    java.util.concurrent.TimeUnit.SECONDS,
                                    OTP.this,
                                    mCallbacks);   // this wil verify phone number

                            String getAccNo = accNo.getText().toString();
                            SharedPreferences preferences = getSharedPreferences("PREF",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("accno",getAccNo);
                            editor.putString("client",cName.getText().toString());
                            editor.apply();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                  AlertDialog malertDialog = builder.create();
                  malertDialog.show();
                }
            }
        });


        cName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mySearchedList.setVisibility(View.VISIBLE);
                b1.hide();


                if (!s.toString().isEmpty()){
                    setAdapterNew(s.toString());
                }else{
                    clientNameList.clear();
                    mySearchedList.removeAllViews();

                    mySearchedList.setVisibility(View.INVISIBLE);
                    b1.show();

                }
            }

        });
        mySearchedList =findViewById(R.id.searchList);
        mySearchedList.setHasFixedSize(true);
        mySearchedList.setLayoutManager(new LinearLayoutManager(this));
        click(okBtnName);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            OTP.this.startActivity(new Intent(OTP.this, MainActivity.class));

                            progress.hide();

                            OTP.this.finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progress.hide();
                                Snackbar.make(coordinatorLayout,"Error ! Please Try again",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(OTP.this,MainActivity.class));
            finish();
        }
    }

    private void setAdapterNew(final String inputString){
        Query query = clientReference.whereGreaterThanOrEqualTo("clientName", inputString);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int counter =0;
                    clientNameList.clear();
                    mySearchedList.removeAllViews();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        DataModel dataModel = documentSnapshot.toObject(DataModel.class);
                        String clientName = dataModel.getClientName();
                        if (clientName.toLowerCase().contains(inputString.toLowerCase())){
                            clientNameList.add(clientName);
                            counter++;
                        }
                        if (counter==15){
                            break;
                        }
                    }

                }
                SearchAdapter searchAdapter = new SearchAdapter(OTP.this,clientNameList);
                mySearchedList.setAdapter(searchAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OTP.this, "error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void click(ImageView button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientNameList.clear();
                mySearchedList.removeAllViews();
                mySearchedList.setVisibility(View.INVISIBLE);
                b1.show();

            }
        });
    }
}
