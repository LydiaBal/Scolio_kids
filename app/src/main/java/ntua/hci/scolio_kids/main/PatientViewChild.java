package ntua.hci.scolio_kids.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import ntua.hci.scolio_kids.R;

public class PatientViewChild extends AppCompatActivity {

        TextView receiver_name;
        TextView receiver_height;
        TextView receiver_age;
        TextView receiver_quest;
        TextView receiver_ans;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.patient_view_child);

            receiver_name = (TextView)findViewById(R.id.name_ans);
            receiver_height = (TextView)findViewById(R.id.height_ans);
            receiver_age = (TextView)findViewById(R.id.age_ans);
            receiver_quest = (TextView)findViewById(R.id.question_ans);
            receiver_ans = (TextView)findViewById(R.id.doc_com);

            // create the get Intent object
            Intent intent = getIntent();

            // receive the value by getStringExtra() method
            // and key must be same which is send by first activity
            String str_name = intent.getStringExtra("name_key");
            String str_age = intent.getStringExtra("age_key");
            String str_height = intent.getStringExtra("height_key");
            String str_quest = intent.getStringExtra("quest_key");
            String str_ans = intent.getStringExtra("ans_key");

            // display the string into textView
            receiver_name.setText(str_name);
            receiver_age.setText(str_age);
            receiver_height.setText(str_height);
            receiver_quest.setText(str_quest);
            receiver_ans.setText(str_ans);

            ((Button)this.findViewById(R.id.images)).setOnClickListener((View.OnClickListener) new View.OnClickListener(){
                public final void onClick (View it){
                    Intent info = new Intent((Context) PatientViewChild.this, KidImagesActivity.class);

                    info.putExtra("name_key", str_name);

                    startActivity(info);

                    PatientViewChild.this.setResult(Activity.RESULT_OK);
                    // this.finish();
                }
            });

        }
}
