package com.yeshuwahane.d4c.presenatation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeshuwahane.d4c.presenatation.auth.LoginScreen
import com.yeshuwahane.d4c.presenatation.shops.ShopScreen
import com.yeshuwahane.d4c.presenatation.tickets.TicketScreen
import com.yeshuwahane.d4c.ui.theme.D4cTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {



            D4cTheme {
               MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()

    // Token check state
    val jwtState by viewModel.jwtFlow.collectAsState(initial = null)
    var tokenChecked by remember { mutableStateOf(false) }

    // Perform navigation only once when token is checked
    LaunchedEffect(jwtState) {
        if (jwtState != null) {
            tokenChecked = true
            if (jwtState!!.isEmpty()) {
                navController.navigate("login") {
                    popUpTo(0)
                }
            } else {
                navController.navigate("products") {
                    popUpTo(0)
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "B4C",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        composable("login") {
            LoginScreen {
                navController.navigate("products") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("products") {
            ShopScreen(){
                navController.navigate("ticket")
            }
        }

        composable("ticket") {
            TicketScreen(){
                navController.popBackStack()
            }
        }

    }
}



