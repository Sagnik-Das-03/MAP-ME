package com.example.mapme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapme.databinding.ActivityDisplayMapBinding
import com.example.mapme.model.UserMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds

private const val TAG = "DisplayMapActivity"
class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayMapBinding
    private lateinit var userMap: UserMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as UserMap

        supportActionBar?.title = userMap.title
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_display_map,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal_map -> {
                Log.i(TAG,"On display map Tapped on normal map!")
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.hybrid_map -> {
                Log.i(TAG,"On display map Tapped on Hybrid map!")
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                return true
            }
            R.id.satellite_map -> {
                Log.i(TAG,"On display map Tapped on normal map!")
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.terrain_map -> {
                Log.i(TAG,"On display map Tapped on normal map!")
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

        Log.i(TAG, "user map to render : ${userMap.title}")

        val boundsBuilder = LatLngBounds.builder()
        for(place in userMap.places) {
            val latLng = place?.let { LatLng(it.latitude,place.longitude) }
            if (latLng != null) {
                boundsBuilder.include(latLng)
            }
            latLng?.let { MarkerOptions().position(it)
                .title(place.title)
                .snippet(place.description)
                .draggable(false)
                .anchor(0.5f, 0.5f)
                .rotation(20.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)) }
                ?.let { mMap.addMarker(it) }
        }
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,20))

    }
}