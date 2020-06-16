package retrofit2.adapter.rxjava

import retrofit2.adapter.rxjava2.GitHubPaging

abstract class PagingWrapper<T>{

    abstract fun getElements(): List<T>

    val paging by lazy {
        GitHubPaging<T>().also { it.addAll(getElements()) }
    }
}