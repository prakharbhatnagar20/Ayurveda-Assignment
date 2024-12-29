package com.example.ayurvedaassignment.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ayurvedaassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, cartState:SnapshotStateMap<Int,Int>, products: List<String>) {

    val totalQuantity = cartState.values.sum()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },

                actions = {
                    IconButton(onClick = { navController.navigate("cart") },
                        modifier = Modifier.size(70.dp)) {
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
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            itemsIndexed(products) { index, product ->
                ProductItem(
                    productName = product,
                    productIndex = index,
                    quantity = cartState[index] ?: 0,
                    onAddToCart = { cartState[index] = 1 },
                    onIncreaseQuantity = { cartState[index] = (cartState[index] ?: 0) + 1 },
                    onDecreaseQuantity = {
                        cartState[index]?.let {
                            if (it > 1) cartState[index] = it - 1
                            else cartState.remove(index)
                        }
                    },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    productName: String,
    productIndex: Int,
    quantity: Int,
    onAddToCart: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("productDetail/$productName/$productIndex")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(84.dp)
                .clip(RoundedCornerShape(8.dp))

        )
        Spacer(modifier = Modifier.width(30.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = productName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))


        }
        if (quantity > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(Icons.Default.Delete, contentDescription = "Decrease Quantity")
                }
                Text(quantity.toString(), Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                }
            }
        } else {
            Button(onClick = onAddToCart) {
                Text("Add to Cart")
            }
        }
    }
}
