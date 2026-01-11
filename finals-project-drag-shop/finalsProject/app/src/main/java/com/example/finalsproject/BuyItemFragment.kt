package com.example.finalsproject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.util.Locale

class BuyItemFragment : Fragment(R.layout.fragment_buyitem) {

    private var quantity = 1
    private var unitPrice = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.ItemMainPicture)
        val titleView = view.findViewById<TextView>(R.id.ItemMainTitle)
        val descriptionView = view.findViewById<TextView>(R.id.ItemDetailedDescription)
        val priceView = view.findViewById<TextView>(R.id.SpecitemPrice)
        val quantityText = view.findViewById<TextView>(R.id.quantityText)
        val buttonMinus = view.findViewById<ImageView>(R.id.buttonMinus)
        val buttonPlus = view.findViewById<ImageView>(R.id.buttonPlus)

        // get arguments from PartsListFragment
        val name = arguments?.getString("name") ?: ""
        val description = arguments?.getString("description") ?: ""
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        unitPrice = arguments?.getDouble("price") ?: 0.0


        titleView.text = name
        descriptionView.text = description // bind data
        priceView.text = String.format(Locale.US, "$%,.2f", unitPrice)

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder) // load the image with glide
            .into(imageView)

        // Back arrow
        view.findViewById<ImageView>(R.id.ItemBackarrowIcon).setOnClickListener {
            parentFragmentManager.popBackStack()
        } // go back to partsList

        // quantity selector logic
        fun updateUI() {
            quantityText.text = quantity.toString()
            val totalPrice = unitPrice * quantity
            priceView.text = String.format(Locale.US, "$%,.2f", totalPrice)
        } //update quantity number (the 1 or 2 or etc) and update price (original*quantity), bind it with $ sign

        buttonMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateUI()
            }
        }

        buttonPlus.setOnClickListener {
            quantity++
            updateUI()
        }



        val buyButton = view.findViewById<TextView>(R.id.buyButtonText)

        buyButton.setOnClickListener {
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
        view?.findViewById<TextView>(R.id.quantityText)?.text = quantity.toString()
        view?.findViewById<TextView>(R.id.SpecitemPrice)?.text =
            String.format(Locale.US, "$%,.2f", unitPrice)
    } // once we return back to here (from orderConfirmation) we reset the quantity to 1 and price to original

}
