package edu.uw.tillej.dotify.repository

import edu.uw.tillej.dotify.model.ArtistList
import edu.uw.tillej.dotify.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://raw.githubusercontent.com/"
class DataRepository {

    private val artistService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArtistService::class.java)


    suspend fun getAllArtist() = artistService.getAllArtist()

//    suspend fun getUser(): User = artistService.getUser()
}
interface ArtistService {
    @GET("echeeUW/codesnippets/master/allartists.json")
    suspend fun getAllArtist(): ArtistList
//
//    @GET("echeeUW/codesnippets/master/user_info.json")
//    suspend fun getUser(): User
}
