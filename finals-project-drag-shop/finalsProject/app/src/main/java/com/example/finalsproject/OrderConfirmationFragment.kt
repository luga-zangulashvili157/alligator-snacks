package com.example.finalsproject

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalsproject.databinding.FragmentConfirmorderBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class OrderConfirmationFragment : Fragment(R.layout.fragment_confirmorder) {

    private var _binding: FragmentConfirmorderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfirmorderBinding.bind(view)

        // setup focus listeners to scroll the specific EditText into view
        val focusListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.postDelayed({
                    val rect = Rect()
                    v.getDrawingRect(rect)
                    v.requestRectangleOnScreen(rect)
                }, 300) // delay to allow keyboard to start showing
            } // basically a code chunk that lets us see the edit-texts once the keyboard pops up. without this, we wouldnt be able to see te edit-texts with keyboard popped up
        }


        binding.shippingInfoInput.onFocusChangeListener = focusListener
        binding.paymentInfoInput.onFocusChangeListener = focusListener
        binding.clientInfoInput.onFocusChangeListener = focusListener // do that on each edit-text

        // arguments passed from BuyItemFragment
        val quantity = arguments?.getInt("quantity") ?: 1
        val totalPrice = arguments?.getDouble("totalPrice") ?: 0.0
        val name = arguments?.getString("name") ?: ""
        val imageUrl = arguments?.getString("imageUrl") ?: ""

        // bind product info (from BuyItemFragment) into the page
        binding.chosenQuantity.text = quantity.toString()
        binding.GivenPrice.text = String.format(Locale.US, "$%,.2f", totalPrice)
        binding.ItemMainTitle.text = name

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder) // bind the image
            .into(binding.ItemMainPicture)

        // firebase reference for the user's data (the edit-texts. they are editable, and whatever you edit, it will be saved)
        val database = FirebaseDatabase.getInstance().getReference("users").child("user1")

        // load existing data (from database) into EditTexts
        database.get().addOnSuccessListener { snapshot ->
            if (isAdded) {
                binding.shippingInfoInput.setText(snapshot.child("shipping").getValue(String::class.java) ?: "")
                binding.paymentInfoInput.setText(snapshot.child("payment").getValue(String::class.java) ?: "")
                binding.clientInfoInput.setText(snapshot.child("client").getValue(String::class.java) ?: "")
            }
        }

        // helper to save current edits
        fun saveData() {
            val updatedData = mapOf(
                "shipping" to binding.shippingInfoInput.text.toString(),
                "payment" to binding.paymentInfoInput.text.toString(),
                "client" to binding.clientInfoInput.text.toString()
            )
            database.updateChildren(updatedData)
        }

        // back arrow. save whats written in edit-text then pop
        binding.ConfirmationBackarrowIcon.setOnClickListener {
            saveData()
            parentFragmentManager.popBackStack()
        }

        // buy button. save then navigate forward
        binding.buyButtonText.setOnClickListener {
            saveData()
            val fragment = SuccessFragment()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out  //animation to successFragment
                )
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
