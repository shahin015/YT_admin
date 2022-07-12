package com.shahindemunav.admin.ui.slideshow;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shahindemunav.admin.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {


    private ImageView sliderImge;
    private EditText titile;
    private Spinner count;
    private Button selectphto,upload,btn_public,sliderMantincess;
    private SliderData sliderData;
    private DatabaseReference databaseReference,refcount;

    private final int REQ=1;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);




        sliderImge=root.findViewById(R.id.s_imageView);
        titile=root.findViewById(R.id.titile);
        sliderMantincess=root.findViewById(R.id.slidermantines);
        btn_public=root.findViewById(R.id.public_btn);
        count=root.findViewById(R.id.spiner);
        selectphto=root.findViewById(R.id.selectphoto);
        upload=root.findViewById(R.id.upload);
        databaseReference= FirebaseDatabase.getInstance().getReference("slider");
        refcount= FirebaseDatabase.getInstance().getReference("Count");

        storageReference= FirebaseStorage.getInstance().getReference();
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
        sliderMantincess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        return root;
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
        databaseReference.child(unikey).setValue(new SliderData(dewonloadURl,titile,pub)).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        if (requestCode ==REQ) {
            //TODO: action
            sliderImge.setImageBitmap(bitmap);
        }
    }
}