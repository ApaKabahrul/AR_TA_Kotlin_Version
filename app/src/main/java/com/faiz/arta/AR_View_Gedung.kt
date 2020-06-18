package com.faiz.arta

import android.content.Intent
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Toast
import com.wikitude.architect.ArchitectStartupConfiguration
import com.wikitude.architect.ArchitectView
import kotlinx.android.synthetic.main.ar_view_gedung.*

class AR_View_Gedung : AppCompatActivity(), LocationListener {
    private var architectView: ArchitectView? = null
    private var locationProvider: LocationProvider? = null

    private val errorCallback = LocationProvider.ErrorCallback {
            Toast.makeText(this,"Please enable GPS and Network positioning in your Settings and restart the Activity", Toast.LENGTH_LONG
            ).show()
        }

    private val sensorAccuracyChangeListener = ArchitectView.SensorAccuracyChangeListener{accuracy->
        if (accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM){
            Toast.makeText(this, "Please re-calibrate compass by waving your device in a figure 8 motion", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_view_gedung)

        locationProvider = LocationProvider(this, this, errorCallback)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        WebView.setWebContentsDebuggingEnabled(true)
        val config = ArchitectStartupConfiguration()
        config.licenseKey = ""
        architectView?.onCreate(config)

        cari?.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        architectView?.onPostCreate()
        architectView?.load("https://apakabahrul.github.io/")
    }

    override fun onResume() {
        super.onResume()
        architectView?.onResume()
        locationProvider?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        architectView?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        architectView?.onPause()
        architectView?.unregisterSensorAccuracyChangeListener(sensorAccuracyChangeListener)
    }

    override fun onLocationChanged(location: Location) {
        val accuracy = if (location.hasAccuracy()) location.accuracy else 1000.toFloat()
        if (location.hasAltitude()) {
            architectView!!.setLocation(
                location.latitude,
                location.longitude,
                location.altitude,
                accuracy
            )
        } else {
            architectView!!.setLocation(
                location.latitude,
                location.longitude,
                accuracy.toDouble()
            )
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}