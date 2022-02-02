package br.com.jobflix.view.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import br.com.jobflix.R
import br.com.jobflix.databinding.DialogRequestPinBinding
import br.com.jobflix.util.Constants
import br.com.jobflix.util.Constants.PIN_MAX_LENGTH
import br.com.jobflix.util.extensions.*

class HandlePinDialog : DialogFragment() {

    private val binding by viewBinding(DialogRequestPinBinding::inflate)

    private var onCreatePin: ((String, Boolean) -> Unit)? = null
    private var onRemovePin: ((String) -> Unit)? = null
    private val hasPin by argument<Boolean>(ARG_HAS_PIN)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWindow()
        buildUi()
    }

    override fun dismiss() {
        binding.etPassword.hideSoftKeyboard()
        super.dismiss()
    }

    private fun setupWindow() {
        if (activity?.isFinishing == false) {
            dialog?.window?.run {
                setLayout(MATCH_PARENT, WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    private fun buildUi() {
        with(binding) {
            etPassword.showSoftKeyboard()
            etPassword.doOnTextChanged { text, _, _, _ ->
                btNewPassword.isEnabled = (text?.length == PIN_MAX_LENGTH).also { pinFilled ->
                    if (!pinFilled && tiPassword.error != null)
                        tiPassword.error = null
                }
            }
            btNewPassword.setOnClickListener { onButtonClicked() }
        }
        if (hasPin) setupRemovePinLayout()
    }

    private fun setupRemovePinLayout() {
        with(binding) {
            tvLabel.text = getString(R.string.label_remove_pin_info)
            btNewPassword.text = getString(R.string.action_remove_pin)
            tiPassword.helperText = null
            cbEnableFingerprint.hide()
        }
    }

    private fun onButtonClicked() {
        if (hasPin)
            onRemovePin?.invoke(binding.etPassword.text.toString())
        else
            onCreatePin?.invoke(
                binding.etPassword.text.toString(),
                binding.cbEnableFingerprint.isChecked
            )
    }

    fun onSavePin(action: (String, Boolean) -> Unit) {
        onCreatePin = action
    }

    fun onRemovePin(action: (String) -> Unit) {
        onRemovePin = action
    }

    fun setError() {
        binding.tiPassword.error = getString(R.string.invalid_pin)
    }

    companion object {
        private const val ARG_HAS_PIN = "arg_has_pin"

        fun newInstance(hasPin: Boolean) = HandlePinDialog().withArgs {
            putBoolean(ARG_HAS_PIN, hasPin)
        }
    }
}