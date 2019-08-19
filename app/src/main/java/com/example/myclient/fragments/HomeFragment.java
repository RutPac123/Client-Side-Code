package com.example.myclient.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.myclient.adapters.CardAdapter2;
import com.example.myclient.models.AdsModel;
import com.example.myclient.adapters.CardAdapter;
import com.example.myclient.models.ImagesModel;
import com.example.myclient.R;
import com.example.myclient.activities.WebViewSchema;
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

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class HomeFragment extends Fragment {
    private AVLoadingIndicatorView loadingIndicatorView;
    private RecyclerView listCards;
    private RecyclerView listCards2;
    private CardAdapter adapter;
    private CardAdapter2 adapter2;
    private Intent intent;
    private ArrayList<String> imgurlsList;
    private ArrayList<String> detailstxtList;
    private ArrayList<String> webUrlsList;
    private ArrayList<String> webUrlsList2;
    private ArrayList<String> detailstxtList2;
    private ArrayList<String> imgurlsList2;
    private ArrayList<String> adUrlsList;
    private  FlipperLayout flipperLayout;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("ImagesUrls");
    private CollectionReference collectionReference2 = firestore.collection("Ads");
    private CollectionReference collectionReference3 = firestore.collection("ImageUrlsSecond");
    private String[] webUrls = {"https://stackoverflow.com/questions/37733221/android-horizontal-recyclerview-scroll-direction","https://www.youtube.com/watch?v=A2_6mI7drVQ","https://www142.lunapic.com/editor/?action=save"};
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.home,container,false);

        imgurlsList = new ArrayList<>();
        detailstxtList = new ArrayList<>();
        imgurlsList = new ArrayList<>();
        detailstxtList2 = new ArrayList<>();
        imgurlsList2 = new ArrayList<>();
        adUrlsList = new ArrayList<>();
        webUrlsList = new ArrayList<>();
        webUrlsList2 = new ArrayList<>();

        flipperLayout = myView.findViewById(R.id.flipper_layout);
        loadingIndicatorView = myView.findViewById(R.id.loader);


        listCards = myView.findViewById(R.id.cardlist);
        listCards.setHasFixedSize(true);
        listCards.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        loadImgCardDetails();

        loadMyAds();

        listCards2 = myView.findViewById(R.id.cardlist2);
        listCards2.setHasFixedSize(true);
        listCards2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        loadImgCardDetails2();

        return myView;
    }
    private void loadImgCardDetails(){
        loadingIndicatorView.show();
        Query query = collectionReference.orderBy("desc");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            detailstxtList.clear();
                            imgurlsList.clear();
                            webUrlsList.clear();
                            for (DocumentSnapshot snapshot : task.getResult()){
                                ImagesModel model = snapshot.toObject(ImagesModel.class);
                                String myUrl = model.getUrl();
                                String myDeatails = model.getDesc();
                                String myURLWeb = model.getWebUrl();
                                detailstxtList.add(myDeatails);
                                imgurlsList.add(myUrl);
                                webUrlsList.add(myURLWeb);
                            }

                        }

                        adapter = new CardAdapter(getActivity(),imgurlsList,detailstxtList);
                        listCards.setAdapter(adapter);
                        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                intent = new Intent(getActivity(), WebViewSchema.class);
                                switch (position){
                                    case 0 :
                                        intent.putExtra("url",webUrlsList.get(0));
                                        startActivity(intent);
                                        break;
                                    case 1 :
                                        intent.putExtra("url",webUrlsList.get(1));
                                        startActivity(intent);
                                        break;
                                    case 2 :
                                        intent.putExtra("url",webUrlsList.get(2));
                                        startActivity(intent);
                                        break;
                                    case 3 :
                                        intent.putExtra("url",webUrlsList.get(3));
                                        startActivity(intent);
                                        break;
                                    default:
                                        break;
                                }
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
    private void loadMyAds(){

        Query query = collectionReference2.orderBy("url");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    adUrlsList.clear();
                    for (DocumentSnapshot snapshot : task.getResult()){
                        AdsModel adsModel = snapshot.toObject(AdsModel.class);
                        String myAdUrl = adsModel.getUrl();
                        adUrlsList.add(myAdUrl);
                    }
                }
                for (int i = 0; i < adUrlsList.size(); i++) {
                    FlipperView view = new FlipperView(getActivity());
                    view.setImageUrl(adUrlsList.get(i))// Use one of setImageUrl() or setImageDrawable() functions, otherwise IllegalStateException will be thrown
                            .setImageScaleType(ImageView.ScaleType.CENTER_CROP); //You can use any ScaleType
                    flipperLayout.setScrollTimeInSec(10); //setting up scroll time, by default it's 3 seconds
                    flipperLayout.addFlipperView(view);
                }

            }
        });

    }

    private void loadImgCardDetails2(){
        Query query = collectionReference3.orderBy("desc");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            detailstxtList2.clear();
                            imgurlsList2.clear();
                            webUrlsList2.clear();
                            for (DocumentSnapshot snapshot : task.getResult()){
                                ImagesModel model = snapshot.toObject(ImagesModel.class);
                                String myUrl = model.getUrl();
                                String myDeatails = model.getDesc();
                                String myURLWeb = model.getWebUrl();
                                detailstxtList2.add(myDeatails);
                                imgurlsList2.add(myUrl);
                                webUrlsList2.add(myURLWeb);
                            }
                            loadingIndicatorView.hide();
                        }

                        adapter2 = new CardAdapter2(getActivity(),imgurlsList2,detailstxtList2);
                        listCards2.setAdapter(adapter2);
                        adapter2.setOnItemClickListener(new CardAdapter2.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                intent = new Intent(getActivity(), WebViewSchema.class);
                                switch (position){
                                    case 0 :
                                        intent.putExtra("url",webUrlsList2.get(0));
                                        startActivity(intent);
                                        break;
                                    case 1 :
                                        intent.putExtra("url",webUrlsList2.get(1));
                                        startActivity(intent);
                                        break;
                                    case 2 :
                                        intent.putExtra("url",webUrlsList2.get(2));
                                        startActivity(intent);
                                        break;
                                    case 3 :
                                        intent.putExtra("url",webUrlsList2.get(3));
                                        startActivity(intent);
                                        break;
                                    default:
                                        break;
                                }
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
