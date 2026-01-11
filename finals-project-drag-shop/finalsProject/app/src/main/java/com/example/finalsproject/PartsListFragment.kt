package com.example.finalsproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PartsListFragment : Fragment(R.layout.fragment_partslist) {

    private lateinit var adapter: PartsListAdapter // adapter for recyclerview
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var noResultsText: TextView // textview for no results

    private var allParts: List<PartsListItem> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleText = view.findViewById<TextView>(R.id.partsListText) // category title goes to partsListText
        val category = arguments?.getString("category") ?: "track" // get the argument called category, or use a default (track) if it’s missing

        titleText.text = when (category) { // go to which parts list (according to category)
            "drag" -> "DRAG PARTS"
            "track" -> "TRACK/DRIFT PARTS"
            else -> "PARTS LIST"
        }

        val backArrow = view.findViewById<ImageView>(R.id.partsListBackarrowIcon)
        backArrow.setOnClickListener {
            (activity as MainActivity).navigateTo(ShopItemsFragment()) // goes back to shopItemsFragment
        }

        recyclerView = view.findViewById(R.id.partsListRecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        noResultsText = view.findViewById(R.id.noResultsText) // for search no results

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
        recyclerView.adapter = adapter



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

        val searchInput = view.findViewById<EditText>(R.id.search_input) // search bar
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim() // takes search input, converts to string and trims off extra spaces

                val filtered = if (query.isEmpty()) { // if search box is empty then show all parts
                    allParts
                } else {
                    allParts.filter { it.name.contains(query, ignoreCase = true) } // if not then show filtered parts (case insensitive)
                }

                if (filtered.isEmpty()) {
                    adapter.updateList(emptyList())
                    noResultsText.visibility = View.VISIBLE // if no results, show no results text
                } else {
                    adapter.updateList(filtered)
                    noResultsText.visibility = View.GONE // if results, hide no results text
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {} // to react to the previous text state right before the users edit happens.
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {} // this is for live search (responds immediately as the user types)
        })
    }
}
