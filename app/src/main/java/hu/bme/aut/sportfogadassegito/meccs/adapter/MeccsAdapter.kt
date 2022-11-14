package hu.bme.aut.sportfogadassegito.meccs.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.sportfogadassegito.databinding.ItemMeccsekBinding
import hu.bme.aut.sportfogadassegito.meccs.data.Meccs

class MeccsAdapter(private val listener: MeccsClickListener) :
    RecyclerView.Adapter<MeccsAdapter.MeccsViewHolder>() {


    private val items = mutableListOf<Meccs>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MeccsViewHolder(
        ItemMeccsekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MeccsViewHolder, position: Int) {
        val meccsItem = items[position]

        holder.binding.isWin.isChecked = meccsItem.isWin
        holder.binding.tvStartingTime.text = "ToDo"
        holder.binding.tvHomeTeam.text = meccsItem.homeName
        holder.binding.tvAwayTeam.text = meccsItem.awayName
        holder.binding.tvHomeScore.text = "${meccsItem.homeScore}"
        holder.binding.tvAwayScore.text = "${meccsItem.awayScore}"
        holder.binding.tvOdds.text="${meccsItem.odds}"
        if(meccsItem.expectedWinner){
            holder.binding.tvExpectedWinner.text= meccsItem.homeName
        }else{
            holder.binding.tvExpectedWinner.text= meccsItem.awayName
        }


        holder.binding.isWin.setOnCheckedChangeListener { buttonView, isChecked ->
            meccsItem.isWin = isChecked
            listener.onItemChanged(meccsItem)
        }
        holder.binding.root.setOnClickListener{listener.onItemChanged(meccsItem)}
        holder.binding.ibRemove.setOnClickListener{listener.onItemDeleted(meccsItem)}
    }

    override fun getItemCount(): Int = items.size

    interface MeccsClickListener {
        fun onItemChanged(item: Meccs)
        fun onItemDeleted(item: Meccs)
    }

    fun addItem(item: Meccs) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<Meccs>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun deleteItem(item: Meccs) {
        val place = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(place)
    }

    inner class MeccsViewHolder(val binding: ItemMeccsekBinding) : RecyclerView.ViewHolder(binding.root)
}