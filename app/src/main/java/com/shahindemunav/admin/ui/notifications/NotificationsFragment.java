package com.shahindemunav.admin.ui.notifications;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.admin.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private AdepterForPaymane adepter;
    private ArrayList<PaymentData>list;
    private RecyclerView paymentrecylerView;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        paymentrecylerView=root.findViewById(R.id.peymentInformation_Recyler);
        reference= FirebaseDatabase.getInstance().getReference("users");
       paymentrecylerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        paymentrecylerView.setHasFixedSize(true);
        progressDialog=new ProgressDialog(getContext());

        getData_In_To_Recyler_View();






        return root;
    }

    private void getData_In_To_Recyler_View() {
        progressDialog.setMessage("Featch Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int id=0;

                list=new ArrayList<>();


                for (DataSnapshot snapshot1:snapshot.getChildren()){

                    if (snapshot1.hasChild("paymentInformetion")){
                        reference.child(snapshot1.getKey()).child("paymentInformetion").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                PaymentData data=snapshot.getValue(PaymentData.class);
                                list.add(0,data);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }else {
                        id++;
                        Toast.makeText(getActivity(), "NO Payment IDs Found "+id, Toast.LENGTH_SHORT).show();

                    }



                }

                adepter=new AdepterForPaymane(getContext(),list);
                adepter.notifyDataSetChanged();
                paymentrecylerView.setAdapter(adepter);
                progressDialog.dismiss();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}