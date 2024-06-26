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
    private lateinit var stickyTextView: TextView
    private lateinit var carouselRecyclerView: RecyclerView

    private val list: ArrayList<CarouselModel> by lazy {
        arrayListOf(
            CarouselModel(R.drawable.fire, "fire"),
            CarouselModel(R.drawable.fire_blue, "fire blue"),
            CarouselModel(R.drawable.fire_green, "fire green"),
            CarouselModel(R.drawable.flower_skull, "flower skull"),
            CarouselModel(R.drawable.fox_green, "fox"),
            CarouselModel(R.drawable.foxgirl, "fox girl"),
            CarouselModel(R.drawable.foxphone, "foxPhone"),
            CarouselModel(R.drawable.girl, "girl"),
            CarouselModel(R.drawable.harry, "harry"),
            CarouselModel(R.drawable.monkey, "monkey"),
            CarouselModel(R.drawable.rabbit, "rabbit"),
            CarouselModel(R.drawable.turtle, "turtle"),
            CarouselModel(R.drawable.white_skull, "white skull"),
            CarouselModel(R.drawable.women, "women")
        )
    }

    private val gridList: ArrayList<GridViewModel> by lazy {
        arrayListOf(
            GridViewModel(R.drawable.space,"Space"),
            GridViewModel(R.drawable.space1,"Space"),
            GridViewModel(R.drawable.space2,"Space"),
            GridViewModel(R.drawable.space3,"Space"),
            GridViewModel(R.drawable.venom,"Venom"),
            GridViewModel(R.drawable.superman,"Super Man"),
            GridViewModel(R.drawable.spider_man, "spider_man"),
            GridViewModel(R.drawable.cap, "cap"),
            GridViewModel(R.drawable.goku_red, "goku_red"),
            GridViewModel(R.drawable.spider2, "spider2"),
            GridViewModel(R.drawable.tangi, "tangi"),
            GridViewModel(R.drawable.giyu_tomioka, "giyu"),
            GridViewModel(R.drawable.spider3, "spider3"),
            GridViewModel(R.drawable.madara, "madara"),
            GridViewModel(R.drawable.sukuna_curse, "sukuna"),
            GridViewModel(R.drawable.hashira, "hashira"),
            GridViewModel(R.drawable.pain_akatsuki, "pain_akatsuki"),
            GridViewModel(R.drawable.naruto_lake, "naruto_lake"),
            GridViewModel(R.drawable.your_name, "your_name"),
            GridViewModel(R.drawable.family, "family"),
            GridViewModel(R.drawable.vespi, "merge"),
            GridViewModel(R.drawable.kimetsu_no_yaiba, "kimetsu"),
            GridViewModel(R.drawable.zenitsu_fanart, "zenitsu"),
            GridViewModel(R.drawable.zenitsu_lightning, "zenitsu"),
            GridViewModel(R.drawable.kokushibou_demon, "kokushibou"),
            GridViewModel(R.drawable.kokushibo, "kokushibo"),
            GridViewModel(R.drawable.vibe, "vibe"),
            GridViewModel(R.drawable.itachi, "itachi"),
            GridViewModel(R.drawable.this_is_good, "this_is_good"),
            GridViewModel(R.drawable.kk, "kk"),
            GridViewModel(R.drawable.doodle, "doodle"),
            GridViewModel(R.drawable.space_light, "space_light"),
            GridViewModel(R.drawable.squad, "squad"),
            GridViewModel(R.drawable.grid_art, "art"),
            GridViewModel(R.drawable.grid_luffy2, "luffy"),
            GridViewModel(R.drawable.grid_holy_owl, "holy owl"),
            GridViewModel(R.drawable.grid_blackhair_girl, "black hair girl"),
            GridViewModel(R.drawable.inosuke, "inosuke"),
            GridViewModel(R.drawable.black_sdam, "black_sdam"),
            GridViewModel(R.drawable.grid_leonado, "leonardo"),
            GridViewModel(R.drawable.grid_luffy1, "luffy"),
            GridViewModel(R.drawable.grid_black_pika, "grid_black_pika")
        )
    }

    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var gridViewAdapter: GridViewAdapter

    private var carouselRecyclerViewHeight: Int = 0
    private val originalMarginSize: Int by lazy {
        resources.getDimensionPixelSize(R.dimen.original_margin_size)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerViews()
        setupStickyHeader()
    }

    private fun setupViews() {
        stickyTextView = homeBinding.recentlyUloadedtextView
        carouselRecyclerView = homeBinding.carouselRecyclerView
    }

    private fun setupRecyclerViews() {
        setupCarouselRecyclerView()
        setupGridView()
    }

    private fun setupCarouselRecyclerView() {
        carouselAdapter = CarouselAdapter(list)
        carouselRecyclerView.adapter = carouselAdapter

        carouselRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                carouselRecyclerViewHeight = carouselRecyclerView.height
                carouselRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                adjustStickyHeader()
            }
        })
    }

    private fun setupGridView() {
        gridViewAdapter = GridViewAdapter(gridList, object : GridViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(requireContext(), SliderActivity::class.java).apply {
                    putExtra("selectedPosition", position)
                    putIntegerArrayListExtra("allImages", gridList.map { it.imageId } as ArrayList<Int>)
                    putStringArrayListExtra("allImageNames", gridList.map { it.title } as ArrayList<String>)
                }
                startActivity(intent)
            }
        })

        // Calculate number of columns dynamically
        val spanCount = calculateNoOfColumns()

        homeBinding.homeFragmentRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = gridViewAdapter

        }
    }

    // Function to calculate number of columns dynamically
    private fun calculateNoOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 120 // Change this scaling factor according to your needs
        return (dpWidth / scalingFactor).toInt()
    }
    private fun setupStickyHeader() {
        homeBinding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            adjustStickyHeader(scrollY)
        }
    }

    private fun adjustStickyHeader(scrollY: Int = 0) {
        if (scrollY >= carouselRecyclerViewHeight) {
            val offset = scrollY - carouselRecyclerViewHeight
            stickyTextView.translationY = offset.toFloat()
            val layoutParams = stickyTextView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = 0
            stickyTextView.layoutParams = layoutParams
        } else {
            stickyTextView.translationY = 0f
            val layoutParams = stickyTextView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = originalMarginSize
            stickyTextView.layoutParams = layoutParams
        }
    }
}
