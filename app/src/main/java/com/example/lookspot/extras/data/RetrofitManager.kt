package com.example.lookspot.extras.data

import com.example.lookspot.extras.models.Album
import com.example.lookspot.extras.models.Song
import com.example.lookspot.extras.models.User
import com.example.lookspot.extras.models.UserLogin
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {
    //Pa futuro
    @GET("/track/{id}")
    suspend fun getSong(@Path("id") id: Int):Response<Song>

    @GET("/app/ask/{query}")
    suspend fun listOfSongs(@Path("query") query:String):Response<List<Song>>

    @POST("/user/login")
    suspend fun userLog(@Body user: UserLogin): Response<User>

//// Próxima tarea de M7
//    @PUT("/album/{album_id}")
//    suspend fun changeAlbum(@Path("album_id") albumId:Int):Response<Album>

    @DELETE("/album/{album_id}")
    suspend fun deleteAlbum(@Path("album_id") albumId:Int):Response<Unit>
}



object RetrofitManager{
    private const val BASE_URL = "https://smcardona.tech"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //Desde aqui es posible colocar timeouts a las respuestas o asignar un Token si la app necesita uno
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    //El retrofitService se inicia a partir de la instancia en ViewModels
    val instance: RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RetrofitService::class.java)
    }
}