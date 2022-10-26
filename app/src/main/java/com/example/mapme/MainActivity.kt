package com.example.mapme

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.mapme.model.UserMap
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
private const val TAG = "MainActivity"
private const val FILENAME = "UserMaps.data"
class MainActivity : AppCompatActivity() {
    private lateinit var rvMaps: RecyclerView
    private lateinit var fabCreateMap: FloatingActionButton
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var userMaps: MutableList<UserMap>
    private lateinit var mapsAdapter: MapsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View binding
        rvMaps = findViewById(R.id.rvMaps)
        fabCreateMap = findViewById(R.id.fabCreateMap)


        userMaps =deserializeUserMaps(this).toMutableList()

        //Set layout manager on the recycler view
        rvMaps.layoutManager = LinearLayoutManager(this)
        //Set adapter on the recycler view
        mapsAdapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "on item click $position")
                //When user taps on view in recycler view, navigates to new activity
                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
                Animatoo.animateSlideLeft(this@MainActivity)
            }

            override fun onItemLongClick(position: Int) {
                Log.i(TAG, "onItemLongClick at position $position")
                val dialog =
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Delete this map?")
                        .setMessage("Are you sure you want to delete this map?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("OK", null)
                        .show()
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    userMaps.removeAt(position)
                    mapsAdapter.notifyItemRemoved(position)
                    mapsAdapter.notifyItemRangeChanged(position, mapsAdapter.itemCount)
                    serializeUserMaps(this@MainActivity, userMaps)
                    dialog.dismiss()
                }
            }

        })
        rvMaps.adapter = mapsAdapter

        fabCreateMap.setOnClickListener {
            Log.i(TAG, "Tap on fab")
            showAlertDialog()
        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val data = result.data
                    // Do operations
                    val userMap : UserMap= data!!.getSerializableExtra(EXTRA_USER_MAP) as UserMap
                    Log.i(TAG,"onActivityResult with new map title -> ${userMap.title}")
                    userMaps.add(userMap)
                    mapsAdapter.notifyItemInserted(userMaps.size-1)
                    serializeUserMaps(this, userMaps)
                }

            }
    }

    private fun openSecondActivityForResult(title: String) {
        val intent = Intent(this, CreateMapActivity::class.java)
        intent.putExtra(EXTRA_MAP_TITLE,title)
        activityResultLauncher.launch(intent)
    }


    private fun serializeUserMaps(context: Context, userMaps: List<UserMap>) {
        Log.i(TAG, "serializeUserMaps")
        ObjectOutputStream(FileOutputStream(getDataFile(context))).use { it.writeObject(userMaps) }
    }

    private fun deserializeUserMaps(context: Context) : List<UserMap> {
        Log.i(TAG, "deserializeUserMaps")
        val dataFile = getDataFile(context)
        if (!dataFile.exists()) {
            Log.i(TAG, "Data file does not exist yet")
            return emptyList()
        }
        ObjectInputStream(FileInputStream(dataFile)).use { return it.readObject() as List<UserMap> }
    }

    private fun getDataFile(context: Context) : File {
        Log.i(TAG, "Getting file from directory ${context.filesDir}")
        return File(context.filesDir, FILENAME)
    }
    private fun showAlertDialog() {
        val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Map Title")
            .setView(mapFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Ok",null)
            .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val title = mapFormView.findViewById<EditText>(R.id.etMapTitle).text.toString()

            // Exception case for empty title and description
            if(title.trim().isEmpty() ){
                Toast.makeText(this, "Place must have non-empty title and description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //Navigate to CreateMapActivity
            val intent = Intent(this, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE,title)
            openSecondActivityForResult(title)
            dialog.dismiss()
        }

    }

}

