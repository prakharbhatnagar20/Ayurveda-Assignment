package com.example.ayurvedaassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ayurvedaassignment.screens.CartScreen
import com.example.ayurvedaassignment.screens.ProductDetailScreen
import com.example.ayurvedaassignment.screens.ProductScreen
import com.example.ayurvedaassignment.ui.theme.AyurvedaAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AyurvedaAssignmentTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val cartState = remember { mutableStateMapOf<Int, Int>() }
    val products = listOf("Product A", "Product B", "Product C", "Product D")

    NavHost(navController = navController, startDestination = "products") {
        composable("products") {
            ProductScreen(
                navController=navController, cartState, products
            )
        }
        composable("cart") {
            CartScreen(cartState, products)
        }
        composable(
            "productDetail/{productName}/{productIndex}",
            arguments = listOf(
                navArgument("productName") { type = NavType.StringType },
                navArgument("productIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName") ?: "Unknown"
            val productIndex = backStackEntry.arguments?.getInt("productIndex") ?: -1
            ProductDetailScreen(
                productName = productName,
                productIndex = productIndex,
                navController = navController,
                cartState = cartState
            )
        }
    }
}




