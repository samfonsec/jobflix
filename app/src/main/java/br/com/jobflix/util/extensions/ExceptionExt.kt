package br.com.jobflix.util.extensions

import java.util.concurrent.CancellationException

fun Throwable.isCanceling() = this is CancellationException