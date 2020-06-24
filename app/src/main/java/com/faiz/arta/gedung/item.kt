package com.faiz.arta.gedung
import java.io.Serializable

data class DataGedung(
    var nama_gedung: String? = "",
    var deskripsi: String? = "",
    var daftar_ruangan: ArrayList<DataRuangan>
) : Serializable

data class DataRuangan(
    var ruangan: String? = ""
) : Serializable