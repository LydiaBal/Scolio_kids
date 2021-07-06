package ntua.hci.scolio_kids.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import ntua.hci.scolio_kids.R

class UserProfile : AppCompatActivity()  {
    private lateinit var auth: FirebaseAuth

    var reference: DatabaseReference? = null
    var member: Member? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.user_profile)

        reference = FirebaseDatabase.getInstance().reference
        member = Member()

        var submit = findViewById(R.id.submit) as Button
        var r1 = findViewById(R.id.radio_1) as RadioButton
        var r2 = findViewById(R.id.radio_2) as RadioButton

        val d1 = "Doctor"
        val d2 = "Patient"

        submit.setOnClickListener(View.OnClickListener {
            if (r1.isChecked) {
                member!!.user1 = d1
                reference!!.child("users").child(auth.currentUser!!.uid).child("role").setValue(member!!.user1)
            } else {
            }
            if (r2.isChecked) {
                member!!.user2 = d2
                reference!!.child("users").child(auth.currentUser!!.uid).child("role").setValue(member!!.user2)
            }

            if(r1.isChecked) {
                val main = Intent(this, MoreDoctor::class.java)
                startActivity(main)

                this.setResult(Activity.RESULT_OK)
                this.finish()
            }
            if(r2.isChecked) {
                val main = Intent(this, ChooseDoctor::class.java)
                startActivity(main)

                this.setResult(Activity.RESULT_OK)
                this.finish()
            }

        })


    }
}