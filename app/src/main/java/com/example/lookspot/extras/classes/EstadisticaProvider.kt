package com.example.lookspot.extras.classes


import com.example.lookspot.extras.models.Estadistica
import com.example.lookspot.extras.models.Song
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
object EstadisticaProvider {
    //private val firestore = FirebaseFirestore.getInstance()
    //La variable s'inicialitzarà la primera vegada que s'utilitzi.
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
            val doc = db.collection("Devices").document(idDispositiu)
                .get()
                .await()
            val valorbbdd=doc.toObject<Estadistica>()
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

    suspend  fun guardarEstadistica(idDispositiu:String, estadistica:Estadistica): Result<Unit> {
        return try {
            db.collection("Devices").document(idDispositiu).set(estadistica).await()
            dataEstadistica=estadistica
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
        dataEstadistica.numResults = listOfSongs.size
    }

    fun afegeixCategories(listOfSongs: List<Song>){
        // Uso de grouping para segregar por los tipos que puede haber de song
        val conteoTipos = listOfSongs.groupingBy { it.albumType.lowercase() }.eachCount()

        dataEstadistica.numSingle = conteoTipos["single"] ?: 0
        dataEstadistica.numAlbum = conteoTipos["album"] ?: 0
        dataEstadistica.numComp = conteoTipos["compilation"] ?: 0
    }

    fun afegeixDuracioNovaCancio(listOfSongs: List<Song>) {
        val totalDuracion = listOfSongs.sumOf { it.duracion } // total en segundos
        val duracionMediaSegundos = if (listOfSongs.isNotEmpty()) totalDuracion / listOfSongs.size else 0L
        val duracionMediaMinutos = duracionMediaSegundos / 60L

        dataEstadistica.averageSongDuration = duracionMediaMinutos
    }
}