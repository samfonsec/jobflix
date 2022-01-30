package br.com.jobflix.di

import br.com.jobflix.data.api.SearchApi
import br.com.jobflix.data.api.SeriesApi
import br.com.jobflix.data.dataSource.SearchDataSource
import br.com.jobflix.data.dataSource.SeriesDataSource
import br.com.jobflix.data.database.FavoriteDatabase
import br.com.jobflix.data.repository.SearchRepository
import br.com.jobflix.data.repository.SeriesRepository
import br.com.jobflix.viewModel.details.DetailsViewModel
import br.com.jobflix.viewModel.home.HomeViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val seriesModule = module {
    single<SeriesApi> { getRetrofit().create(SeriesApi::class.java) }
    single { FavoriteDatabase.getInstance(androidContext()).favoriteDao() }
    single<SeriesRepository> { SeriesDataSource(get(), get()) }
}

val searchModule = module {
    single<SearchApi> { getRetrofit().create(SearchApi::class.java) }
    single<SearchRepository> { SearchDataSource(get()) }
}

val viewModels = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

private fun Scope.getRetrofit() = get<Retrofit>()