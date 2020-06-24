package com.faiz.arta.gedung

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faiz.arta.R
import com.faiz.arta.ruangan.RuanganAdapter
import kotlinx.android.synthetic.main.list_gedung.view.*

class GedungAdapter(var dataGedung: ArrayList<DataGedung>, val clickListener: View.OnClickListener):RecyclerView.Adapter<GedungAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_gedung, parent, false))
    }

    override fun getItemCount(): Int {
        return dataGedung.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(clickListener)
        holder.nama_gedung.text = dataGedung.get(position).nama_gedung
        holder.deskripsi.text = dataGedung.get(position).deskripsi

        val ruanganLayoutManager = LinearLayoutManager(holder.itemView.recycler_view_ruangan.context)

        holder.itemView.recycler_view_ruangan.apply {
            layoutManager = ruanganLayoutManager
            adapter = RuanganAdapter(dataGedung.get(position))
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nama_gedung = view.nama_gedung
        val deskripsi = view.deskripsi
    }

    fun setfilter(filterGedung: ArrayList<DataGedung>) {
        dataGedung = ArrayList()
        dataGedung.addAll(filterGedung)
        notifyDataSetChanged()
    }

}