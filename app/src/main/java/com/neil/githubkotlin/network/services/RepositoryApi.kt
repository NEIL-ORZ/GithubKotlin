package com.neil.githubkotlin.network.services

import com.neil.githubkotlin.network.entities.Repository
import com.neil.githubkotlin.network.entities.SearchRepositories
import com.neil.githubkotlin.network.retrofit
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.GitHubPaging
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryApi {

    //分页项目列表
    @GET("/users/{owner}/repos?type=all")
    fun listRepositoriesOfUser(
        @Path("owner") owner: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20
    ): Observable<GitHubPaging<Repository>>

    //搜索分页列表
    @GET("/search/repositories?order=desc&sort=updated")
    fun allRepositories(
        @Query("page") page: Int = 1,
        @Query("q") q: String,
        @Query("per_page") per_page: Int = 20
    ): Observable<SearchRepositories>

//    @GET("/repos/{owner}/{repgito}")
//    fun getRepository(
//        @Path("owner") owner: String,
//        @Path("repo") repo: String,
//        @Query(FORCE_NETWORK) forceNetwork: Boolean = false
//    ): Observable<Repository>
}

object RepositoryService : RepositoryApi by retrofit.create(RepositoryApi::class.java)