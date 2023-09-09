package com.avi.infinitywalls.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avi.infinitywalls.Adapters.CarouselAdapter
import com.avi.infinitywalls.Adapters.CarouselModel
import com.avi.infinitywalls.Adapters.GridViewAdapter
import com.avi.infinitywalls.Adapters.GridViewModel
import com.avi.infinitywalls.R
import com.avi.infinitywalls.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val list = ArrayList<CarouselModel>()
    private lateinit var carouselAdapter: CarouselAdapter
    //grid view
    private val gridList=ArrayList<GridViewModel>()
    private lateinit var gridViewAdapter: GridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)


            list.add(CarouselModel(R.drawable.fire, "fire"))
            list.add(CarouselModel(R.drawable.fire_blue, "fire blue"))
            list.add(CarouselModel(R.drawable.fire_green, "fire green"))
            list.add(CarouselModel(R.drawable.flower_skull, "flower skull"))
            list.add(CarouselModel(R.drawable.fox_green, "fox"))
            list.add(CarouselModel(R.drawable.foxgirl, "fox girl"))
            list.add(CarouselModel(R.drawable.foxphone, "foxPhone"))
            list.add(CarouselModel(R.drawable.girl, "girl"))
            list.add(CarouselModel(R.drawable.harry, "harry"))
            list.add(CarouselModel(R.drawable.monkey, "monkey"))
            list.add(CarouselModel(R.drawable.rabbit, "rabbit"))
            list.add(CarouselModel(R.drawable.turtle, "turtle"))
            list.add(CarouselModel(R.drawable.white_skull, "white skull"))
            list.add(CarouselModel(R.drawable.women, "women"))

        carouselAdapter = CarouselAdapter(list, requireContext())

        homeBinding.carouselRecyclerView.adapter = carouselAdapter

        //set grid adapter
        gridList.add(GridViewModel(R.drawable.grid_cap_si,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_ironman,"ironman"))
        gridList.add(GridViewModel(R.drawable.grid_money,"money"))
        gridList.add(GridViewModel(R.drawable.grid_darkskin,"dark skin"))
        gridList.add(GridViewModel(R.drawable.grid_dragonwal,"dragon wall"))
        gridList.add(GridViewModel(R.drawable.grid_assasin,"assassin"))
        gridList.add(GridViewModel(R.drawable.grid_drag_head,"Dragon head"))
        gridList.add(GridViewModel(R.drawable.grid_movie,"movie"))
        gridList.add(GridViewModel(R.drawable.grid_goldenfox,"golden fox"))
        gridList.add(GridViewModel(R.drawable.grid_art,"art"))
        gridList.add(GridViewModel(R.drawable.grid_luffy1,"luffy1"))
        gridList.add(GridViewModel(R.drawable.grid_whiteh,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_spidi,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_gow,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_blackhair_girl,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_holy_fox,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_luffy2,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_black_cat,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_pika,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_black_pika,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_goku,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_splash,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_holy_owl,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_worrior_fox,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_drag_green,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_gamethron,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_pop_girl,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_leonado,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_game2,"capsir"))
        gridList.add(GridViewModel(R.drawable.grid_catmug,"capsir"))
         gridViewAdapter=GridViewAdapter(gridList,requireContext())
        homeBinding.homeFragmentRecyclerView.adapter=gridViewAdapter


        return homeBinding.root
    }

    companion object {}

}