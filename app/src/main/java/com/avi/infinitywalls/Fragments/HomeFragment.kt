package com.avi.infinitywalls.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avi.infinitywalls.Adapters.CarouselAdapter
import com.avi.infinitywalls.Adapters.CarouselModel
import com.avi.infinitywalls.R
import com.avi.infinitywalls.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val list = ArrayList<CarouselModel>()
    private lateinit var carouselAdapter: CarouselAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        return homeBinding.root
    }

    companion object {}

}