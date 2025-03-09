package com.example.lookspot.extras.data

import com.example.lookspot.R
import com.example.lookspot.extras.models.Album
import com.example.lookspot.extras.models.AlbumCreate
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
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
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

    val inputStream: InputStream = resources.openRawResource(R.raw.server)
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val certificate = certificateFactory.generateCertificate(inputStream) as X509Certificate
    inputStream.close()

    // Crear un KeyStore y agregar el certificado
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
    keyStore.load(null, null)
    keyStore.setCertificateEntry("server", certificate)

    // Crear un TrustManager que confíe en el certificado
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)
    val trustManagers = trustManagerFactory.trustManagers
    val trustManager = trustManagers[0] as X509TrustManager

    // Configurar el SSLContext con el TrustManager
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, arrayOf(trustManager), null)

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