package com.neil.githubkotlin.network

import com.bennyhuo.github.network.interceptors.AcceptInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neil.common.ext.ensureDir
import com.neil.githubkotlin.AppContext
import com.neil.githubkotlin.network.interceptors.AuthInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 13:54
 * @DESC
 */
private const val BASE_URL = "https://api.github.com"

//缓存文件
private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply {
        //使用扩展方法判断
        ensureDir()
    }
}

//网络请求
val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory2.createWithSchedulers(Schedulers.io(), AndroidSchedulers.mainThread()))//自定义，优化线程切换
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(Cache(cacheFile, 1024 * 1024))
                .addInterceptor(AuthInterceptor())//登录鉴权拦截
                .addInterceptor(AcceptInterceptor())//请求头拦截
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .baseUrl(BASE_URL)
        .build()
}