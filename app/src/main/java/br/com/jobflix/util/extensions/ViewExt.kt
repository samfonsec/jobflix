package br.com.jobflix.util.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethod.SHOW_FORCED
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun TextView.setTextFromHtml(html: String) {
    text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).trim()
}

@Suppress("DEPRECATION")
fun View.showSoftKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
