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
import br.com.jobflix.databinding.FragNewPinBinding
import br.com.jobflix.util.Constants.LOSE_FOCUS_DELAY
import br.com.jobflix.util.Constants.PIN_MAX_LENGTH
import br.com.jobflix.util.extensions.hideSoftKeyboard
import br.com.jobflix.util.extensions.isFingerPrintAvailable

class NewPinFragment : Fragment() {

    private var _binding: FragNewPinBinding? = null
    private val binding get() = _binding!!
    private val actViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragNewPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
    }

    private fun buildUi() {
        with(binding) {
            etNewPassword.doOnTextChanged { text, _, _, _ -> onPinTextChanged(text) }
            btNewPassword.setOnClickListener { onSavePin() }
            tvSkip.setOnClickListener {
                actViewModel.skipPin()
                launchMain()
            }
            cbEnableFingerprint.isVisible = isFingerPrintAvailable()
        }
    }

    private fun onPinTextChanged(text: CharSequence?) {
        with(binding) {
            btNewPassword.isEnabled = (text?.length == PIN_MAX_LENGTH).also { pinFilled ->
                if (pinFilled) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        etNewPassword.clearFocus()
                        etNewPassword.hideSoftKeyboard()
                    }, LOSE_FOCUS_DELAY)
                }
            }
        }
    }

    private fun onSavePin() {
        actViewModel.savePin(binding.etNewPassword.text.toString())
        actViewModel.enableFingerprint(binding.cbEnableFingerprint.isChecked)
        launchMain()
    }

    private fun launchMain() {
        (activity as AuthActivity).launchMainActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}