package br.com.jobflix.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.jobflix.R
import br.com.jobflix.databinding.FragRequestPinBinding
import br.com.jobflix.util.BiometricUtils
import br.com.jobflix.util.Constants.LOSE_FOCUS_DELAY
import br.com.jobflix.util.Constants.PIN_MAX_LENGTH
import br.com.jobflix.util.extensions.hide
import br.com.jobflix.util.extensions.hideSoftKeyboard
import br.com.jobflix.util.extensions.isFingerPrintAvailable
import br.com.jobflix.util.extensions.showSoftKeyboard

class RequestPinFragment : Fragment() {

    private var _binding: FragRequestPinBinding? = null
    private val binding get() = _binding!!
    private val actViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragRequestPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
    }

    private fun buildUi() {
        with(binding) {
            etPassword.doOnTextChanged { text, _, _, _ -> onPinTextChanged(text) }
            tvFingerprint.setOnClickListener { showFingerprintDialog() }
            tvFingerprint.isVisible = isFingerPrintAvailable()

            if (isFingerPrintAvailable() && actViewModel.isFingerPrintEnabled())
                delay(DIALOG_DELAY) { showFingerprintDialog() }
            else {
                etPassword.showSoftKeyboard()
                tvFingerprint.hide()
            }
        }
    }

    private fun onPinTextChanged(text: CharSequence?) {
        if (text?.length == PIN_MAX_LENGTH)
            validatePin(text.toString())
        else if (binding.tiPassword.error != null)
            binding.tiPassword.error = null
    }

    private fun showFingerprintDialog() {
        BiometricUtils.init(this)
            .onSuccess { (activity as AuthActivity).launchMainActivity() }
            .showDialog()
    }

    private fun launchMain() {
        delay {
            with(binding.etPassword) {
                clearFocus()
                hideSoftKeyboard()
            }
            (activity as AuthActivity).launchMainActivity()
        }
    }

    private fun validatePin(pin: String?) {
        pin?.let {
            if (actViewModel.isValidPin(it))
                launchMain()
            else
                binding.tiPassword.error = getString(R.string.invalid_pin)
        }
    }

    private fun delay(delayTime: Long = LOSE_FOCUS_DELAY, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delayTime)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DIALOG_DELAY = 150L
    }
}