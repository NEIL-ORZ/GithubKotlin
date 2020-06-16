package retrofit2.adapter.rxjava2

import com.neil.common.log.logger
import okhttp3.HttpUrl

/**
 * @USER NEIL.Z
 * @TIME 2020/6/16 0016 9:37
 * @DESC github 接口分页
 */
class GitHubPaging<T> : ArrayList<T>() {
    companion object {
        const val URL_PATTERN =
            """(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"""
    }

    private val relMap = HashMap<String, String?>().withDefault { null }

    private val first by relMap
    private val last by relMap
    private val next by relMap
    private val prev by relMap

    val isLast
        get() = last == null

    val hasNext
        get() = next != null

    val isFirst
        get() = first == null

    val hasPrev
        get() = prev != null

    var since: Int = 0

    operator fun get(key: String): String? {
        return relMap[key]
    }

    /**
     * 设置分页链接信息
     */
    fun setupLinks(link: String) {
        logger.warn("setupLinks: $link")
        Regex("""<($URL_PATTERN)>; rel="(\w+)"""").findAll(link).asIterable()
            .map { matchResult ->
                val url = matchResult.groupValues[1]
                relMap[matchResult.groupValues[3]] = url // next=....
                if (url.contains("since")) {
                    HttpUrl.parse(url)?.queryParameter("since")?.let {
                        since = it.toInt()
                    }
                }
                logger.warn("${matchResult.groupValues[3]} => ${matchResult.groupValues[1]}")
            }
    }

    /**
     * 添加集合
     */
    fun mergeData(paging: GitHubPaging<T>): GitHubPaging<T> {
        addAll(paging)
        relMap.clear()
        relMap.putAll(paging.relMap)
        since = paging.since
        return this
    }
}