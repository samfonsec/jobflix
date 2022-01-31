package br.com.jobflix.util.extensions

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import br.com.jobflix.R
import com.squareup.picasso.Picasso

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun ImageView.loadImageFromUrl(url: String?, placeHolderResId: Int = R.drawable.bg_placeholder_serie) {
    Picasso.get().load(url).placeholder(placeHolderResId).tag(context).into(this)
}

fun TextView.setTextFromHtml(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).trim()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html).trim()
    }
}

fun View.showSoftKeyboard() {
    postDelayed({
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, 200)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

