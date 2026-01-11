package com.example.finalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import androidx.cardview.widget.CardView

class GarageFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var adapter: GarageAdapter
    private val homeFragment = HomeFragment() // preload home

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garage, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.garageRecycler)
        adapter = GarageAdapter() // recyclerview doesnt know how to display posts, so we need the adapter that binds data into those views (posts)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        database = FirebaseDatabase.getInstance().getReference("garagePosts") // get data from garagePosts section

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<GaragePost>()
                for (child in snapshot.children) {
                    val post = child.getValue(GaragePost::class.java)
                    post?.let { posts.add(it) }
                }
                adapter.submitList(posts)
            } // the function takes data from the database and assigns them to each garagePost

            override fun onCancelled(error: DatabaseError) {} // gpt wrote this function and idk what this is so i just leave it there
        })

        // back arrow navigation
        val garageBackarrowIcon = view.findViewById<ImageView>(R.id.garageBackarrowIcon)
        garageBackarrowIcon.setOnClickListener {
            (activity as MainActivity).navigateTo(HomeFragment()) // back arrow navigates to homeFragment
        }




        return view // idk what this is
    }
}
