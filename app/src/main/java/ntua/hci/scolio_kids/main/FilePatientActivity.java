package ntua.hci.scolio_kids.main;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.installations.FirebaseInstallations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ntua.hci.scolio_kids.MainActivity;
import ntua.hci.scolio_kids.R;
//import ntua.hci.scolio_kids.sendnotifpack.APIService;
//import ntua.hci.scolio_kids.sendnotifpack.Client;
//import ntua.hci.scolio_kids.sendnotifpack.Data;
//import ntua.hci.scolio_kids.sendnotifpack.MyResponse;
//import ntua.hci.scolio_kids.sendnotifpack.NotificationSender;
//import ntua.hci.scolio_kids.sendnotifpack.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilePatientActivity extends AppCompatActivity {

    TextView receiver_name;
    TextView receiver_height;
    TextView receiver_age;
    TextView receiver_quest;

    EditText a1;

    List<String> parents = new ArrayList<>();


    private FirebaseAuth auth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_patient);

//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        receiver_name = (TextView)findViewById(R.id.name_ans);
        receiver_height = (TextView)findViewById(R.id.height_ans);
        receiver_age = (TextView)findViewById(R.id.age_ans);
        receiver_quest = (TextView)findViewById(R.id.question_ans);

        // create the get Intent object
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str_name = intent.getStringExtra("name_key");
        String str_age = intent.getStringExtra("age_key");
        String str_height = intent.getStringExtra("height_key");
        String str_quest = intent.getStringExtra("quest_key");
        String str_parent = intent.getStringExtra("parent_key");

        parents.add(str_parent);

        // display the string into textView
        receiver_name.setText(str_name);
        receiver_age.setText(str_age);
        receiver_height.setText(str_height);
        receiver_quest.setText(str_quest);

        ((Button)this.findViewById(R.id.btn_doc_com)).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View it) {
                a1 = (EditText) findViewById(R.id.doc_com);
                String answer = a1.getText().toString().trim();

                reference.child("users").child(str_parent).child("children").child(str_name).child("ans").setValue(answer);


                //THIS NOT WORKING
                Context context = getApplicationContext();
                CharSequence text = "Your comment has been submitted";
                int duration = Toast.LENGTH_LONG;

                Toast.makeText(context, text, duration).show();
                //ENDTHIS

//                FirebaseDatabase.getInstance().getReference().child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String usertoken=dataSnapshot.getValue(String.class);
//                        sendNotifications(usertoken, "New Notification", "Your doctor sent you a message");
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

            }
        });

//        UpdateToken();

        ((Button)this.findViewById(R.id.btn_images)).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public final void onClick(View it) {
                Intent main = new Intent((Context) FilePatientActivity.this, PatientImagesActivity.class);
                main.putExtra("parent_key", str_parent);
                main.putExtra("name_key", str_name);
                FilePatientActivity.this.startActivity(main);
                FilePatientActivity.this.setResult(-1);
                //FilePatientActivity.this.finish();
            }
        });


    }

//    public void sendNotifications(String usertoken, String title, String message) {
//        Data data = new Data(title, message);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    Log.i("I am in here",usertoken);
//                    if (response.body().success != 1) {
//                        Toast.makeText(FilePatientActivity.this, "Failed ", Toast.LENGTH_LONG);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void UpdateToken(){
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken= FirebaseInstanceId.getInstance().getToken();
//               // FirebaseInstallations.getInstance().getToken(true).toString();
//        Token token= new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
//    }

}

