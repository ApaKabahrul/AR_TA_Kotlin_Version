package com.faiz.arta

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.faiz.arta.gedung.DataGedung
import com.faiz.arta.gedung.DataRuangan
import com.faiz.arta.gedung.GedungAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONException

//belom tambah onclicklistener

class SearchActivity : AppCompatActivity(){
    private var req: RequestQueue? = null

    var mDataGedung = ArrayList<DataGedung>()

    val adapter by lazy {
        GedungAdapter(mDataGedung, View.OnClickListener {
            val pos = it?.tag as Int
            val data = mDataGedung[pos]
            Log.e("KLIK", data.nama_gedung)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterRuang: ArrayList<DataGedung> = filter(mDataGedung, newText!!)
                adapter.setfilter(filterRuang)
                return true
            }

        })

        //addData()

        progressBar?.visibility = View.VISIBLE
        getInfo()

        recycler_view_gedung.layoutManager = LinearLayoutManager(this)
        recycler_view_gedung.adapter = adapter
    }

    private fun filter(mDataGedung: ArrayList<DataGedung>, query: String): ArrayList<DataGedung> {
        val filterModeList: ArrayList<DataGedung> = ArrayList()
        for (row in mDataGedung){
            val text = row.daftar_ruangan.toString().replace("\\s+","").toLowerCase()
            if(text.contains(query.replace("\\s+","").toLowerCase())){
                filterModeList.add(row)
            }
        }
        return filterModeList
    }

    private fun getInfo() {
        val url = "https://raw.githubusercontent.com/ApaKabahrul/ApaKabahrul.github.io/master/wikitude/dataPOI.json"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Log.wtf("respon",""+response)
                try {
                    for (i in 0 until response.length()) {
                        val dataPOI = response.getJSONObject(i)

                        val mDataRuangan = ArrayList<DataRuangan>()

                        Log.d("dataPOI",""+dataPOI)

                        val deskripsi = dataPOI.getString("description")

                        val daftar_ruangan = dataPOI.getJSONArray("daftar ruangan")

                        for(j in 0 until daftar_ruangan.length()){
                            mDataRuangan.add(DataRuangan(daftar_ruangan[j].toString()))
                        }

                        Log.e("isi", daftar_ruangan.toString())

                        val nama_gedung = dataPOI.getString("name")

                        val main1 = DataGedung("Gedung "+nama_gedung,deskripsi, mDataRuangan)
                        mDataGedung.add(main1)

                    }

                    progressBar?.visibility = View.GONE

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error-> // Do something when error occurred
                Log.d("ini error",""+error)
                progressBar?.visibility = View.GONE
            }
        )

        req = Volley.newRequestQueue(this)
        req?.add(jsonArrayRequest)
    }
}