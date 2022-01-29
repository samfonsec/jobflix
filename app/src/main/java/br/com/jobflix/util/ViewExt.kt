package br.com.jobflix.util

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun ImageView.loadImageFromUrl(url: String) {
    Picasso.get().load(url).tag(context).into(this)
}

fun TextView.setTextFromHtml(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).trim()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html).trim()
    }
}