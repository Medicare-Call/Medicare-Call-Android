package com.konkuk.medicarecall.data

import android.content.Context
import android.util.Log
import com.konkuk.medicarecall.BuildConfig
import com.konkuk.medicarecall.data.api.ElderRegisterService
import com.konkuk.medicarecall.data.api.EldersInfoService
import com.konkuk.medicarecall.data.api.MemberRegisterService
import com.konkuk.medicarecall.data.api.NaverPayService
import com.konkuk.medicarecall.data.api.NoticeService
import com.konkuk.medicarecall.data.api.SetCallService
import com.konkuk.medicarecall.data.api.SettingService
import com.konkuk.medicarecall.data.api.SubscribeService
import com.konkuk.medicarecall.data.api.TokenRefreshService
import com.konkuk.medicarecall.data.api.VerificationService
import com.konkuk.medicarecall.data.network.AuthAuthenticator
import com.konkuk.medicarecall.data.network.AuthInterceptor
import com.konkuk.medicarecall.data.repository.DataStoreRepository
import com.konkuk.medicarecall.data.repository.EldersHealthInfoRepository
import com.konkuk.medicarecall.data.repository.EldersInfoRepository
import com.konkuk.medicarecall.data.repository.MemberRegisterRepository
import com.konkuk.medicarecall.data.repository.NaverPayRepository
import com.konkuk.medicarecall.data.repository.NoticeRepository
import com.konkuk.medicarecall.data.repository.SetCallRepository
import com.konkuk.medicarecall.data.repository.SubscribeRepository
import com.konkuk.medicarecall.data.repository.UpdateElderInfoRepository
import com.konkuk.medicarecall.data.repository.UserRepository
import com.konkuk.medicarecall.data.repository.VerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(dataStoreRepository: DataStoreRepository): Interceptor {
        return AuthInterceptor(dataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(
        dataStoreRepository: DataStoreRepository,
        tokenRefreshService: dagger.Lazy<TokenRefreshService>
    ): AuthAuthenticator {
        return AuthAuthenticator(dataStoreRepository, tokenRefreshService)
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // BODY로 해야 요청/응답 JSON까지 다 찍힘
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Log.d("Retrofit", "Base URL: ${BuildConfig.BASE_URL}")
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // BuildConfig에서 baseUrl 가져오는 경우
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideEldersInfoService(retrofit: Retrofit): EldersInfoService {
        return retrofit.create(EldersInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideEldersInfoRepository(service: EldersInfoService): EldersInfoRepository {
        return EldersInfoRepository(service)
    }


    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Provides
    @Singleton
    fun provideVerificationService(retrofit: Retrofit): VerificationService {
        return retrofit.create(VerificationService::class.java)
    }

    @Provides
    @Singleton
    fun provideVerificationRepository(
        service: VerificationService
    ): VerificationRepository {
        return VerificationRepository(service)
    }

    @Provides
    @Singleton
    fun provideMemberRegisterService(retrofit: Retrofit): MemberRegisterService {
        return retrofit.create(MemberRegisterService::class.java)
    }

    @Provides
    @Singleton
    fun provideMemberRegisterRepository(
        service: MemberRegisterService
    ): MemberRegisterRepository {
        return MemberRegisterRepository(service)
    }

    @Provides
    @Singleton
    fun provideElderRegisterService(retrofit: Retrofit): ElderRegisterService {
        return retrofit.create(ElderRegisterService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideElderRegisterRepository(service: ElderRegisterService): ElderRegisterRepository {
//        return ElderRegisterRepository(service)
//    }


    @Provides
    @Singleton
    fun provideNoticeService(retrofit: Retrofit): NoticeService {
        return retrofit.create(NoticeService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeRepository(
        service: NoticeService
    ): NoticeRepository {
        return NoticeRepository(service)
    }

    @Provides
    @Singleton
    fun provideSetCallService(retrofit: Retrofit): SetCallService {
        return retrofit.create(SetCallService::class.java)
    }

    @Provides
    @Singleton
    fun provideSetCallRepository(service: SetCallService): SetCallRepository {
        return SetCallRepository(service)
    }

    @Provides
    @Singleton
    fun provideSubscribeService(retrofit: Retrofit): SubscribeService {
        return retrofit.create(SubscribeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSubscribeRepository(service: SubscribeService): SubscribeRepository {
        return SubscribeRepository(service)
    }

    @Provides
    @Singleton
    fun provideSettingService(retrofit: Retrofit): SettingService {
        return retrofit.create(SettingService::class.java)
    }

    @Provides
    @Singleton
    fun provideEldersHealthInfoRepository(
        elderInfoService: EldersInfoService,
        elderRegisterService: ElderRegisterService
    ): EldersHealthInfoRepository {
        return EldersHealthInfoRepository(elderInfoService, elderRegisterService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        settingService: SettingService,
        dataStoreRepository: DataStoreRepository
    ): UserRepository {
        return UserRepository(settingService, dataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateElderRepository(
        eldersInfoService: EldersInfoService
    ): UpdateElderInfoRepository {
        return UpdateElderInfoRepository(eldersInfoService)
    }

    @Provides
    @Singleton
    fun provideNaverPayService(retrofit: Retrofit)
            : NaverPayService {
        return retrofit.create(NaverPayService::class.java)
    }

    @Provides
    @Singleton
    fun provideNaverPayRepository(
        naverPayService: NaverPayService
    ): NaverPayRepository {
        return NaverPayRepository(naverPayService)
    }

}
