package com.example.finalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalsproject.databinding.FragmentGarageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class GarageFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var adapter: GarageAdapter
    private val homeFragment = HomeFragment() // preload home

    // binding reference
    private var _binding: FragmentGarageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGarageBinding.inflate(inflater, container, false)

        adapter = GarageAdapter() // recyclerview doesnt know how to display posts, so we need the adapter that binds data into those views (posts)
        binding.garageRecycler.adapter = adapter
        binding.garageRecycler.layoutManager = LinearLayoutManager(requireContext())

        database = FirebaseDatabase.getInstance().getReference("garagePosts") // get data from garagePosts section

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<GaragePost>()
                for (child in snapshot.children) {
                    val post = child.getValue(GaragePost::class.java)
                    post?.let { posts.add(it) }
                }
                adapter.submitList(posts) // the function takes data from the database and assigns them to each garagePost
            }

            override fun onCancelled(error: DatabaseError) {} // gpt wrote this function and idk what this is so i just leave it there
        })

        // back arrow navigation
        binding.garageBackarrowIcon.setOnClickListener {
            (activity as MainActivity).navigateTo(HomeFragment())
        }

        return binding.root // idk what this is
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
