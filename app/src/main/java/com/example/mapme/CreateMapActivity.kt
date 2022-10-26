package com.example.mapme

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mapme.databinding.ActivityCreateMapBinding
import com.example.mapme.model.Place
import com.example.mapme.model.UserMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max


private const val TAG = "CreateMapActivity"
class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private var markers: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)
        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.view?.let {
            Snackbar.make(it,"Long press to add a marker",Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok") {}
                .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //chek that the item is save icon
        if (item.itemId == R.id.miSave){
            Log.i(TAG,"Tapped on save!")
            if(markers.isEmpty()){
                Toast.makeText(this, "There must be at least one marker on the top", Toast.LENGTH_LONG).show()
                return true
            }
            val places = markers.map { marker -> marker.title?.let { marker.snippet?.let { it1 ->
                Place(it,
                    it1,marker.position.latitude, marker.position.longitude)
            } } }
            val userMap = intent.getStringExtra(EXTRA_MAP_TITLE)?.let { UserMap(it,places) }
            val data = Intent(this@CreateMapActivity, MainActivity::class.java)
            data.putExtra(EXTRA_USER_MAP,userMap)
            setResult(RESULT_OK, data)
            finish()
            return true
        }
        when (item.itemId) {
            R.id.normal_map -> {
                Log.i(TAG,"Tapped on normal map!")
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.hybrid_map -> {
                Log.i(TAG,"Tapped on Hybrid map!")
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                return true
            }
            R.id.satellite_map -> {
                Log.i(TAG,"Tapped on normal map!")
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.terrain_map -> {
                Log.i(TAG,"Tapped on normal map!")
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener {markerToDelete->
            Log.i(TAG, "onWindowClickListener - delete this marker")
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }

        mMap.setOnMapLongClickListener { LatLng->
            Log.i(TAG, "onMapLongClickListener")
            showAlertDialog(LatLng)
        }
//        Add a dummy marker location
//        Zoom Levels
//        1:World
//        5:Landmass/Continent
//        10:City
//        15:Streets
//        20:Building
        val westBengal = LatLng(23.004612091135986, 86.96060591571582)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(westBengal,8f))
    }

    private fun showAlertDialog(latLng: LatLng) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)
        val dialog = AlertDialog.Builder(this).setTitle("Create a marker")
            .setView(placeFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Ok",null)
            .show()
        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener{
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()
            val description = placeFormView.findViewById<EditText>(R.id.etDescription).text.toString()
            // Exception case for empty title and description
            if(title.trim().isEmpty() || description.trim().isEmpty()){
                Toast.makeText(this, "Place must have non-empty title and description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val marker = mMap.addMarker(MarkerOptions().position(latLng)
                .title(title)
                .snippet(description)
                .draggable(true)
                .anchor(0.5f, 0.5f)
                .rotation(20.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))

            if (marker != null) {
                markers.add(marker)
                dropPinEffect(marker)
            }
            dialog.dismiss()
        }

    }
    private fun dropPinEffect(marker: Marker) {
        // Handler allows us to repeat a code block after a specified delay
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val duration: Long = 1500

        // Use the bounce interpolator
        val interpolator: Interpolator = BounceInterpolator()

        // Animate marker with a bounce updating its position every 15ms
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                // Calculate t for bounce based on elapsed time
                val t = max(
                    1 - interpolator.getInterpolation(elapsed.toFloat() / duration), 0f)
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t)
                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15)
                } else { // done elapsing, show window
                    marker.showInfoWindow()
                }
            }
        })
    }
}