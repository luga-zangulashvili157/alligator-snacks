package com.example.third_task_firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductAdapter // mokled rom vtqvat, inicializacia cvladebis
    private lateinit var dbRef: DatabaseReference
    private var valueListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recyclerview da adapteri
        rv = findViewById(R.id.rvProducts)
        adapter = ProductAdapter(mutableListOf()) { product -> val msg = "ID: ${product.id}\nname: ${product.name}\nprice: ${product.price}\nin stock: ${product.inStock}"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } // product adapteri, romelic actiurad achvenebs detalebs tostit.
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // amit vakavshirebt chvens firebase databse products -ebtan
        dbRef = FirebaseDatabase.getInstance("https://test-project-7991d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("products")

        // rame rom sheicvleba databazashi, egreve gamoachens cvlilebas, appi roca chartuli gvaq.
        attachListener()
    }

    private fun attachListener() {
        valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val list = mutableListOf<Product>()

                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)

                    // viyenebt mocemul IDs key-d an realtime database keys.
                    if (product != null && product.id.isNullOrEmpty()) {
                        product.id = child.key
                    }
                    product?.let { list.add(it) }
                }
                adapter.replaceAll(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "DB error: ${error.message}", Toast.LENGTH_SHORT).show()
            } // tu databazidan shecdomitaa rame, mashin shecdomas gamoitans
        }
        dbRef.addValueEventListener(valueListener!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        valueListener?.let { dbRef.removeEventListener(it) } // database listeners shlis roca eventi morcheba.
    }
}
