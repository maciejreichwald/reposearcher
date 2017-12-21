package com.rudearts.reposearcher.api

import com.rudearts.reposearcher.model.external.response.SearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface RestAPI {

    @GET("/users/{${RestController.USERNAME_PATH}}/repos")
    fun search(@Path(value = RestController.USERNAME_PATH, encoded = true) username: String): Single<Response<SearchResponse>>

}
