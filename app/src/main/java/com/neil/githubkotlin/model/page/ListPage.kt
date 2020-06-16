package com.neil.githubkotlin.model.page

import com.neil.common.log.logger
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.GitHubPaging

/**
 * 分页封装
 */
abstract class ListPage<DataType> : DataProvider<DataType> {
    companion object {
        const val PAGE_SIZE = 20
    }

    var currentPage = 1
        private set

    val data = GitHubPaging<DataType>()

    /**
     * 加载更多
     */
    fun loadMore() = getData(currentPage + 1)
        .doOnNext {
            currentPage + 1
        }
        .doOnError {
            logger.error("loadMore Error", it)
        }
        .map {
            data.mergeData(it)
            data
        }

    /**
     * 加载更多页数
     */
    fun loadFromFirst(pageCount: Int = currentPage) =
        Observable.range(1, pageCount)
            .concatMap {
                getData(it)
            }
            .doOnError {
                logger.error("loadFromFirst, pageCount=$pageCount", it)
            }
            .reduce { acc, page ->
                acc.mergeData(page)
            }
            .doOnSuccess() {
                data.clear()
                data.mergeData(it)
            }
}