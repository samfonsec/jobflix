package br.com.jobflix.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.jobflix.R
import br.com.jobflix.databinding.ActAuthBinding
import br.com.jobflix.util.extensions.viewBinding
import br.com.jobflix.view.main.MainActivity
import br.com.jobflix.viewModel.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private val binding by viewBinding(ActAuthBinding::inflate)

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        when {
            viewModel.hasPin() -> showFragment(RequestPinFragment())
            viewModel.hasSkippedPin() -> launchMainActivity()
            else -> showFragment(NewPinFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.flAuthContent, fragment).commit()
    }

    fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}