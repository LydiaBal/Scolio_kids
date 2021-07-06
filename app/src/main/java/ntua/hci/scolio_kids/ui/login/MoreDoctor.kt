package ntua.hci.scolio_kids.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ntua.hci.scolio_kids.R
import ntua.hci.scolio_kids.main.DoctorMain

class MoreDoctor : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var reference: DatabaseReference? = null

    var member_doc: MemberDocInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.more_doctor)

        reference = FirebaseDatabase.getInstance().reference
        member_doc = MemberDocInfo()

        var submit = findViewById(R.id.btnDone) as Button

        submit.setOnClickListener(View.OnClickListener {
            var a1 = findViewById(R.id.name_ans) as EditText
            var a2 = findViewById(R.id.area_ans) as EditText
            var a3 = findViewById(R.id.area2_ans) as EditText

            val kidname: String = a1.getText().toString().trim()
            val kidage: String = a2.getText().toString().trim()
            val kidheight: String = a3.getText().toString().trim()

            if (TextUtils.isEmpty(kidname) || TextUtils.isEmpty(kidage) || TextUtils.isEmpty(kidheight)) {
                if (TextUtils.isEmpty(kidname)) Toast.makeText(baseContext, "Please fill name", Toast.LENGTH_SHORT).show()
                if (TextUtils.isEmpty(kidage)) Toast.makeText(baseContext, "Please fill age", Toast.LENGTH_SHORT).show()
                if (TextUtils.isEmpty(kidheight)) Toast.makeText(baseContext, "Please fill height", Toast.LENGTH_SHORT).show()
                //Toast.makeText(baseContext, "Please fill all the above fields", Toast.LENGTH_SHORT).show()
            } else {
                member_doc!!.name = kidname
                reference!!.child("users").child(auth.currentUser!!.uid).child("info").child("name").setValue(member_doc!!.name)

                member_doc!!.city = kidage
                reference!!.child("users").child(auth.currentUser!!.uid).child("info").child("city").setValue(member_doc!!.city)

                member_doc!!.address = kidheight
                reference!!.child("users").child(auth.currentUser!!.uid).child("info").child("address").setValue(member_doc!!.address)

                val next = Intent(this, DoctorMain::class.java)
                startActivity(next)

                this.setResult(Activity.RESULT_OK)
                this.finish()
            }
        })
    }
}