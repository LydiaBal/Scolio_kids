
package ntua.hci.scolio_kids.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


public class PatientImagesActivity extends AppCompatActivity{

    private ListView listView;
    List<String> kidsimages = new ArrayList<>();

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_images);

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
        String str_parent = intent.getStringExtra("parent_key");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(str_parent).child("children").child(str_name);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                //Log.i("HelloListView", "You clicked Item: " + id  +" "+folders.get((int) id) +" "+quest.get((int) id)+" "+height.get((int) id)+ " " +age.get((int) id) + "at position:" + position);
//                reference.child(uid).child("assigned_doctor").setValue(codenames.get((int) id));

                Intent main = new Intent((Context) PatientImagesActivity.this, EditImageActivity.class);

                main.putExtra("image_key", kidsimages.get((int) id));


                PatientImagesActivity.this.startActivity(main);
                PatientImagesActivity.this.setResult(-1);
                //PatientImagesActivity.this.finish();
            }
        });

    }
}