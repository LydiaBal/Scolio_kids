package ntua.hci.scolio_kids.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import androidx.support.app.ActionBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ntua.hci.scolio_kids.R;
import ntua.hci.scolio_kids.ui.login.MemberKid;

import java.io.IOException;
import java.util.UUID;

public class UploadImages extends AppCompatActivity {

    private FirebaseAuth auth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    // views for button
    private Button btnSelect, btnUpload;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_images);

//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable
//                = new ColorDrawable(
//                Color.parseColor("#0F9D58"));
//        actionBar.setBackgroundDrawable(colorDrawable);

        // initialise views
        btnSelect = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });
    }

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            String thisimage = UUID.randomUUID().toString();

            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + thisimage);
            Log.i( "hola", thisimage);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(this,
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                String sthing = "";
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                    ref.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {

                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //Log.i( "hola2", "SUCCESSSSS");
                                            Uri this_uri = uri;
                                            sthing = this_uri.toString();
                                            Log.i( "hola2", sthing);

                                            DatabaseReference references = FirebaseDatabase.getInstance().getReference();
                                            references.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //String uri = ref.getDownloadUrl().toString();
                                                    Intent intent = getIntent();
                                                    String str_name = intent.getStringExtra("name_key");
                                                    String what1 = snapshot.child("users").child(uid).child("children").child(str_name).child("one").getValue(String.class);
                                                    String what2 = snapshot.child("users").child(uid).child("children").child(str_name).child("two").getValue(String.class);
                                                    String what3 = snapshot.child("users").child(uid).child("children").child(str_name).child("three").getValue(String.class);
                                                    String what4 = snapshot.child("users").child(uid).child("children").child(str_name).child("four").getValue(String.class);
                                                    String what5 = snapshot.child("users").child(uid).child("children").child(str_name).child("five").getValue(String.class);
                                                    if(what1.equals(""))
                                                        reference.child("users").child(uid).child("children").child(str_name).child("one").setValue(sthing);
                                                    else if (what2.equals(""))
                                                        reference.child("users").child(uid).child("children").child(str_name).child("two").setValue(sthing);
                                                    else if (what3.equals(""))
                                                        reference.child("users").child(uid).child("children").child(str_name).child("three").setValue(sthing);
                                                    else if (what4.equals(""))
                                                        reference.child("users").child(uid).child("children").child(str_name).child("four").setValue(sthing);
                                                    else if (what5.equals(""))
                                                        reference.child("users").child(uid).child("children").child(str_name).child("five").setValue(sthing);
                                                    else {
                                                        Toast.makeText(UploadImages.this, "Max number of pictures reached ", Toast.LENGTH_SHORT).show();
                                                        Log.i("max","Max number of pictures reached ");
                                                    }
                                                    //Log.i( "hola-inOnDataChange", sthing);
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

//                                            FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, dlUri.toString());
//                                            mMessagesDatabaseReference.push().setValue(friendlyMessage);
                                        }
                                    });



                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(UploadImages.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(UploadImages.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });

        }
    }
}