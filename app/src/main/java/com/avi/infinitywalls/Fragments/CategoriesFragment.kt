package com.avi.infinitywalls.Fragments

import CategoriesRecyclerViewAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avi.infinitywalls.Adapters.CategoriesRecyclerViewModel
import com.avi.infinitywalls.R
import com.avi.infinitywalls.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {
    private lateinit var CategoriesBinding:FragmentCategoriesBinding

    private val rvList=ArrayList<CategoriesRecyclerViewModel>()
    private lateinit var CategoriesRvAdapter:CategoriesRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          CategoriesBinding=FragmentCategoriesBinding.inflate(inflater,container,false)

            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_black_pika,"A.I Wallpapers"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_art,"Abstract"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_goldenfox,"Amoled"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.girl,"Anime"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_blackhair_girl,"Exclusive"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_gow,"Games"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_whiteh,"Gradient"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_pop_girl,"Minimal"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_holy_owl,"Nature"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_drag_head,"Shapes"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_gamethron,"Shows"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_luffy2,"Sports"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_drag_green,"Stock"))
            rvList.add(CategoriesRecyclerViewModel(R.drawable.grid_leonado,"Superheroes"))

         CategoriesRvAdapter= CategoriesRecyclerViewAdapter()
        CategoriesBinding.CategoriesRv.adapter = CategoriesRvAdapter
        CategoriesRvAdapter.submitList(rvList)

        return  CategoriesBinding.root
    }

    companion object {}
}