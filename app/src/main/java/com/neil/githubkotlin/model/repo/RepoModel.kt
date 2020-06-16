package com.neil.githubkotlin.model.repo

import com.neil.githubkotlin.model.page.ListPage
import com.neil.githubkotlin.network.entities.Repository
import com.neil.githubkotlin.network.entities.User
import com.neil.githubkotlin.network.services.RepositoryService
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.GitHubPaging
import java.util.*

class RepoListPage(val owner: User?) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
        return if (owner == null) {
            RepositoryService.allRepositories(page, "pushed:<2020-06-16").map { it.paging }
        } else {
            RepositoryService.listRepositoriesOfUser(owner.login, page)
        }
    }

}