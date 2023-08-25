package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(
    var items: List<MarvelChars>,
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave : (MarvelChars) -> Boolean
) :

    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit,
            fnSave : (MarvelChars) -> Boolean
        ) {

            println("Recibiendo a ${item.nombre}")
            binding.marvelTitle.text = item.nombre
            binding.marvelDesc.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imageView2)


            itemView.setOnClickListener {
                fnClick(item)
            }

            binding.btnSave.setOnClickListener {
                fnSave(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick, fnSave)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItem(newItems: List<MarvelChars>){
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newItems: List<MarvelChars>){
        this.items = newItems
        notifyDataSetChanged()
    }

}