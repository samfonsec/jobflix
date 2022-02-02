package br.com.jobflix.util.extensions

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import br.com.jobflix.R
import com.google.android.material.snackbar.Snackbar

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }

inline fun <reified T> Activity.extra(key: String): Lazy<T> = lazy {
    val value = intent.extras?.get(key)
    if (value is T) value
    else throw IllegalArgumentException("Couldn't find extra with key \"$key\" from type " + T::class.java.canonicalName)
}

fun Activity.showErrorSnackbar(view: View, action: () -> Unit) {
    Snackbar.make(view, getString(R.string.error_short_message), Snackbar.LENGTH_INDEFINITE).apply {
        setAction(getString(R.string.error_action)) { action() }
        setBackgroundTint(getColor(R.color.primaryLightColor))
        setTextColor(getColor(R.color.primaryTextColor))
        setActionTextColor(getColor(R.color.secondaryColor))
    }.show()
}