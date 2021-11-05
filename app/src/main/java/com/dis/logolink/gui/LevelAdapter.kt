package com.dis.logolink.gui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelAdapter: RecyclerView.Adapter<LevelAdapter.ViewHolder>() {

    private var levelNames = arrayOf("Level 1", "Level 2")
    private var image = R.drawable.levels_chip

    //View holder class with attributes and init function
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView

        init{
            itemImage = itemView.findViewById(R.id.levelx_imageView)
            itemTitle = itemView.findViewById(R.id.levelx_name)

            //On click listener
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.levels_item, parent, false)
        return ViewHolder(view)
    }

    //Set holder attributes
    override fun onBindViewHolder(holder: LevelAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = levelNames[position]
        holder.itemImage.setImageResource(image)
    }

    override fun getItemCount(): Int {
        return levelNames.size
    }
}