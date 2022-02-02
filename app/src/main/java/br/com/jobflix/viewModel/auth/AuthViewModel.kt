package br.com.jobflix.viewModel.auth

import br.com.jobflix.util.Constants.KEY_ENABLE_FINGERPRINT
import br.com.jobflix.util.Constants.KEY_SKIP_PIN
import br.com.jobflix.viewModel.base.BaseAuthViewModel

class AuthViewModel : BaseAuthViewModel() {

    fun hasSkippedPin() = sharedPreferences.contains(KEY_SKIP_PIN)

    fun isFingerPrintEnabled() = sharedPreferences.getBoolean(KEY_ENABLE_FINGERPRINT, false)
}