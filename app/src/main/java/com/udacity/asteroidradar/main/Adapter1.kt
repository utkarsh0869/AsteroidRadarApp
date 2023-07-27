package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView 
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class Adapter1(private val clickListener: OnItemClickListener): RecyclerView.Adapter<Adapter1.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(asteroid: Asteroid)
    }
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
        holder.bind(asteroid, clickListener)

    }

    override fun getItemCount(): Int {
        Log.i("Rv", data.size.toString())
        return data.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView = itemView.findViewById(R.id.asteroidName)
        val asteroidDetectionDate: TextView = itemView.findViewById(R.id.asteroidDate)
        val asteroidStatusImage : ImageView = itemView.findViewById(R.id.asteroidStatusImage)

        fun bind(asteroid: Asteroid, clickListener: OnItemClickListener) {
            asteroidName.text =  asteroid.codename
            asteroidDetectionDate.text = asteroid.closeApproachDate

            if(asteroid.isPotentiallyHazardous) {
                asteroidStatusImage.setImageResource(R.drawable.ic_status_potentially_hazardous)
            } else {
                asteroidStatusImage.setImageResource(R.drawable.ic_status_normal)
            }

            //set the click listener on the item view
            itemView.setOnClickListener {
                clickListener.onItemClick(asteroid)
            }
        }
    }

}
