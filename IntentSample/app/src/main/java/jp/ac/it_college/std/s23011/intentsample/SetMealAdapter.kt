package jp.ac.it_college.std.s23011.intentsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s23011.intentsample.databinding.SetMealRowBinding

class SetMealAdapter(
    private val data: List<SetMeal>,
    private val onItemSelected: (item: SetMeal) -> Unit
) : RecyclerView.Adapter<SetMealAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SetMealRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SetMeal, callback: (item: SetMeal) -> Unit) {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.root.setOnClickListener {
                callback(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SetMealRowBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onItemSelected)
    }
}