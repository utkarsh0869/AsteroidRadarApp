package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView 
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class Adapter1: RecyclerView.Adapter<Adapter1.ViewHolder>() {

    var data = listOf<Asteroid>()
        set(value)  {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.preview_asteroid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = data[position]
        holder.asteroidName.text =  asteroid.codename
        holder.asteroidDetectionDate.text = asteroid.closeApproachDate
        if(asteroid.isPotentiallyHazardous) {
            holder.asteroidStatusImage.setImageResource(R.drawable.ic_status_potentially_hazardous)
        } else {
            holder.asteroidStatusImage.setImageResource(R.drawable.ic_status_normal)
        }
    }

    override fun getItemCount(): Int {
        Log.i("Rv", data.size.toString())
        return data.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView = itemView.findViewById(R.id.asteroidName)
        val asteroidDetectionDate: TextView = itemView.findViewById(R.id.asteroidDate)
        val asteroidStatusImage : ImageView = itemView.findViewById(R.id.asteroidStatusImage)
    }

}
/**
 * ViewHolder that holds a single [TextView].
 *
 * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
 * to the RecyclerView such as where on the screen it was last drawn during scrolling.
 */
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView) {

}
