package com.example.lookspot.extras.models

data class Estadistica (
    var numPrompts: Int = 0,
    var numResults: Int = 0,
    var numAsserts: Int = 0,
    var numCompilation: Int = 0,
    var numAlbum: Int = 0,
    var numSingle: Int = 0,
    var averageDurationSum: Long = 0
) {
    fun getAverageSongDuration(): Long {
        return if (numResults > 0) averageDurationSum / numResults else 0

    }
}