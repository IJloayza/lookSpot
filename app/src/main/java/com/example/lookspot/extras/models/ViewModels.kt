package com.example.lookspot.extras.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lookspot.extras.classes.EstadisticaProvider
import com.example.lookspot.extras.data.RetrofitManager
import kotlinx.coroutines.launch
import java.util.ArrayList

class SongViewModel : ViewModel() {

    // LiveData para una canción
    private val _song = MutableLiveData<Song?>()
    val song: LiveData<Song?> get() = _song

    // LiveData para una lista de canciones
    private val _songs = MutableLiveData<List<Song>?>()
    val songs: LiveData<List<Song>?> get() = _songs

    // Obtener una canción por su ID
    fun getSong(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.getSong(id)
                if (response.isSuccessful) {
                    val song = response.body()
                    Log.d("Retrofit", "Song: $song")
                    _song.value = song
                } else {
                    Log.e("Retrofit", "Error: ${response.errorBody()}")
                    _song.value = null
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Error solicitud: ${e.message}")
                _song.value = null
            }
        }
    }

    // Obtener una lista de canciones por query
    fun listSongs(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.listOfSongs(query)
                if (response.isSuccessful) {
                    val songs = response.body()
                    Log.d("Retrofit", "List Songs: $songs")
                    _songs.value = songs
                } else {
                    Log.e("Retrofit", "Error: ${response.errorBody()}")
                    _songs.value = null
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Error solicitud: ${e.message}")
                _songs.value = null
            }
        }
    }

    fun postSong(albumId: Int, song: Song){
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.postSongInAlbum(albumId, song)
                if (response.isSuccessful) {
                    val song = response.body()
                    Log.d("Retrofit", "Song: $song")
                    _song.value = song
                } else {
                    Log.e("Retrofit", "Error: ${response.errorBody()}")
                    _song.value = null
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Error solicitud: ${e.message}")
                _songs.value = null
            }
        }
    }

    fun deleteSong(albumId: Int, songId: String){
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.deleteSong(albumId, songId)
                if (response.isSuccessful) {
                    val song = response.body()
                    Log.d("Retrofit", "Song eliminado")
                } else {
                    Log.e("Retrofit", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Error solicitud: ${e.message}")
            }
        }
    }
}

class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun postUserLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = UserLogin(email, password)
                val response = RetrofitManager.instance.userLog(user)
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("Retrofit", "User Existe: $user")
                    _user.value = user
                } else {
                    Log.e("Retrofit", "User No Existe: ${response.errorBody()}")
                    _user.value = null
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Fallo en la solicitud: ${e.message}")
                _user.value = null
            }
        }
    }
}

class AlbumViewModel : ViewModel() {

    // LiveData para notificar el resultado de la operación
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> get() = _album

    fun postAlbum(idUser: Int, nomAlbum: String){
        viewModelScope.launch {
            try {
                val albumCreate = AlbumCreate(idUser, nomAlbum)
                val response = RetrofitManager.instance.postAlbum(albumCreate)
                if (response.isSuccessful) {
                    val album = response.body()
                    album?.canciones = ArrayList()
                    Log.d("Retrofit", "Album Existe: $album")
                    _album.value = album
                } else {
                    Log.e("Retrofit", "Album No Existe: ${response.errorBody()}")
                    _album.value = null
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Fallo en la solicitud: ${e.message}")
                _album.value = null
            }
        }
    }
    // Eliminar un álbum por su ID
    fun deleteAlbum(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.deleteAlbum(id)
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Álbum eliminado")
                } else {
                    Log.e("Retrofit", "Error al eliminar álbum: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Fallo en la solicitud: ${e.message}")
            }
        }
    }

    fun changeAlbum(albumId: Int, albumName: String){
        viewModelScope.launch {
            try {
                val albumUpdateName = AlbumUpdate(albumName)
                val response = RetrofitManager.instance.changeAlbum(albumId, albumUpdateName)
                if (response.isSuccessful) {
                    val album = response.body()
                    if(album?.canciones.isNullOrEmpty()){
                        album?.canciones = ArrayList()
                    }
                    Log.d("Retrofit", "Album Cambiado: $album")
                    _album.value = album
                } else {
                    Log.e("Retrofit", "Album No Existe: ${response.errorBody()}")
                    _album.value = null
                }
            }catch (e: Exception){
                Log.e("Retrofit", "Fallo en la solicitud: ${e.message}")
            }
        }
    }
}

class EstadisticaViewModel(private val dataprovider: EstadisticaProvider) : ViewModel() {


    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> get() = _saved

    private val _resultEstadistica = MutableLiveData<Result<Estadistica>>()
    val resultEstadistica: LiveData<Result<Estadistica>> get() = _resultEstadistica

    //Necessitem el patró factory per a que es pugui crear el viewmodel
    //Ho fem en el companion object
    //    viewModelFactory:
    //    Aquesta funció de la biblioteca androidx.lifecycle:lifecycle-viewmodel-ktx permet crear un ViewModelProvider.Factory de manera més concisa.
    //    Pots inicialitzar el teu ViewModel amb les dependències necessàries amb
    //    private val vmodel: EstadisticaViewModel by viewModels { EstadisticaViewModel.Factory }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dataprovider = EstadisticaProvider
                EstadisticaViewModel(dataprovider)
            }
        }}

    fun cargarEstadistica(idDispositiu: String) {
        viewModelScope.launch {
            val result = dataprovider.carregarEstadistica(idDispositiu)
            _resultEstadistica.value=result
        }
    }
    fun actualitzaEstadística(){
        _resultEstadistica.value=Result.success(dataprovider.dataEstadistica)
    }
    fun resetEstadística(){
        dataprovider.dataEstadistica=Estadistica()
        _resultEstadistica.value=Result.success(dataprovider.dataEstadistica)
    }
    fun guardarEstadistica(idDispositiu:String) {
        viewModelScope.launch {
            val result= dataprovider.guardarEstadistica(idDispositiu,dataprovider.dataEstadistica)
            _saved.value=result.isSuccess
        }
    }

    fun ActualitzaEstadistica(dau1:Int, dau2:Int){
        EstadisticaProvider.afegeixTirada(dau1,dau2)
        //Actualitzem la variable per a que els observers s'enterin.
        _resultEstadistica.value=Result.success(dataprovider.dataEstadistica)

    }
}
