package br.com.jobflix.ui.auth

import br.com.jobflix.util.Constants.KEY_ENABLE_FINGERPRINT
import br.com.jobflix.util.Constants.KEY_SKIP_PIN

class AuthViewModel : BaseAuthViewModel() {

    fun hasSkippedPin() = sharedPreferences.contains(KEY_SKIP_PIN)

    fun isFingerPrintEnabled() = sharedPreferences.getBoolean(KEY_ENABLE_FINGERPRINT, false)
}