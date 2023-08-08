package co.utp.aves.di

import android.content.Context
import co.utp.aves.BuildConfig
import co.utp.aves.data.BirdRepositoryImpl
import co.utp.aves.data.remote.ApiBird
import co.utp.aves.data.repository.dataSourceImpl.ApiBirdRemoteDataSourceImpl
import co.utp.aves.domain.usescases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BirdModule {


    @Provides
    @Singleton
    fun provideFindBirdUseCase(birdRepository: BirdRepositoryImpl)=
        FindBirdUseCase(birdRepository)

    @Provides
    @Singleton
    fun provideGetAboutUsUseCase(birdRepository: BirdRepositoryImpl)=
        GetAboutUsUseCase(birdRepository)

    @Provides
    @Singleton
    fun provideGetAboutUsDependenciesUseCase(birdRepository: BirdRepositoryImpl) =
        GetAboutUsDependenciesUseCase(birdRepository)

    @Provides
    @Singleton
    fun provideGetBirdUseCase(birdRepository: BirdRepositoryImpl)=
        GetBirdUseCase(birdRepository)

    @Provides
    @Singleton
    fun provideUploadImageUseCase(birdRepository: BirdRepositoryImpl)=
        UploadImageUseCase(birdRepository)

    @Provides
    @Singleton
    fun provideApiBirdRemoteDataSourceImpl(apiBird: ApiBird)=
        ApiBirdRemoteDataSourceImpl(apiBird)

    @Provides
    @Singleton
    fun provideBirdRepositoryImpl(
        @ApplicationContext context: Context,
        apiBirdRemoteDataSourceImpl: ApiBirdRemoteDataSourceImpl
    )= BirdRepositoryImpl(context,apiBirdRemoteDataSourceImpl)

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiBird =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build().create(ApiBird::class.java)


}