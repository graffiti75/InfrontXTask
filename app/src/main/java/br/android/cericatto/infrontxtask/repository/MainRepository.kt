package br.android.cericatto.infrontxtask.repository

import br.android.cericatto.infrontxtask.data.fixture.ApiFixture
import br.android.cericatto.infrontxtask.data.result.ApiResult
import br.android.cericatto.infrontxtask.util.Resource

interface MainRepository {

    suspend fun getFixtures(): Resource<ApiFixture>

    suspend fun getResults(): Resource<ApiResult>
}