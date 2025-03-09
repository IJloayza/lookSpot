package com.example.lookspot.extras.data

import com.example.lookspot.extras.models.Album
import com.example.lookspot.extras.models.AlbumCreate
import com.example.lookspot.extras.models.Song
import com.example.lookspot.extras.models.User
import com.example.lookspot.extras.models.UserLogin
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.security.SecureRandom
import java.security.cert.CertificateException
import javax.net.ssl.*
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext

interface RetrofitService {
    //Pa futuro
    @GET("/track/{id}")
    suspend fun getSong(@Path("id") id: Int):Response<Song>

    @GET("/app/ask/{query}")
    suspend fun listOfSongs(@Path("query") query:String):Response<List<Song>>

    @POST("/user/login")
    suspend fun userLog(@Body email: UserLogin): Response<User>

    @POST("/album/")
    suspend fun postAlbum(@Body album: AlbumCreate): Response<Album>

    @POST("/album/{album_id}/cancion")
    suspend fun postSongInAlbum(@Body albumId: Int,@Body song:Song): Response<Song>

//// Próxima tarea de M7
//    @PUT("/album/{album_id}")
//    suspend fun changeAlbum(@Path("album_id") albumId:Int):Response<Album>

    @DELETE("/album/{album_id}")
    suspend fun deleteAlbum(@Path("album_id") albumId:Int):Response<Unit>

    @DELETE("/album/{album_id}/cancion/{cancion_id}")
    suspend fun deleteSong(@Path("album_id")albumId:Int, @Path("cancion_id")cancionId:Int):Response<Unit>
}



object RetrofitManager{
    private const val BASE_URL = "https://smcardona.tech"

    //Desde aqui es posible colocar timeouts a las respuestas o asignar un Token si la app necesita uno
    private val client = getUnsafeOkHttpClient()

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

private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}