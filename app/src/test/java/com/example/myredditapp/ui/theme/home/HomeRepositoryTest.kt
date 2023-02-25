package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.ApiLoadingResponse
import com.example.myredditapp.network.ApiSuccessResponse
import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.db.HomeRoomDataSource
import com.example.myredditapp.network.models.ListingRequest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class HomeRepositoryTest {

    private var fakeHomeService: HomeService = FakeHomeService() // = mock(HomeService::class.java)
    private var fakeHomeRoomDataSource: HomeRoomDataSource = FakeHomeRoomDataSource()  // = mock(HomeRoomDataSource::class.java)

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

            repo.getPokemonAndRefresh(ListingRequest(0, 0)).drop(2).first { result ->
                assert(result is ApiSuccessResponse)
                true
            }
        }
    }
}