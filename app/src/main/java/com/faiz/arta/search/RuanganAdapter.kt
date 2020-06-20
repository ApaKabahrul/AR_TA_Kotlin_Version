package com.faiz.arta.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.faiz.arta.R


internal class RuanganAdapter(
    private val mContext: Context,
    ruangan: ArrayList<Ruangan>
) :
    RecyclerView.Adapter<RuanganAdapter.RuanganViewHolder>() {
    private var mRuangan: ArrayList<Ruangan>
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RuanganViewHolder {
        val v: View =
            LayoutInflater.from(mContext).inflate(R.layout.list_layout, parent, false)
        return RuanganViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: RuanganViewHolder,
        position: Int
    ) {
        val currentItem: Ruangan = mRuangan[position]
        val gedung: String = currentItem.nama_gedung
        val deskripsi: String = currentItem.deskripsi
        val ruang: String = currentItem.daftar_ruangan

        holder.mTextViewGedung.text = gedung
        holder.mTextViewDeskripsi.text = deskripsi
        holder.mTextViewRuangan.text = ruang
    }

    override fun getItemCount(): Int {
        return mRuangan.size
    }

    fun setfilter(filterRuang: ArrayList<Ruangan>) {
        mRuangan =  ArrayList()
        mRuangan.addAll(filterRuang)
        notifyDataSetChanged()
    }

    inner class RuanganViewHolder(itemView: View) : ViewHolder(itemView) {
        var mTextViewGedung: TextView
        var mTextViewDeskripsi: TextView
        var mTextViewRuangan: TextView

        init {
            mTextViewGedung = itemView.findViewById(R.id.nama_gedung)
            mTextViewDeskripsi = itemView.findViewById(R.id.deskripsi)
            mTextViewRuangan = itemView.findViewById(R.id.daftar_ruangan)

            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onItemClick(position)
                    }
                }
            }
        }
    }

    init {
        mRuangan = ruangan
    }
}