package com.example.finalsproject

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class OrderConfirmationFragment : Fragment(R.layout.fragment_confirmorder) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrollView = view.findViewById<ScrollView>(R.id.confirmationScrollView)

        // product info views. this is what the page recieves from BuyItemFragment
        val quantityView = view.findViewById<TextView>(R.id.chosenQuantity)
        val priceView = view.findViewById<TextView>(R.id.GivenPrice)
        val titleView = view.findViewById<TextView>(R.id.ItemMainTitle)
        val imageView = view.findViewById<ImageView>(R.id.ItemMainPicture)

        // editable info views (the edit-texts)
        val shippingInput = view.findViewById<EditText>(R.id.shippingInfoInput)
        val paymentInput = view.findViewById<EditText>(R.id.paymentInfoInput)
        val clientInput = view.findViewById<EditText>(R.id.clientInfoInput)

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
        shippingInput.onFocusChangeListener = focusListener
        paymentInput.onFocusChangeListener = focusListener
        clientInput.onFocusChangeListener = focusListener // do that on each edit-text

        // arguments passed from BuyItemFragment
        val quantity = arguments?.getInt("quantity") ?: 1
        val totalPrice = arguments?.getDouble("totalPrice") ?: 0.0
        val name = arguments?.getString("name") ?: ""
        val imageUrl = arguments?.getString("imageUrl") ?: ""

        // bind product info (from BuyItemFragment) into the page
        quantityView.text = quantity.toString()
        priceView.text = String.format(Locale.US, "$%,.2f", totalPrice)
        titleView.text = name

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder) // bind the image
            .into(imageView)

        // firebase reference for the user's data (the edit-texts. they are editable, and whatever you edit, it will be saved)
        val database = FirebaseDatabase.getInstance().getReference("users").child("user1")

        // load existing data (from database) into EditTexts
        database.get().addOnSuccessListener { snapshot ->
            if (isAdded) {
                shippingInput.setText(snapshot.child("shipping").getValue(String::class.java) ?: "")
                paymentInput.setText(snapshot.child("payment").getValue(String::class.java) ?: "")
                clientInput.setText(snapshot.child("client").getValue(String::class.java) ?: "")
            }
        }

        // helper to save current edits
        fun saveData() {
            val updatedData = mapOf(
                "shipping" to shippingInput.text.toString(),
                "payment" to paymentInput.text.toString(),
                "client" to clientInput.text.toString()
            )
            database.updateChildren(updatedData)
        }

        // back arrow. save whats written in edit-text then pop
        view.findViewById<View>(R.id.ConfirmationBackarrowIcon).setOnClickListener {
            saveData()
            parentFragmentManager.popBackStack()
        }

        // buy button. save then navigate forward
        view.findViewById<View>(R.id.buyButtonText).setOnClickListener {
            saveData()

            val fragment = SuccessFragment()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out //animation to successFragment
                )
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
