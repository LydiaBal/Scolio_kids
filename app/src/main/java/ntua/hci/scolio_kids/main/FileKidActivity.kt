package ntua.hci.scolio_kids.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ntua.hci.scolio_kids.R
import ntua.hci.scolio_kids.ui.login.MemberKid

class FileKidActivity: AppCompatActivity()  {
    private lateinit var auth: FirebaseAuth

    var reference: DatabaseReference? = null
    var member_kid: MemberKid? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.file_kid)

        reference = FirebaseDatabase.getInstance().reference
        member_kid = MemberKid()

        var contin = findViewById(R.id.nextone) as Button

        contin.setOnClickListener(View.OnClickListener {
            var a1 = findViewById(R.id.name_ans) as EditText
            var a2 = findViewById(R.id.age_ans) as EditText
            var a3 = findViewById(R.id.height_ans) as EditText
            var a4 = findViewById(R.id.question_ans) as EditText

            val kidname: String = a1.getText().toString().trim()
            val kidage: String = a2.getText().toString().trim()
            val kidheight: String = a3.getText().toString().trim()
            val kidquest: String = a4.getText().toString().trim()

            if (TextUtils.isEmpty(kidname) || TextUtils.isEmpty(kidage) || TextUtils.isEmpty(kidheight) || TextUtils.isEmpty(kidquest)) {
                if(TextUtils.isEmpty(kidname)) Toast.makeText(baseContext, "Please fill name", Toast.LENGTH_SHORT).show()
                if(TextUtils.isEmpty(kidage)) Toast.makeText(baseContext, "Please fill age", Toast.LENGTH_SHORT).show()
                if(TextUtils.isEmpty(kidheight)) Toast.makeText(baseContext, "Please fill height", Toast.LENGTH_SHORT).show()
                if(TextUtils.isEmpty(kidquest)) Toast.makeText(baseContext, "Please fill question", Toast.LENGTH_SHORT).show()
                //Toast.makeText(baseContext, "Please fill all the above fields", Toast.LENGTH_SHORT).show()
            } else {
                member_kid!!.name = kidname
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("name").setValue(member_kid!!.name)

                member_kid!!.age = kidage
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("age").setValue(member_kid!!.age)

                member_kid!!.height = kidheight
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("height").setValue(member_kid!!.height)

                member_kid!!.quest = kidquest
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("quest").setValue(member_kid!!.quest)

                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("one").setValue("")
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("two").setValue("")
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("three").setValue("")
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("four").setValue("")
                reference!!.child("users").child(auth.currentUser!!.uid).child("children").child(kidname).child("five").setValue("")

                val main = Intent(this , KidImagesActivity::class.java)
                Log.i("hi","i am going to flip")
                main.putExtra("name_key", kidname)
                startActivity(main)

                this.setResult(Activity.RESULT_OK)
                //this.finish()
            }
        })

    }
}