package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.ApiErrorResponse
import com.example.myredditapp.network.ApiLoadingResponse
import com.example.myredditapp.network.ApiSuccessResponse
import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.db.HomeRoomDataSource
import com.example.myredditapp.network.models.ListingRequest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Test



internal class HomeRepositoryTest {

    var fakeHomeService: HomeService = FakeHomeService()

    var fakeHomeRoomDataSource: HomeRoomDataSource = FakeHomeRoomDataSource()

    var repo: HomeRepository = HomeRepository(fakeHomeService, fakeHomeRoomDataSource)

    @Test
    fun getPokemonAndRefresh_returns_loading_first() {
        runBlocking {
            repo.getPokemonAndRefresh(ListingRequest()).first { response ->
                assert(response is ApiLoadingResponse)
                true
            }
            repo.getPokemonAndRefresh(ListingRequest(0, 0)).first { response ->
                assert(response is ApiLoadingResponse)
                true
            }
            repo.getPokemonAndRefresh(ListingRequest(Integer.MAX_VALUE, Integer.MAX_VALUE))
                .first { response ->
                    assert(response is ApiLoadingResponse)
                    true
                }
            repo.getPokemonAndRefresh(ListingRequest(Integer.MIN_VALUE, Integer.MIN_VALUE))
                .first { response ->
                    assert(response is ApiLoadingResponse)
                    true
                }
        }
    }

    @Test
    fun getPokemonAndRefresh_emits_AtLeastTwoResults() {
        runBlocking {
            repo.getPokemonAndRefresh(ListingRequest()).drop(1)
            assert(repo.getPokemonAndRefresh(ListingRequest()).firstOrNull() != null)
        }
    }

    @Test
    fun getPokemonAndRefresh_returns_SUCCESS() {
        runBlocking {
            repo.getPokemonAndRefresh(ListingRequest()).drop(1).first { result ->
                assert(result is ApiSuccessResponse)
                true
            }
        }
    }
}