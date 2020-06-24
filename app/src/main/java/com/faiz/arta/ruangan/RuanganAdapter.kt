package com.faiz.arta.ruangan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faiz.arta.R
import com.faiz.arta.gedung.DataGedung
import kotlinx.android.synthetic.main.list_ruangan.view.*

class RuanganAdapter (val dataRuangan: DataGedung): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_ruangan, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.daftar_ruangan.text = dataRuangan.daftar_ruangan.get(position).ruangan
    }

    override fun getItemCount(): Int {
        return dataRuangan.daftar_ruangan.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val daftar_ruangan = view.daftar_ruangan
}
