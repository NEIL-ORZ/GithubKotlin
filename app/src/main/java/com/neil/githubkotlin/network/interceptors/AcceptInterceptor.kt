package com.bennyhuo.github.network.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 14:22
 * @DESC 请求头拦截
 */
class AcceptInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
                .apply {
                    header("accept", "application/vnd.github.v3.full+json, ${original.header("accept") ?: ""}")
                }
                .build())
    }
}