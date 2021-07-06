package ntua.hci.scolio_kids.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

import ntua.hci.scolio_kids.R;
import ntua.hci.scolio_kids.ui.login.MemberDocInfo;
import ntua.hci.scolio_kids.ui.login.MemberKid;

public class DoctorMain extends AppCompatActivity {
    private ListView listView;
    List<String> folders = new ArrayList<>();
    List<String> age = new ArrayList<>();
    List<String> height = new ArrayList<>();
    List<String> quest = new ArrayList<>();
    List<String> parents = new ArrayList<>();

    private FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_page);

        auth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.listView);

        ((Button)this.findViewById(R.id.btnSignOut)).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View it) {
                auth.signOut();

                DoctorMain.this.setResult(-1);
                DoctorMain.this.finish();
            }
        });

        CustomListViewAdapter adapter = new CustomListViewAdapter( this, R.layout.list_item, folders);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        folders.clear();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                folders.clear();
                for (DataSnapshot snapshot1 : snapshot.child("users").getChildren()) {
                    String what = snapshot1.child("role").getValue(String.class);
                    String whos = snapshot1.getKey();
                    if (what.equals("Patient")) {
                        String whois = snapshot1.child("assigned_doctor").getValue(String.class);
                        if(whois.equals(uid)){
                            for (DataSnapshot snapshot2 : snapshot1.child("children").getChildren()) {
                                MemberKid info1 = snapshot2.getValue(MemberKid.class);
                                String txt1 = info1.getName();
                                String txt2 = info1.getAge();
                                String txt3 = info1.getHeight();
                                String txt4 = info1.getQuest();
                                age.add(txt2);
                                height.add(txt3);
                                quest.add(txt4);
                                folders.add(txt1);
                                parents.add(whos);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id  +" "+folders.get((int) id) +" "+quest.get((int) id)+" "+height.get((int) id)+ " " +age.get((int) id) + "at position:" + position);
//                reference.child(uid).child("assigned_doctor").setValue(codenames.get((int) id));

                Intent main = new Intent((Context) DoctorMain.this, FilePatientActivity.class);

                main.putExtra("name_key", folders.get((int) id));
                main.putExtra("age_key", age.get((int) id));
                main.putExtra("height_key", height.get((int) id));
                main.putExtra("quest_key", quest.get((int) id));
                main.putExtra("parent_key", parents.get((int) id));

                DoctorMain.this.startActivity(main);
                DoctorMain.this.setResult(-1);
                //DoctorMain.this.finish();
            }
        });


    }

}

