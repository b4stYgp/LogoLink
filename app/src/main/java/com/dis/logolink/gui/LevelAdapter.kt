package com.dis.logolink.gui

import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.content.Intent
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.levels_item.view.*

class LevelAdapter(var levelNames: List<String?>): RecyclerView.Adapter<LevelAdapter.ViewHolder>() {

    //View holder class with attributes and init function
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.levelx_name)

        init{

            //On click listener
            itemView.setOnClickListener{
                Toast.makeText(it.context, it.levelx_name.text, Toast.LENGTH_LONG).show()
                //open LevelActivity
                it.context.startActivity(
                    Intent(it.context, LevelActivity::class.java)
                        .putExtra("levelname",
                            it.levelx_name.text.toString().substringAfter("level")))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.levels_item, parent, false)
        return ViewHolder(view)
    }

    //Set holder attributes
    override fun onBindViewHolder(holder: LevelAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = levelNames[position]!!.substringBefore(".")
    }

    override fun getItemCount(): Int {
        return levelNames.size
    }
}