package com.neil.githubkotlin.model.page

import io.reactivex.Observable
import retrofit2.adapter.rxjava2.GitHubPaging

interface DataProvider<DataType> {
    fun getData(page: Int): Observable<GitHubPaging<DataType>>
}