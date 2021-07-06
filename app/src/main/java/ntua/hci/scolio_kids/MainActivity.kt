package ntua.hci.scolio_kids

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContentView(R.layout.activity_main)

        if (auth.currentUser?.isEmailVerified == false)
            Toast.makeText(baseContext, R.string.verify_email,
                    Toast.LENGTH_SHORT).show()

        findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            auth.signOut()
            this.setResult(Activity.RESULT_OK)
            this.finish()
        }
    }
}