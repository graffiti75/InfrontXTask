package br.android.cericatto.infrontxtask.data

import br.android.cericatto.infrontxtask.data.fixture.ApiFixture
import br.android.cericatto.infrontxtask.data.result.ApiResult
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("fixtures.json")
    suspend fun getFixtures(): Response<ApiFixture>

    @GET("results.json")
    suspend fun getResults(): Response<ApiResult>
}