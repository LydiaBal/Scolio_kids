package ntua.hci.scolio_kids.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import ntua.hci.scolio_kids.main.FileKidActivity;
import ntua.hci.scolio_kids.main.PatientsMain;

public class ChooseDoctor extends AppCompatActivity {
    private ListView listView;
    List<String> names = new ArrayList<>();
    List<String> codenames = new ArrayList<>();

    private FirebaseAuth auth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_doctor);

        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_choice, names);

        listView.setAdapter(adapter);



        auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                names.clear();
                for (DataSnapshot snapshot1 : snapshot.child("users").getChildren()) {
                    String dockey =snapshot1.getKey();
                    String what = snapshot1.child("role").getValue(String.class);
                    if (what.equals("Doctor")) {
                        MemberDocInfo who = snapshot1.child("info").getValue(MemberDocInfo.class);
                        names.add(who.getName() + ", "+ who.getCity()+ ", " + who.getAddress());
                        codenames.add(dockey);
                    }
//                    MemberKid info1 = snapshot1.getValue(MemberKid.class);
//                    String txt1 = info1.getName();
//                    folders.add(txt1);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id  +" "+names.get((int) id) +" "+codenames.get((int) id) + " at position:" + position);
                reference.child("users").child(uid).child("assigned_doctor").setValue(codenames.get((int) id));

                Intent main = new Intent((Context)ChooseDoctor.this, PatientsMain.class);
                ChooseDoctor.this.startActivity(main);
                ChooseDoctor.this.setResult(-1);
                ChooseDoctor.this.finish();
            }
        });

    }

}
