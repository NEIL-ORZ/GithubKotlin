package  com.neil.githubkotlin.settings

import com.neil.common.log.logger
import com.neil.githubkotlin.AppContext
import com.neil.githubkotlin.utils.deviceId

object Configs {

    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "4919c17e41904ab1d12f"
        const val clientSecret = "d1d0636acef98a0b39ed2005b24a8af02b7cb56c"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: " + it) }
        }
    }

}