package com.shahindemunav.admin.ui.slideshow;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shahindemunav.admin.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {


    private ImageView sliderImge;
    private TextView countText;
    private EditText titile;
    private Spinner count;
    private SliderAdpter adpter;
    private ArrayList<SliderData>list;
    private Button selectphto,upload,btn_public;
    private SliderData sliderData;
    private DatabaseReference databaseReference,refcount,ref;
    private RecyclerView recyclerView;

    private final int REQ=1;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);




        sliderImge=root.findViewById(R.id.s_imageView);
        titile=root.findViewById(R.id.titile);
        countText=root.findViewById(R.id.counttext);
        btn_public=root.findViewById(R.id.public_btn);
        count=root.findViewById(R.id.spiner);
        selectphto=root.findViewById(R.id.selectphoto);
        upload=root.findViewById(R.id.upload);
        recyclerView=root.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        databaseReference= FirebaseDatabase.getInstance().getReference("slider");
        refcount= FirebaseDatabase.getInstance().getReference("Count");

        storageReference= FirebaseStorage.getInstance().getReference();
        ref= FirebaseDatabase.getInstance().getReference();
        progressDialog=new ProgressDialog(getContext());



        setSpnnerData();
        selectphto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhoto();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titiles=titile.getText().toString();
                String pub=btn_public.getText().toString();
                if (titiles.isEmpty()){
                    titile.setError("Enter Photo Titile");
                    titile.requestFocus();
                    return;
                }
                UploadPhoto(titiles,pub);
            }
        });

        countText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Slider Update");
                progressDialog.show();
                refcount.child("count").setValue(count.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                });
            }
        });



        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag= (String) btn_public.getTag();

                if (tag.equals("p")){
                    btn_public.setText("privet");
                    btn_public.setTag("pr");
                }else if (tag.equals("pr")){
                    btn_public.setText("public");
                    btn_public.setTag("p");
                }

            }
        });

        get_data_for_recylerView();
        return root;
    }

    private void get_data_for_recylerView() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                list=new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    SliderData data=snapshot1.getValue(SliderData.class);
                    list.add(data);
                }
                adpter=new SliderAdpter(getContext(),list);
                adpter.notifyDataSetChanged();
                recyclerView.setAdapter(adpter);





                ////  imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/skill-buider-yt.appspot.com/o/slider%2F%5BB%407f400e4.jpg?alt=media&token=ff451b97-f797-40d7-8eaf-c838a6564378", "photoTitile1", null));











            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }


    private void setSpnnerData() {
        final List<String> nomeConsulta = new ArrayList<String>();

        nomeConsulta.add("1");
        nomeConsulta.add("2");
        nomeConsulta.add("3");
        nomeConsulta.add("4");
        nomeConsulta.add("5");
        nomeConsulta.add("6");
        nomeConsulta.add("7");
        nomeConsulta.add("8");
        nomeConsulta.add("9");
        nomeConsulta.add("10");
        nomeConsulta.add("11");
        nomeConsulta.add("12");
        nomeConsulta.add("13");
        nomeConsulta.add("14");
        nomeConsulta.add("15");
        nomeConsulta.add("16");
        nomeConsulta.add("17");
        nomeConsulta.add("18");
        nomeConsulta.add("19");
        nomeConsulta.add("20");
        nomeConsulta.add("21");
        nomeConsulta.add("22");
        nomeConsulta.add("23");
        nomeConsulta.add("24");
        nomeConsulta.add("25");
        nomeConsulta.add("10");
        nomeConsulta.add("26");
        nomeConsulta.add("27");
        nomeConsulta.add("28");
        nomeConsulta.add("29");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nomeConsulta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count.setAdapter(arrayAdapter);


    }








    private void UploadPhoto(String titile,String pub) {


        if (bitmap==null){
            Toast.makeText(getContext(), "Pleace Select photo", Toast.LENGTH_SHORT).show();
            return;
        }else {

            UploadImage_in_firebase(titile,pub);
        }

    }

    private void UploadImage_in_firebase(String titile,String pub) {
        progressDialog.setMessage("Uploading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bos);
        byte[]finalImage=bos.toByteArray();
        final StorageReference filepath;
        filepath=storageReference.child("slider").child(finalImage+".jpg");

        final UploadTask uploadTask=filepath.putBytes(finalImage);

        uploadTask.addOnCompleteListener((Activity) getContext(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String  dewonloadURl=String.valueOf(uri);

                                    Upload_All_Data(dewonloadURl,titile,progressDialog,pub);


                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Image Not Uplaoded", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void Upload_All_Data(String dewonloadURl,String titile ,ProgressDialog progressDialog,String pub) {

        final  String unikey=databaseReference.push().getKey();
        databaseReference.child(unikey).setValue(new SliderData(dewonloadURl,titile,pub,unikey)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                String counts=count.getSelectedItem().toString();

                refcount.child("count").setValue(counts).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                Toast.makeText(getActivity(), "Image Uplaode IS sucessfuly", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Image Uplaode IS Faild", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });






    }

    private void SelectPhoto() {
        Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);


    }

     @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==REQ &&resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            sliderImge.setImageBitmap(bitmap);
        }

    }

}