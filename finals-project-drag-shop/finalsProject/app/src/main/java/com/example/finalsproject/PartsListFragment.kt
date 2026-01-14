package com.example.finalsproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalsproject.databinding.FragmentPartslistBinding
import com.google.firebase.database.*

class PartsListFragment : Fragment(R.layout.fragment_partslist) {

    private lateinit var adapter: PartsListAdapter // adapter for recyclerview
    private lateinit var database: DatabaseReference
    private var allParts: List<PartsListItem> = emptyList()

    private var _binding: FragmentPartslistBinding? = null // reference starts as null and gets assigned when the fragment’s view is created (onViewCreated). we later clear it in onDestroyView() to avoid memory leaks.
    private val binding get() = _binding!! // _binding!!!1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPartslistBinding.bind(view)

        val category = arguments?.getString("category") ?: "track" // get the argument called category, or use a default (track) if it’s missing

        binding.partsListText.text = when (category) { // go to which parts list (according to category)
            "drag" -> "DRAG PARTS"
            "track" -> "TRACK/DRIFT PARTS"
            else -> "PARTS LIST"
        }

        binding.partsListBackarrowIcon.setOnClickListener {
            (activity as MainActivity).navigateTo(ShopItemsFragment()) // goes back to shopItemsFragment
        }

        binding.partsListRecycler.layoutManager = LinearLayoutManager(requireContext()) // display the list in a vertical scrolling column (or horizontal if specified). without a layout manager, the recyclerView wouldn’t know how to display its children.

        adapter = PartsListAdapter(emptyList()) { part ->
            val fragment = BuyItemFragment().apply {
                arguments = Bundle().apply {
                    putString("name", part.name)
                    putString("description", part.description) // pass data to BuyItemFragment
                    putString("imageUrl", part.imageUrl)
                    putDouble("price", part.price)
                }
            }

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragment) // navigate to BuyItemFragment with the animation
                .addToBackStack(null)
                .commit()
        }
        binding.partsListRecycler.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("shopParts") // get data from shopParts section

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val parts = snapshot.children.mapNotNull { it.getValue(PartsListItem::class.java) }
                Log.d("PartsListFragment", "Total parts fetched: ${parts.size}") // these logs arent required for app to function, but they are (were) useful for debugging. ill leave them there.
                Log.d("PartsListFragment", "All parts: $parts")

                val selectedCategory = arguments?.getString("category")
                allParts = parts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
                Log.d("PartsListFragment", "Filtered parts count: ${allParts.size}") // uhh yeah... without logs, it would be pretty hard to make this work
                Log.d("PartsListFragment", "Filtered parts: $allParts")

                adapter.updateList(allParts) //  tells recyclerView adapter to refresh its data with the new list allParts, so the UI updates and shows only those filtered parts.
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PartsListFragment", "Firebase error: ${error.message}") // and if anything, whats the error
            }
        })

        binding.searchInput.addTextChangedListener(object : TextWatcher {  // search bar
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim() // takes search input, converts to string and trims off extra spaces

                val filtered = if (query.isEmpty()) { // if search box is empty then show all parts
                    allParts
                } else {
                    allParts.filter { it.name.contains(query, ignoreCase = true) } // if not then show filtered parts (case insensitive)
                }

                if (filtered.isEmpty()) {
                    adapter.updateList(emptyList())
                    binding.noResultsText.visibility = View.VISIBLE // if no results, show no results text
                } else {
                    adapter.updateList(filtered)
                    binding.noResultsText.visibility = View.GONE // if results, hide no results text
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // to react to the previous text state right before the users edit happens.
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} // this is for live search (responds immediately as the user types)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // again, avoid memory leak
    }
}
