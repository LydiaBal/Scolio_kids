package ntua.hci.scolio_kids.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ntua.hci.scolio_kids.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnEmail).setOnClickListener {
            findNavController().navigate(R.id.splash_to_connect)
        }

        view.findViewById<TextView>(R.id.txtSignUp).setOnClickListener {
            findNavController().navigate(R.id.splash_to_signup)
        }
    }


}
