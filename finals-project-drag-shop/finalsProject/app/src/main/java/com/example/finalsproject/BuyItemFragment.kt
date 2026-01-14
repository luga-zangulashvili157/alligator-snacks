package com.example.finalsproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalsproject.databinding.FragmentBuyitemBinding
import java.util.Locale

class BuyItemFragment : Fragment(R.layout.fragment_buyitem) {

    private var quantity = 1
    private var unitPrice = 0.0

    // Binding reference
    private var _binding: FragmentBuyitemBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBuyitemBinding.bind(view)

        // get arguments from PartsListFragment
        val name = arguments?.getString("name") ?: ""
        val description = arguments?.getString("description") ?: ""
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        unitPrice = arguments?.getDouble("price") ?: 0.0

        // bind data to views
        binding.ItemMainTitle.text = name
        binding.ItemDetailedDescription.text = description
        binding.SpecitemPrice.text = String.format(Locale.US, "$%,.2f", unitPrice)

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(binding.ItemMainPicture)

        // back arrow
        binding.ItemBackarrowIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        } // go back to partsList

        // quantity selector logic
        fun updateUI() {
            binding.quantityText.text = quantity.toString()
            val totalPrice = unitPrice * quantity
            binding.SpecitemPrice.text = String.format(Locale.US, "$%,.2f", totalPrice)
        } //update quantity number (the 1 or 2 or etc) and update price (original*quantity), bind it with $ sign

        binding.buttonMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateUI()
            }
        }

        binding.buttonPlus.setOnClickListener {
            quantity++
            updateUI()
        }

        binding.buyButtonText.setOnClickListener {
            val fragment = OrderConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putInt("quantity", quantity) // pass chosen quantity
                    putDouble("totalPrice", unitPrice * quantity) // pass calculated price
                    putString("name", name) // pass product name
                    putString("imageUrl", imageUrl) // pass product image
                }
            }

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        } // fade to the next fragment with arguments
    }

    override fun onResume() {
        super.onResume()
        quantity = 1
        binding.quantityText.text = quantity.toString()
        binding.SpecitemPrice.text = String.format(Locale.US, "$%,.2f", unitPrice)
    } // once we return back to here (from orderConfirmation) we reset the quantity to 1 and price to original

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
