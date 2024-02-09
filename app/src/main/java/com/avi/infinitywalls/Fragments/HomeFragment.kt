package com.avi.infinitywalls.Fragments

import GridViewAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.Adapters.CarouselAdapter
import com.avi.infinitywalls.Adapters.CarouselModel
import com.avi.infinitywalls.Adapters.GridViewModel
import com.avi.infinitywalls.R
import com.avi.infinitywalls.SliderActivity
import com.avi.infinitywalls.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private val list = ArrayList<CarouselModel>()
    private lateinit var carouselAdapter: CarouselAdapter
    private val gridList = ArrayList<GridViewModel>()
    private lateinit var gridViewAdapter: GridViewAdapter

    private lateinit var stickyTextView: TextView
    private lateinit var carouselRecyclerView: RecyclerView
    private var carouselRecyclerViewHeight: Int = 0
    private var originalMarginSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the original margin size here
        originalMarginSize = resources.getDimensionPixelSize(R.dimen.original_margin_size)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = homeBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerViews
        setupCarouselRecyclerView()
        setupGridView()

        // Set up sticky header
        stickyTextView = view.findViewById(R.id.recentlyUloadedtextView)
        carouselRecyclerView = view.findViewById(R.id.carousel_recycler_view)

        // Calculate the height of the carousel RecyclerView
        carouselRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                carouselRecyclerViewHeight = carouselRecyclerView.height
                // Remove the listener to avoid multiple calls
                carouselRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        // Set an OnScrollChangeListener to handle the sticky behavior
        homeBinding.scrollView.setOnScrollChangeListener(View.OnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY >= carouselRecyclerViewHeight) {
                // Calculate the offset for the sticky header
                val offset = scrollY - carouselRecyclerViewHeight
                stickyTextView.translationY = offset.toFloat()

                // Remove the margin when the header is sticky
                val layoutParams = stickyTextView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.topMargin = 0
                stickyTextView.layoutParams = layoutParams
            } else {
                // Reset the header view to its original state
                stickyTextView.translationY = 0f
                val layoutParams = stickyTextView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.topMargin = originalMarginSize
                stickyTextView.layoutParams = layoutParams
            }
        })
    }

    private fun setupCarouselRecyclerView() {
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

        carouselAdapter = CarouselAdapter(list)
        homeBinding.carouselRecyclerView.adapter = carouselAdapter
    }

    private fun setupGridView() {
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
        gridList.add(GridViewModel(R.drawable.grid_whiteh,"white"))
        gridList.add(GridViewModel(R.drawable.grid_spidi,"spidi"))
        gridList.add(GridViewModel(R.drawable.grid_gow,"god of war"))
        gridList.add(GridViewModel(R.drawable.grid_blackhair_girl,"black hair girl"))
        gridList.add(GridViewModel(R.drawable.grid_holy_fox,"holy fox"))
        gridList.add(GridViewModel(R.drawable.grid_luffy2,"luffy"))
        gridList.add(GridViewModel(R.drawable.grid_black_cat,"black cat"))
        gridList.add(GridViewModel(R.drawable.grid_pika,"pika"))
        gridList.add(GridViewModel(R.drawable.grid_black_pika,"black pika"))
        gridList.add(GridViewModel(R.drawable.grid_goku,"goku"))
        gridList.add(GridViewModel(R.drawable.grid_splash,"splash"))
        gridList.add(GridViewModel(R.drawable.grid_holy_owl,"holy owl"))
        gridList.add(GridViewModel(R.drawable.grid_worrior_fox,"worrier fox"))
        gridList.add(GridViewModel(R.drawable.grid_drag_green,"Green Dragon"))
        gridList.add(GridViewModel(R.drawable.grid_gamethron,"game of throne"))
        gridList.add(GridViewModel(R.drawable.grid_pop_girl,"pop girl"))
        gridList.add(GridViewModel(R.drawable.grid_leonado,"leonardo"))
        gridList.add(GridViewModel(R.drawable.grid_game2,"girl"))
        gridList.add(GridViewModel(R.drawable.grid_catmug,"Cat"))

        gridViewAdapter = GridViewAdapter(gridList, object : GridViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Pass the selected position and all images to SliderActivity
                val intent = Intent(requireContext(), SliderActivity::class.java)
                intent.putExtra("selectedPosition", position)
                intent.putIntegerArrayListExtra("allImages", gridList.map { it.imageId } as ArrayList<Int>)
                intent.putStringArrayListExtra("allImageNames", gridList.map { it.title } as ArrayList<String>)
                startActivity(intent)
            }
        })

        homeBinding.homeFragmentRecyclerView.adapter = gridViewAdapter
        homeBinding.homeFragmentRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }
}
