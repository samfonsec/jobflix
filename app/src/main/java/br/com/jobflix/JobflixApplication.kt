package br.com.jobflix

import android.app.Application
import br.com.jobflix.di.retrofitModule
import br.com.jobflix.di.seriesModule
import br.com.jobflix.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class JobflixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@JobflixApplication)
            modules(mutableListOf(retrofitModule, seriesModule, viewModels))
        }
    }
}