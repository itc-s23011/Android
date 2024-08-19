package jp.ac.it_college.std.s23011.frogmentsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23011.frogmentsample.databinding.FragmentMenuListBinding
import kotlinx.serialization.json.Json

class MenuListFragment : Fragment() {

    private var _binding: FragmentMenuListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setMealList = Json.decodeFromString<List<FoodMenu>>(
            resources.openRawResource(R.raw.set_meal).reader().readText()
        )
        binding.apply {
            // RecyclerView の設定
            lvMenu.apply {
                adapter = FoodMenuListAdapter(setMealList, ::order)
                val manager = LinearLayoutManager(context)
                layoutManager = manager
                addItemDecoration(DividerItemDecoration(context, manager.orientation))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun order(item: FoodMenu){
        val bundle = Bundle().apply {
            putString("menuName", item.name)
            putInt("menuPrice", item.price)
        }

//        parentFragmentManager.commit {
//            setReorderingAllowed(true)
//
//            addToBackStack("Only List")
//
//            replace(R.id.fragmentMainContainer, MenuThanksFragment::class.java, bundle)
//        }
        parentFragmentManager.setFragmentResult("selectedMenu", bundle)
    }
}