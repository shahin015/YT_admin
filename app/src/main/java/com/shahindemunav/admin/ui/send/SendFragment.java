package com.shahindemunav.admin.ui.send;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.admin.R;

import java.util.HashMap;

public class SendFragment extends Fragment {
    private EditText bkash,nagad,roket,upay;
    private String mbkash,mnagad,mroket,mupay;
    private Button update;
    private ProgressDialog progressDialog;

    private DatabaseReference reference;

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel = ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        bkash=root.findViewById(R.id.bkash);
        nagad=root.findViewById(R.id.nagad);
        roket=root.findViewById(R.id.Roket);
        upay=root.findViewById(R.id.Upay);
        update=root.findViewById(R.id.updatMobaile);
        progressDialog=new ProgressDialog(getContext());
        reference= FirebaseDatabase.getInstance().getReference("Mobaile");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Upload_Mobaile();
            }
        });


        CallDataBase();

        return root;
    }

    private void Upload_Mobaile() {
        String bikas=bkash.getText().toString();
        String Nagad=nagad.getText().toString();
        String Roket=roket.getText().toString();
        String Upay=upay.getText().toString();

        if (bikas.isEmpty()||Nagad.isEmpty()||Roket.isEmpty()||Upay.isEmpty()){

            Toast.makeText(getContext(), "Fill All Filds", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Updating....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap<String,String>Mobaile=new HashMap<>();
        Mobaile.put("bKash",bikas);
        Mobaile.put("Nagad",Nagad);
        Mobaile.put("Roket",Roket);
        Mobaile.put("Upay",Upay);

        reference.setValue(Mobaile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    progressDialog.dismiss();

                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void CallDataBase() {
        progressDialog.setMessage("Fatching Number");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mbkash=snapshot.child("bKash").getValue().toString();
                mnagad=snapshot.child("Nagad").getValue().toString();
                mroket=snapshot.child("Roket").getValue().toString();
                mupay=snapshot.child("Upay").getValue().toString();

                if (!mbkash.isEmpty() && !mroket.isEmpty()&& !mupay.isEmpty()&& !mnagad.isEmpty()){

                    bkash.setText(mbkash);
                    nagad.setText(mnagad);
                    roket.setText(mroket);
                    upay.setText(mupay);
                    progressDialog.dismiss();


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }
}