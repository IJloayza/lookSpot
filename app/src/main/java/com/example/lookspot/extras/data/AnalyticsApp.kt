package com.example.lookspot.extras.data

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


data class Statistics(
    var tirades: Int = 0,
    var numdobles: Int = 0,
    var daus: ArrayList<Int> = arrayListOf<Int>(0, 0, 0, 0, 0, 0)
)

class AnalyticsApp : Application() {

    companion object {
        var idDispositiu = ""
        var estadistica = Statistics()
    }

    //La variable s'inicialitzarà la primera vegada que s'utilitzi.
    val db: FirebaseFirestore by lazy { Firebase.firestore }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()

        //Es desaconsella utilitzar ids lligats al dispositiu,
        //https://developer.android.com/identity/user-data-ids
        idDispositiu = Settings.Secure.getString(
            getApplicationContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )

        //Obtenim les dades de la base de dades.
        //Guardarem les tirades en la col·lecció Devices.
        //Per cada Device(identificat amb un id), es guardaran les estadístiques.
        val doc = db.collection("Devices").document(idDispositiu)

        //Obtenim el document corresponent al nostre dispositiu
        doc.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    //El nostre dispositiu ja estava registrat
                    val estadisticabbdd = documentSnapshot.toObject<Statistics>()
                    estadistica = estadisticabbdd!!

                } else {
                    //El nostre dispositiu no estava registrat, i el guardem amb valors per defecte.
                    db.collection("Devices").document(idDispositiu).set(estadistica)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error en caso de fallo al obtener el documento
                Log.i("App_onCreate", "Error al comprobar la existencia del documento: $exception")
            }
    }

    fun saveStats() {
        db.collection("Devices").document(idDispositiu).set(estadistica)
            .addOnSuccessListener {
                Log.i("saveStats","Dades guardades correctament")
            }
            .addOnFailureListener {
                Log.i("saveStats",it.message.toString())
                throw it
            }
    }
    fun resetStats(){
        estadistica= Statistics()
    }
}