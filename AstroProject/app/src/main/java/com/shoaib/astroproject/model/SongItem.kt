package com.shoaib.astroproject.model

import android.os.Parcelable

class SongItem {
    var image = 0
    var song = 0
    var songName = ""
    var songListening = ""
    var songLength = ""

    constructor(image: Int, song: Int, songName: String, songListening: String, songLength: String) {
        this.image = image
        this.song = song
        this.songName = songName
        this.songListening = songListening
        this.songLength = songLength
    }
}