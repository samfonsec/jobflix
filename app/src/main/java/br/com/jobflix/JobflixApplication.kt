package br.com.jobflix

import android.app.Application
import br.com.jobflix.di.*
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class JobflixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@JobflixApplication)
            modules(mutableListOf(retrofitModule, seriesModule, searchModule, peopleModule, viewModels))
        }
    }
}