package com.faiz.arta

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.faiz.arta.search.Ruangan
import com.faiz.arta.search.RuanganAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONException

//belom tambah onclicklistener

class SearchActivity : AppCompatActivity(), RuanganAdapter.OnItemClickListener {
    private var mRecyclerView: RecyclerView? = null
    private var ruanganAdapter: RuanganAdapter? = null
    private var mRuangan: ArrayList<Ruangan>? = null
    private var req: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRuangan = ArrayList()
        /*req = Volley.newRequestQueue(this)*/
        ruanganAdapter = mRuangan?.let { RuanganAdapter(this, it) }


        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterRuang: ArrayList<Ruangan> = filter(mRuangan!!, newText!!)
                ruanganAdapter?.setfilter(filterRuang)
                return true
            }
            
        })


        getInfo()
    }

    private fun filter(mRuangan: ArrayList<Ruangan>, query: String): ArrayList<Ruangan> {
        val filterModeList: ArrayList<Ruangan> = ArrayList()
        for (row in mRuangan){
            val text = row.nama_gedung.toLowerCase()
            if(text.contains(query.toLowerCase())){
                filterModeList.add(row)
            }
        }
        return filterModeList
    }

    /*private fun filter(
        hi: List<ActorsList>,
        query: String
    ): List<ActorsList>? {
        var query = query
        query = query.toLowerCase()
        val filterModeList: MutableList<ActorsList> = ArrayList()
        for (modal in hi) {
            val text: String = modal.getName().toLowerCase()
            if (text.startsWith(query)) {
                filterModeList.add(modal)
            }
        }
        return filterModeList
    }*/

    private fun getInfo() {
        val url = "https://raw.githubusercontent.com/ApaKabahrul/ApaKabahrul.github.io/master/wikitude/dataPOI.json"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Log.d("respon",""+response)
                try {
                    // Loop through the array elements
                    for (i in 0 until response.length()) {
                        // Get current json object
                        val dataPOI = response.getJSONObject(i)
                        Log.d("dataPOI",""+dataPOI)

                        val deskripsi = dataPOI.getString("description")
                        val nama_gedung = dataPOI.getString("name")

                        Log.d("deskripsi",""+deskripsi)
                        Log.d("nama gedung",""+nama_gedung)

                        mRuangan?.add(Ruangan(nama_gedung, deskripsi))
                    }

                    /*ruanganAdapter = mRuangan?.let { RuanganAdapter(this, it) }*/
                    mRecyclerView?.adapter = ruanganAdapter
                    ruanganAdapter?.setOnItemClickListener(this)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error-> // Do something when error occurred
                Toast.makeText(this, ""+error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

// Add the request to the RequestQueue.
        req = Volley.newRequestQueue(this)
        req?.add(jsonArrayRequest)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}