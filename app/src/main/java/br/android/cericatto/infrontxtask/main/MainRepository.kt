package br.android.cericatto.infrontxtask.main

import br.android.cericatto.infrontxtask.data.fixture.ApiFixture
import br.android.cericatto.infrontxtask.data.result.ApiResult
import br.android.cericatto.infrontxtask.util.Resource
import retrofit2.Response

interface MainRepository {

    suspend fun getFixtures(): Resource<ApiFixture>

    suspend fun getResults(): Resource<ApiResult>
}