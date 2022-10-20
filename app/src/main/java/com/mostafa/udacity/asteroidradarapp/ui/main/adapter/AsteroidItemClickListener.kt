package com.mostafa.udacity.asteroidradarapp.ui.main.adapter

import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid

class AsteroidItemClickListener(val clickListener: (Asteroid) -> Unit) {
    fun onClick(data: Asteroid) = clickListener(data)
}