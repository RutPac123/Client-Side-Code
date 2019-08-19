package com.example.myclient.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myclient.models.ProjectModel;
import com.example.myclient.adapters.ProjectsAdapter;
import com.example.myclient.R;
import com.example.myclient.activities.ProjectDetails;
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

public class DashFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("Projects");
    private String clientName1;
    private ArrayList<String> projectNameList;
    private ArrayList<String> partynamelist;
    private ArrayList<String> statusList;
    private ArrayList<String> acclist;
    private ArrayList<String> projDesList;
    private ArrayList<String> vstatlist;
    private ArrayList<String> tstatlist;
    private ArrayList<String> difflist;
    private ArrayList<String> amountlist;
    private ArrayList<String> totallist;
    private ArrayList<String> quantlist;
    private ArrayList<String> disclist;
    private ArrayList<String> dofbooklist;
    private ArrayList<String> doComlist;
    private ArrayList<String> doDelList;
    private ArrayList<String> projIdList;
    private ArrayList<String> deltolist;
    private ArrayList<String> clientlist;
    private ArrayList<String> idList;
    private String accNo;
    private String parname;
    private String partyName;
    private String id;
    private String status;
    private String accountNo;
    private String projectDesc;
    private String vstat;
    private String tstat;
    private String diff;
    private String amount;
    private String quant;
    private String disc;
    private String total;
    private int projectId;
    private String dateOfBook;
    private String delTo;
    private String comdate;
    private String deldate;
    private String clientName;
    private AVLoadingIndicatorView loadingIndicatorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.dash,container,false);

        recyclerView = myView.findViewById(R.id.listOfProjects);
        loadingIndicatorView = myView.findViewById(R.id.loader);

        projectNameList = new ArrayList<>();
        partynamelist = new ArrayList<>();
        statusList = new ArrayList<>();
        acclist = new ArrayList<>();
        projDesList = new ArrayList<>();
        vstatlist = new ArrayList<>();
        tstatlist = new ArrayList<>();
        difflist = new ArrayList<>();
        amountlist = new ArrayList<>();
        totallist = new ArrayList<>();
        quantlist = new ArrayList<>();
        disclist = new ArrayList<>();
        dofbooklist = new ArrayList<>();
        doComlist = new ArrayList<>();
        doDelList = new ArrayList<>();
        projIdList = new ArrayList<>();
        deltolist = new ArrayList<>();
        clientlist = new ArrayList<>();
        idList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setMyRecyclerView();

        SharedPreferences pref = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        clientName1 = pref.getString("client",null);
        return myView;
    }

    private void setMyRecyclerView() {
        loadingIndicatorView.show();

        SharedPreferences pref = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        clientName1 = pref.getString("client",null);
        accNo = pref.getString("accno",null);
        Query query = collectionReference.whereEqualTo("clientName",clientName1).whereEqualTo("acNo",accNo);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    projectNameList.clear();
                    partynamelist.clear();
                    statusList.clear();
                    acclist.clear();
                    projDesList.clear();
                    vstatlist.clear();
                    tstatlist.clear();
                    difflist.clear();
                    amountlist.clear();
                    totallist.clear();
                    statusList.clear();
                    quantlist.clear();
                    disclist.clear();
                    dofbooklist.clear();
                    doComlist.clear();
                    doDelList.clear();
                    projIdList.clear();
                    deltolist.clear();
                    clientlist.clear();
                    idList.clear();

                    for (DocumentSnapshot snapshot : task.getResult()){
                        ProjectModel projectModel = snapshot.toObject(ProjectModel.class);
                        id = snapshot.getId();
                        partyName = projectModel.getParty_Name();
                        status = projectModel.getStatus();
                        vstat= projectModel.getVideoData();
                        tstat = projectModel.getTitleData();
                        diff = projectModel.getDiff();
                        accountNo = projectModel.getAcNo();
                        projectDesc = projectModel.getProjectDes();
                        amount = projectModel.getAmount();
                        quant = projectModel.getQty();
                        disc = projectModel.getDiscount();
                        total = projectModel.getTotalAmount();
                        projectId = projectModel.getProjectId();
                        dateOfBook = projectModel.getBookingDate();
                        delTo = projectModel.getDeliverTo();
                        comdate = projectModel.getCompleteDate();
                        deldate = projectModel.getDeliveryDate();
                        clientName = projectModel.getClientName();


                        projectNameList.add(projectDesc);
                        partynamelist.add(partyName);
                        statusList.add(status);
                        acclist.add(accountNo);
                        projDesList.add(projectDesc);
                        vstatlist.add(vstat);
                        tstatlist.add(tstat);
                        difflist.add(diff);
                        amountlist.add(amount);
                        totallist.add(total);
                        quantlist.add(quant);
                        disclist.add(disc);
                        dofbooklist.add(dateOfBook);
                        doComlist.add(comdate);
                        doDelList.add(deldate);
                        deltolist.add(delTo);
                        clientlist.add(clientName);
                        projIdList.add(String.valueOf(projectId));
                        idList.add(id);
                    }
                }

                ProjectsAdapter projectsAdapter = new ProjectsAdapter(getActivity(),partynamelist,projectNameList,statusList);
                recyclerView.setAdapter(projectsAdapter);
                loadingIndicatorView.hide();

                projectsAdapter.setOnItemClickListener(new ProjectsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ProjectDetails.class);
                        intent.putExtra("part_name",partynamelist.get(position));
                        intent.putExtra("status",statusList.get(position));
                        intent.putExtra("accountNo",acclist.get(position));
                        intent.putExtra("projectDesc",projDesList.get(position));
                        intent.putExtra("vstat",vstatlist.get(position));
                        intent.putExtra("tastat",tstatlist.get(position));
                        intent.putExtra("diff",difflist.get(position));
                        intent.putExtra("amount",amountlist.get(position));
                        intent.putExtra("quant",quantlist.get(position));
                        intent.putExtra("disc",disclist.get(position));
                        intent.putExtra("total",totallist.get(position));
                        intent.putExtra("projectId",projIdList.get(position));
                        intent.putExtra("dateOfBook",dofbooklist.get(position));
                        intent.putExtra("delTo",deltolist.get(position));
                        intent.putExtra("id",idList.get(position));
                        intent.putExtra("com",doComlist.get(position));
                        intent.putExtra("deldate",doDelList.get(position));
                        intent.putExtra("clName",clientlist.get(position));
                        startActivity(intent);
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error !", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
