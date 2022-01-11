package br.android.cericatto.infrontxtask.di

import br.android.cericatto.infrontxtask.data.Api
import br.android.cericatto.infrontxtask.main.DefaultMainRepository
import br.android.cericatto.infrontxtask.main.MainRepository
import br.android.cericatto.infrontxtask.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://storage.googleapis.com/cdn-og-test-api/test-task/"

/**
 * SingleComponent vs ApplicationComponent:
 * https://stackoverflow.com/a/65991420/1354788
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: Api): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}