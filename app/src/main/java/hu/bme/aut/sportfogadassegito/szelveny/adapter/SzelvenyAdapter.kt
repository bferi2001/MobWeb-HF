package hu.bme.aut.sportfogadassegito.szelveny.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.sportfogadassegito.databinding.ItemSzelvenyekBinding
import hu.bme.aut.sportfogadassegito.szelveny.data.*

class SzelvenyAdapter(private val listener: SzelvenyClickListener) :
    RecyclerView.Adapter<SzelvenyAdapter.SzelvenyViewHolder>() {


        private val items = mutableListOf<Szelveny>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SzelvenyViewHolder(
            ItemSzelvenyekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


        override fun onBindViewHolder(holder: SzelvenyViewHolder, position: Int) {
            val szelvenyItem = items[position]

            holder.binding.isWin.isChecked = szelvenyItem.isWin
            holder.binding.tvDate.text = szelvenyItem.date
            holder.binding.tvDescription.text = szelvenyItem.discription
            holder.binding.tvPrice.text = "${szelvenyItem.price} HUF"
            holder.binding.tvExpectedPrize.text = "${szelvenyItem.expectedPrize} HUF"

            holder.binding.isWin.setOnCheckedChangeListener { buttonView, isChecked ->
                szelvenyItem.isWin = isChecked
                listener.onItemChanged(szelvenyItem)
            }
            holder.binding.root.setOnClickListener{listener.onItemPicked(szelvenyItem)}
            holder.binding.ibRemove.setOnClickListener{listener.onItemDeleted(szelvenyItem)

            }
        }

        override fun getItemCount(): Int = items.size

        interface SzelvenyClickListener {
            fun onItemChanged(item: Szelveny)
            fun onItemDeleted(item: Szelveny)
            fun onItemPicked(item: Szelveny)
        }

    fun addItem(item: Szelveny) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<Szelveny>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun deleteItem(item: Szelveny) {
        val place = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(place)
    }

        inner class SzelvenyViewHolder(val binding: ItemSzelvenyekBinding) : RecyclerView.ViewHolder(binding.root)
    }
