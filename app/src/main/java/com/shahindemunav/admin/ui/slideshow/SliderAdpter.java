package com.shahindemunav.admin.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.admin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdpter extends RecyclerView.Adapter<SliderAdpter.AdminViewHolder> {

    private Context context;
    private String pb;
    private ArrayList<SliderData>list;
    private SliderData data;
    private DatabaseReference databaseReference,ref,Count;

    private ProgressDialog progressDialog;

    public SliderAdpter(Context context, ArrayList<SliderData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SliderAdpter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_for_slider_mantenes,parent,false);

        progressDialog=new ProgressDialog(context);

        ref= FirebaseDatabase.getInstance().getReference();

        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdpter.AdminViewHolder holder, @SuppressLint("RecyclerView") int position) {

        data=list.get(position);


        ref= FirebaseDatabase.getInstance().getReference("slider/"+list.get(position).getKey());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               pb= (String) snapshot.child("pub").getValue();
                holder.privet.setText(pb);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Picasso.get().load(data.imageUrl).into(holder.imageViewl);

        holder.privet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading");
                progressDialog.show();
                String h=holder.privet.getText().toString();
                if (pb.equals("Public")){


                    ref= FirebaseDatabase.getInstance().getReference("slider/"+list.get(position).getKey());
                    ref.child("pub").setValue("Privet").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            progressDialog.dismiss();
                           notifyDataSetChanged();



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });
                }else {
                    ref= FirebaseDatabase.getInstance().getReference();
                    ref= FirebaseDatabase.getInstance().getReference("slider/"+list.get(position).getKey());
                    ref.child("pub").setValue("Public").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                           notifyDataSetChanged();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                            progressDialog.dismiss();
                        }
                    });
                }


            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Removing......");
              ref.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      snapshot.getRef().removeValue();
                      progressDialog.dismiss();
                      notifyDataSetChanged();
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                      progressDialog.dismiss();
                      Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                  }
              });

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewl;
        private Button delete,privet,updatetitile;


        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewl=itemView.findViewById(R.id.r_image);
            delete=itemView.findViewById(R.id.r_delete);
            privet=itemView.findViewById(R.id.r_privet);
            updatetitile=itemView.findViewById(R.id.changetitile);


        }
    }
}
