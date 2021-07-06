package ntua.hci.scolio_kids.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView
;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ntua.hci.scolio_kids.R;


public class KidImagesActivity extends AppCompatActivity{

    private ListView listView;
    List<String> kidsimages = new ArrayList<>();

    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_images);

        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        CustomListViewAdapterTwo adapter = new CustomListViewAdapterTwo( this, R.layout.list_item_img, kidsimages);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        // create the get Intent object
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str_name = intent.getStringExtra("name_key");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("children").child(str_name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kidsimages.clear();
                String one = dataSnapshot.child("one").getValue(String.class);
                String two = dataSnapshot.child("two").getValue(String.class);
                String three = dataSnapshot.child("three").getValue(String.class);
                String four = dataSnapshot.child("four").getValue(String.class);
                String five = dataSnapshot.child("five").getValue(String.class);
                if (one.equals(" ")) {}
                else if(two.equals(" ")){
                    kidsimages.add(one);
                }
                else if(three.equals(" ")){
                    kidsimages.add(one);
                    kidsimages.add(two);
                }
                else if(four.equals(" ")){
                    kidsimages.add(one);
                    kidsimages.add(two);
                    kidsimages.add(three);
                }
                else if(five.equals(" ")){
                    kidsimages.add(one);
                    kidsimages.add(two);
                    kidsimages.add(three);
                    kidsimages.add(four);
                }
                else{
                    kidsimages.add(one);
                    kidsimages.add(two);
                    kidsimages.add(three);
                    kidsimages.add(four);
                    kidsimages.add(five);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ((FloatingActionButton)this.findViewById(R.id.fab)).setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {

//            // create the get Intent object
//            val intent = intent
//
//            // receive the value by getStringExtra() method
//            // and key must be same which is send by first activity
//            val strname = intent.getStringExtra("name_key")


                Intent mains = new Intent((Context) KidImagesActivity.this, UploadImages.class);
                mains.putExtra("name_key", str_name);
                startActivity(mains);

                KidImagesActivity.this.setResult(Activity.RESULT_OK);
                //KidImagesActivity.this.finish();
            }
        }));

        ((Button)this.findViewById(R.id.end)).setOnClickListener((View.OnClickListener) new View.OnClickListener(){
            public final void onClick (View it){
                Intent info = new Intent((Context) KidImagesActivity.this, PatientsMain.class);
                startActivity(info);

                KidImagesActivity.this.setResult(Activity.RESULT_OK);
                // this.finish();
            }
        });

        ((Button)this.findViewById(R.id.info)).setOnClickListener((View.OnClickListener) new View.OnClickListener(){
                public final void onClick (View it){
                Intent info = new Intent((Context) KidImagesActivity.this, InstructionsActivity.class);
                startActivity(info);

                KidImagesActivity.this.setResult(Activity.RESULT_OK);
           // this.finish();
            }
            });
        }
    }