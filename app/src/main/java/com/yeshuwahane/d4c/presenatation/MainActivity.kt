package com.yeshuwahane.d4c.presenatation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeshuwahane.d4c.presenatation.features.auth.LoginScreen
import com.yeshuwahane.d4c.presenatation.features.shops.ShopScreen
import com.yeshuwahane.d4c.presenatation.features.tickets.TicketScreen
import com.yeshuwahane.d4c.presenatation.ui.theme.D4cTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


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
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen { isLoggedIn ->
                println("🧭 MainScreen - Navigation decision: isLoggedIn = $isLoggedIn")
                navController.navigate(if (isLoggedIn) "products" else "login") {
                    popUpTo("splash") { inclusive = true }
                }
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
            ShopScreen {
                navController.navigate("ticket")
            }
        }

        composable("ticket") {
            TicketScreen {
                navController.popBackStack()
            }
        }
    }
}



@Composable
fun SplashScreen(
    onNavigate: (isLoggedIn: Boolean) -> Unit
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val jwtState by viewModel.jwtFlow.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }

    // Animate alpha for subtle pulsing
    val alphaAnim = rememberInfiniteTransition().animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Respond to JWT change
    LaunchedEffect(jwtState) {
        println("🚀 SplashScreen - Received jwtState = '$jwtState'")
        if (!hasNavigated && jwtState != null) {
            hasNavigated = true
            val isLoggedIn = jwtState!!.isNotBlank()
            println("✅ SplashScreen - isLoggedIn: $isLoggedIn → Navigating")
            onNavigate(isLoggedIn)
        } else if (!hasNavigated) {
            println("🕒 SplashScreen - jwtState is still null, waiting...")
        }
    }

    // Fallback after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        if (!hasNavigated) {
            println("⏳ SplashScreen - Timeout fallback, navigating with jwtState='$jwtState'")
            hasNavigated = true
            val isLoggedIn = jwtState?.isNotBlank() == true
            onNavigate(isLoggedIn)
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alphaAnim.value),
                color = Color.Black
            )

            Text(
                text = "B4C",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
