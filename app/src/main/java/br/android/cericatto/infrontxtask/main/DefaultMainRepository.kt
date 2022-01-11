package br.android.cericatto.infrontxtask.main

import br.android.cericatto.infrontxtask.data.Api
import br.android.cericatto.infrontxtask.data.fixture.ApiFixture
import br.android.cericatto.infrontxtask.data.result.ApiResult
import br.android.cericatto.infrontxtask.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: Api
) : MainRepository {

    override suspend fun getFixtures(): Resource<ApiFixture> {
        return try {
            val response = api.getFixtures()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

    override suspend fun getResults(): Resource<ApiResult> {
        return try {
            val response = api.getResults()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}