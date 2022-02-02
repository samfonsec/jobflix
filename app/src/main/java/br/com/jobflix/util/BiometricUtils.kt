package br.com.jobflix.util

import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.jobflix.R

object BiometricUtils {

    private var frag: Fragment? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var onSuccess: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    fun init(fragment: Fragment?): Builder {
        return Builder.create(fragment)
    }

    object Builder {

        fun create(fragment: Fragment?): Builder {
            frag = fragment
            return this
        }

        fun onSuccess(action: () -> Unit): Builder {
            onSuccess = action
            return this
        }

        fun onError(action: () -> Unit): Builder {
            onError = action
            return this
        }

        fun showDialog() {
            frag?.run {
                context?.let { context ->
                    biometricPrompt = BiometricPrompt(
                        this,
                        ContextCompat.getMainExecutor(context),
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                onSuccess?.invoke()
                            }

                            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                if (errorCode != ERROR_NEGATIVE_BUTTON)
                                    onError?.invoke()
                            }
                        }
                    )
                    val promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.app_name))
                        .setNegativeButtonText(getString(R.string.action_insert_pin))
                        .setConfirmationRequired(false)
                        .build()

                    biometricPrompt?.authenticate(promptInfo)
                }
            }
        }
    }
}