package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.example.dispositivosmoviles.logic.list.MarvelChars
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private val items: List<MarvelChars>,
    // En Java es Void, en Kotlin es Unit
    private var fnClick: (MarvelChars) -> Unit
) :

    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit) {
            // En esta funcion se realizan los cambios
            println("Recibiendo a ${item.nombre}")
            //binding.imageView2.bringToFront()
            binding.marvelTitle.text = item.nombre
            binding.marvelDesc.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imageView2)

            // itemView: el elemento en cualquier parte del elemento
            itemView.setOnClickListener {
                /*Snackbar.make(
                    binding.imageView2,
                    item.nombre,
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.rgb(247, 147, 76)).show()
                 */
                fnClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

}