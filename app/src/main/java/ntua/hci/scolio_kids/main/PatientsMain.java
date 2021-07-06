//package ntua.hci.scolio_kids.main;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.ktx.Firebase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ntua.hci.scolio_kids.R;
//import ntua.hci.scolio_kids.ui.login.ChooseDoctor;
//import ntua.hci.scolio_kids.ui.login.MemberKid;
//
//public class PatientsMain extends AppCompatActivity {
//
//    private ListView listView;
//    List<String> folders = new ArrayList<>();
//    List<String> age = new ArrayList<>();
//    List<String> height = new ArrayList<>();
//    List<String> quest = new ArrayList<>();
//    List<String> ans = new ArrayList<>();
//
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.patient_page);
//
//        auth = FirebaseAuth.getInstance();
//        Log.i("ONCREATEPATIENTSMAIN", "I'M IN");
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////        String uid = user.getUid();
//
//        listView = findViewById(R.id.listView);
//
//        ((FloatingActionButton) this.findViewById(R.id.fab)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
//            public final void onClick(View it) {
//                Log.i("FABPATIENTSMAIN", "I'M IN");
//                Intent main = new Intent((Context) PatientsMain.this, FileKidActivity.class);
//                PatientsMain.this.startActivity(main);
//                PatientsMain.this.setResult(-1);
//                PatientsMain.this.finish();
//            }
//        }));
//        ((Button) this.findViewById(R.id.btnSignOut)).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
//            public final void onClick(View it) {
//                auth.signOut();
//
//                PatientsMain.this.setResult(-1);
//                PatientsMain.this.finish();
//            }
//        });
//
//        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, folders);
//        listView.setAdapter(adapter);
//
//
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("children");
////        folders.clear();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                folders.clear();
//                age.clear();
//                ans.clear();
//                quest.clear();
//                height.clear();
//                Log.i("ONDATACHANGEPATIENTSM", "I'M IN");
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    MemberKid info1 = snapshot1.getValue(MemberKid.class);
//                    String txt1 = info1.getName();
//                    String txt2 = info1.getAge();
//                    String txt3 = info1.getHeight();
//                    String txt4 = info1.getQuest();
//                    String txt5 = info1.getAns();
//                    age.add(txt2);
//                    height.add(txt3);
//                    quest.add(txt4);
//                    folders.add(txt1);
//                    ans.add(txt5);
//                }
//                adapter.notifyDataSetChanged();
//
//                DatabaseReference myreference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("children");
//                for (int i = 0; i < folders.size(); i++) {
//                    Log.i("out here", String.valueOf(folders.size()));
//                    int finalI = i;
//                    myreference.child(folders.get(i)).child("ans").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.i("in here", "sth");
////                    folders.get(finalI).setIsVisib(View.VISIBLE);
//                            CardView not_dot = findViewById(R.id.dot);
//                            not_dot.setVisibility(View.VISIBLE);
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("HelloListView", "You clicked Item: " + id + " " + folders.get((int) id) + " " + quest.get((int) id) + " " + height.get((int) id) + " " + age.get((int) id) + "at position:" + position);
////                reference.child(uid).child("assigned_doctor").setValue(codenames.get((int) id));
//
//                Intent main = new Intent((Context) PatientsMain.this, PatientViewChild.class);
//
//                main.putExtra("name_key", folders.get((int) id));
//                main.putExtra("age_key", age.get((int) id));
//                main.putExtra("height_key", height.get((int) id));
//                main.putExtra("quest_key", quest.get((int) id));
//                main.putExtra("ans_key", ans.get((int) id));
//
//                PatientsMain.this.startActivity(main);
//                PatientsMain.this.setResult(-1);
//                //PatientsMain.this.finish();
//            }
//        });
//    }
//
//}
//

package ntua.hci.scolio_kids.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import ntua.hci.scolio_kids.ui.login.ChooseDoctor;
import ntua.hci.scolio_kids.ui.login.MemberKid;

import static android.view.View.VISIBLE;

public class PatientsMain extends AppCompatActivity {

    private ListView listView;
    List<MemberFolder> folders = new ArrayList<>(); /////////alla
    List<String> age = new ArrayList<>();
    List<String> height = new ArrayList<>();
    List<String> quest = new ArrayList<>();
    List<String> ans = new ArrayList<>();

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_page);

        auth = FirebaseAuth.getInstance();
        Log.i("ONCREATEPATIENTSMAIN", "I'M IN");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();

        listView = findViewById(R.id.listView);

        ((FloatingActionButton) this.findViewById(R.id.fab)).setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Log.i("FABPATIENTSMAIN", "I'M IN");
                Intent main = new Intent((Context) PatientsMain.this, FileKidActivity.class);
                PatientsMain.this.startActivity(main);
                PatientsMain.this.setResult(-1);
                PatientsMain.this.finish();
            }
        }));
        ((Button) this.findViewById(R.id.btnSignOut)).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View it) {
                auth.signOut();

                PatientsMain.this.setResult(-1);
                PatientsMain.this.finish();
            }
        });

//        CardView not_dot = findViewById(R.id.dot);
//        not_dot.setVisibility(View.INVISIBLE);

        CustomListViewAdapterPM adapter = new CustomListViewAdapterPM(this, R.layout.list_item, folders);
        listView.setAdapter(adapter);

//        DatabaseReference referenceAns = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("children");
////                final int[] counter = {0};
//        for (int i = 0; i < folders.size(); i++) {
//            int finalI = i;
//            referenceAns.child(folders.get(i).getNameList()).child("ans").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Log.i("in here", "sth");
////                            if(counter[0] !=0) {
//                    folders.get(finalI).setIsVisib(View.VISIBLE);
////                            CardView not_dot = findViewById(R.id.dot);
////                            not_dot.setVisibility(View.VISIBLE);
//                    adapter.notifyDataSetChanged();
////                            }
////                            counter[0] = counter[0] +1;
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("children");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                folders.clear();
                age.clear();
                ans.clear();
                quest.clear();
                height.clear();
                Log.i("ONDATACHANGEPATIENTSM", "I'M IN");
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    MemberKid info1 = snapshot1.getValue(MemberKid.class);
                    MemberFolder some_folder = new MemberFolder();
                    String txt1 = info1.getName();
                    String txt2 = info1.getAge();
                    String txt3 = info1.getHeight();
                    String txt4 = info1.getQuest();
                    String txt5 = info1.getAns();
                    age.add(txt2);
                    height.add(txt3);
                    quest.add(txt4);
                    some_folder.setNameList(txt1);
                    some_folder.setIsVisib(View.INVISIBLE);
                    folders.add(some_folder);
                    ans.add(txt5);
                }
                adapter.notifyDataSetChanged();
//                CardView not_dot = findViewById(R.id.dot);
//                not_dot.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id + " " + folders.get((int) id) + " " + quest.get((int) id) + " " + height.get((int) id) + " " + age.get((int) id) + "at position:" + position);
//                reference.child(uid).child("assigned_doctor").setValue(codenames.get((int) id));
//                CardView mydot = findViewById(R.id.dot);
//                mydot.setVisibility(View.INVISIBLE);
                folders.get((int) id).setIsVisib(View.INVISIBLE);
                adapter.notifyDataSetChanged();

                Intent main = new Intent((Context) PatientsMain.this, PatientViewChild.class);

                main.putExtra("name_key", folders.get((int) id).getNameList());
                main.putExtra("age_key", age.get((int) id));
                main.putExtra("height_key", height.get((int) id));
                main.putExtra("quest_key", quest.get((int) id));
                main.putExtra("ans_key", ans.get((int) id));

                PatientsMain.this.startActivity(main);
                PatientsMain.this.setResult(-1);
                //PatientsMain.this.finish();
            }
        });
    }

}
