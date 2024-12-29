package com.example.ayurvedaassignment.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ayurvedaassignment.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productName: String,
    productIndex: Int,
    navController: NavController,
    cartState: SnapshotStateMap<Int, Int>
) {
    val totalQuantity = cartState.values.sum()

    val quantity = cartState[productIndex] ?: 0
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(productName) },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("cart") },
                        modifier = Modifier.size(70.dp)
                    ) {
                        BadgedBox(
                            badge = {
                                if (totalQuantity > 0) {
                                    Badge { Text(totalQuantity.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = productName,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This is a detailed description of $productName. It has amazing features and specifications.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (quantity > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (quantity > 1) {
                            cartState[productIndex] = quantity - 1
                        } else {
                            cartState.remove(productIndex)
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Decrease Quantity")
                    }
                    Text(quantity.toString(), Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = {
                        cartState[productIndex] = quantity + 1
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                    }
                }
            } else {
                Button(
                    onClick = {
                        cartState[productIndex] = 1
                    }
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}





