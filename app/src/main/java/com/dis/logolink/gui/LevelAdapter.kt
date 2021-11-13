package com.dis.logolink.gui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dis.logolink.level.Level1Activity

class LevelAdapter: RecyclerView.Adapter<LevelAdapter.ViewHolder>() {

    //Filler levelNames
    private var levelNames = arrayOf("Level 1", "Level 2", "Level 3", "Level 4"
        , "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10")
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
                Toast.makeText(itemView.context, "Clicked", Toast.LENGTH_LONG).show()
                //Test activity 1
                it.context.startActivity(Intent(itemView.context, Level1Activity::class.java))
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