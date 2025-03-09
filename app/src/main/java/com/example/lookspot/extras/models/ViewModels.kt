package com.example.lookspot.extras.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lookspot.extras.data.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private val _deleteStatus = MutableLiveData<Boolean>()
    val deleteStatus: LiveData<Boolean> get() = _deleteStatus

    // Eliminar un álbum por su ID
    fun deleteAlbum(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitManager.instance.deleteAlbum(id)
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Álbum eliminado")
                    _deleteStatus.value = true
                } else {
                    Log.e("Retrofit", "Error al eliminar álbum: ${response.errorBody()?.string()}")
                    _deleteStatus.value = false
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Fallo en la solicitud: ${e.message}")
                _deleteStatus.value = false
            }
        }
    }
}
