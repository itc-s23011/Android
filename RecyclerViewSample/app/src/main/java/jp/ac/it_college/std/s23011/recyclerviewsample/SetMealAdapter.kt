package jp.ac.it_college.std.s23011.recyclerviewsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import jp.ac.it_college.std.s23011.recyclerviewsample.databinding.SetMealRawBinding

class SetMealAdapter(private val data: List<SetMeal>) : RecyclerView.Adapter<SetMealAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SetMealRawBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SetMeal) {
                binding.name.text = item.name
                binding.price.text = item.price.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SetMealRawBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}