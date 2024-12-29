package com.example.ayurvedaassignment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartState: MutableMap<Int, Int>, products: List<String>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            if (cartState.isEmpty()) {
                item {
                    Text(
                        text = "Your cart is empty!",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(cartState.keys.toList()) { index ->
                    val productName = products[index]
                    val quantity = cartState[index] ?: 0

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(productName)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                if (quantity > 1) cartState[index] = quantity - 1
                                else cartState.remove(index)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Decrease Quantity")
                            }
                            Text(quantity.toString(), Modifier.padding(horizontal = 8.dp))
                            IconButton(onClick = {
                                cartState[index] = quantity + 1
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                            }
                        }
                    }
                }
            }
        }
    }
}