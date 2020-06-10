package  com.neil.githubkotlin.network.interceptors

import android.util.Base64
import com.neil.githubkotlin.model.account.AccountManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/**
 * @USER NEIL.Z
 * @TIME 2020/6/10 0010 14:22
 * @DESC 登录鉴权拦截
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
            .apply {
                when {
                    original.url().pathSegments().contains("authorizations") -> {
                        //鉴权接口添加用户信息
                        val userCredentials = AccountManager.username + ":" + AccountManager.passwd
                        val auth = "Basic " + String(
                            Base64.encode(
                                userCredentials.toByteArray(),
                                Base64.DEFAULT
                            )
                        ).trim()
                        header("Authorization", auth)
                    }
                    AccountManager.isLoggedIn() -> {
                        //已登录，普通接口添加token
                        val auth = "Token " + AccountManager.token
                        header("Authorization", auth)
                    }
                    else -> removeHeader("Authorization")//未登录
                }
            }
            .build())
    }
}