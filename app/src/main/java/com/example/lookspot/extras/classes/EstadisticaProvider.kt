package com.example.lookspot.extras.classes


import com.example.lookspot.extras.models.Estadistica
import com.example.lookspot.extras.models.Song
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
object EstadisticaProvider {
    //private val firestore = FirebaseFirestore.getInstance()
    //La variable s'inicialitzarà la primera vegada que s'utilitzi.
    private const val USER_STATS_COLLECTIONS = "UserStats"

    val db: FirebaseFirestore by lazy { Firebase.firestore }
    var dataEstadistica=Estadistica()

    //Retorna Unit perque no necessitem cap valor
    //només si ha funcionat o no. El valor d'estadistica es guarda
    //en la variable estadistica.
    suspend fun carregarEstadistica(idDispositiu:String): Result<Estadistica> {
        return try {

            //Obtenim les dades de la base de dades.
            //Guardarem les tirades en la col·lecció Devices.
            //Per cada Device(identificat amb un id), es guardaran les estadístiques.
            val doc = db.collection(USER_STATS_COLLECTIONS).document(idDispositiu)
                .get()
                .await()
            val valorbbdd=doc.toObject(Estadistica::class.java)
            if (valorbbdd != null) {
                dataEstadistica=valorbbdd
                Result.success(valorbbdd)
            } else {

                //si la creem per defecte, o esperem que l'usuari la guardi
                //db.collection("Devices").document(MainApp.idDispositiu).set(estadisticadefecte).await()
                //val estadisticadefecte=Estadistica()
                Result.failure(Exception("Estadística no trobada. Documentid ${idDispositiu}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend  fun guardarEstadistica(idDispositiu:String): Result<Unit> {
        return try {
            db.collection(USER_STATS_COLLECTIONS).document(idDispositiu).set(dataEstadistica).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun afegeixPrompt() {
        dataEstadistica.numPrompts++
    }

    fun afegeixFav() {
        dataEstadistica.numAsserts++
    }

    fun afegeixResult(listOfSongs: List<Song>){
        dataEstadistica.numResults += listOfSongs.size
        afegeixPrompt()
        afegeixCategories(listOfSongs)
        afegeixDuracioNovaCancio(listOfSongs)
    }

    fun afegeixCategories(listOfSongs: List<Song>){
        // Uso de grouping para segregar por los tipos que puede haber de song
        val conteoTipos = listOfSongs.groupingBy { it.album_type.lowercase() }.eachCount()

        dataEstadistica.numSingle += conteoTipos["single"] ?: 0
        dataEstadistica.numAlbum += conteoTipos["album"] ?: 0
        dataEstadistica.numCompilation += conteoTipos["compilation"] ?: 0
    }

    fun afegeixDuracioNovaCancio(listOfSongs: List<Song>) {

        val duracioNovaCancio = listOfSongs.sumOf { it.duration / 1000L / 60L }

        dataEstadistica.averageDurationSum += duracioNovaCancio


    }
}