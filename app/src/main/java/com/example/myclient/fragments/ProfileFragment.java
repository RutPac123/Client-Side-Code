package com.example.myclient.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclient.models.DataModel;
import com.example.myclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {


    private TextView clientName;
    private TextView address;
    private TextView accNo;
    private TextView email;
    private TextView pno;
    private TextView stduioname;
    private TextView remainAmnt;
    private ArrayList<String> totallist;
    private ArrayList<String> paidlist;
    private AVLoadingIndicatorView loadingIndicatorView;


    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("users");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.client_details,container,false);


        clientName = myView.findViewById(R.id.mStatus);
        address = myView.findViewById(R.id.myCname);
        accNo = myView.findViewById(R.id.id);
        email = myView.findViewById(R.id.myaccno);
        pno = myView.findViewById(R.id.myPDesc);
        stduioname = myView.findViewById(R.id.myDateBook);
        remainAmnt = myView.findViewById(R.id.deliveryto);
        loadingIndicatorView = myView.findViewById(R.id.loader);

        totallist = new ArrayList<>();
        paidlist = new ArrayList<>();


        SharedPreferences preferences = getActivity().getSharedPreferences("PREF",MODE_PRIVATE);
        String getAcc = preferences.getString("accno",null);
        loadUserData(getAcc);
        return myView;
    }

    private void loadUserData(final String myaccNo){
        loadingIndicatorView.show();
        Query query = collectionReference.whereEqualTo("acNo",myaccNo);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            totallist.clear();
                            paidlist.clear();
                            for (DocumentSnapshot snapshot : task.getResult()){

                                DataModel model = snapshot.toObject(DataModel.class);
                                String cName = model.getClientName();
                                String maddress = model.getAddress();
                                String maccNo = model.getAcNo();
                                String eId = model.getEmail();
                                String pNo = model.getPhoneNo();
                                String studName = model.getStudioName();
                                String mtotalAmnt = model.getTotalAmount();
                                String mpaidAmnt = model.getTotalAmountPaid();

                                totallist.add(mtotalAmnt);
                                paidlist.add(mpaidAmnt);

                                clientName.setText(cName);
                                address.setText(maddress);
                                accNo.setText(maccNo);
                                email.setText(eId);
                                pno.setText(pNo);
                                stduioname.setText(studName);

                            }
                        }

                        ArrayList<String> nonEmptyListTotal;
                        ArrayList<String> nonEmptyListPaid;
                        nonEmptyListTotal = getNonEmptyList(totallist);
                        nonEmptyListPaid = getNonEmptyList(paidlist);

                            long tot = Long.valueOf(nonEmptyListTotal.get(0));
                            long paid = Long.valueOf(nonEmptyListPaid.get(0));

                            long rem = tot - paid;
                            remainAmnt.setText(String.valueOf(rem));
                        loadingIndicatorView.hide();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error loading data !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private ArrayList<String> getNonEmptyList(ArrayList<String> arrayList){
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).equals("")){
                arrayList.remove(i);
            }
        }
        return arrayList;
    }
}
