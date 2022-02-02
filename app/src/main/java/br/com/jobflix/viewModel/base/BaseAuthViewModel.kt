package br.com.jobflix.viewModel.base

import android.content.SharedPreferences
import br.com.jobflix.util.Constants
import org.koin.java.KoinJavaComponent.inject


abstract class BaseAuthViewModel : BaseViewModel() {

    val sharedPreferences by inject(SharedPreferences::class.java)

    fun hasPin() = sharedPreferences.contains(Constants.KEY_AUTH_PIN)

    fun savePin(pin: String) = sharedPreferences.edit().putString(Constants.KEY_AUTH_PIN, pin).apply()

    fun enableFingerprint(enable: Boolean) = sharedPreferences.edit().putBoolean(Constants.KEY_ENABLE_FINGERPRINT, enable).apply()

    fun isValidPin(pin: String) = pin == sharedPreferences.getString(Constants.KEY_AUTH_PIN, "")

    fun skipPin() = sharedPreferences.edit().putBoolean(Constants.KEY_SKIP_PIN, true).apply()

}