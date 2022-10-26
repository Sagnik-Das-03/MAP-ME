package com.example.mapme

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapme.model.Place
import com.example.mapme.model.UserMap
private const val TAG = "MapsAdapter"
class MapsAdapter(private val context: Context, private val userMaps: List<UserMap>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {


    interface OnClickListener{
        fun onItemClick(position: Int)

        fun onItemLongClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_user_map,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val userMap = userMaps[position]
        holder.itemView.setOnClickListener{
            Log.i(TAG, "Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        holder.itemView.setOnLongClickListener {
            onClickListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }

        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.tvMapTitle)
         textViewTitle.text = userMap.title
        val placeOnMap = holder.itemView.findViewById<TextView>(R.id.tvPlaces)
         placeOnMap.text = userMap.places.size.toString()
    }

    override fun getItemCount(): Int = userMaps.size

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}
