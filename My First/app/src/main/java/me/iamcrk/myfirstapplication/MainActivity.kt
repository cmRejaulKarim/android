package me.iamcrk.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.iamcrk.myfirstapplication.ui.theme.MyFirstApplicationTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstApplicationTheme {
        Greeting("Android")
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MyFirstApplicationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    AnimatedGreetingScreen(modifier = Modifier.padding(innerPadding))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun AnimatedGreetingScreen(modifier: Modifier = Modifier) {
//    // Control the animation visibility
//    var visible by remember { mutableStateOf(false) }
//
//    // Trigger the animation when this composable enters composition
//    LaunchedEffect(Unit) {
//        visible = true
//    }
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF6A11CB),
//                        Color(0xFF2575FC)
//                    )
//                )
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        AnimatedVisibility(
//            visible = visible,
//            enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
//                    slideInVertically(
//                        initialOffsetY = { it / 2 },
//                        animationSpec = tween(durationMillis = 1000)
//                    )
//        ) {
//            Card(
//                modifier = Modifier.padding(16.dp),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = Color.White.copy(alpha = 0.2f)
//                ),
//                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier.padding(24.dp)
//                ) {
//                    Greeting(name = "Reja")
//                    Spacer(modifier = Modifier.height(8.dp))
//                    GreetingMessage(name = "Reja")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hey $name! 👋",
//        style = MaterialTheme.typography.headlineSmall,
//        color = Color.White,
//        modifier = modifier
//    )
//}
//
//@Composable
//fun GreetingMessage(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Glad to see you here! Let’s explore something amazing today 🚀",
//        style = MaterialTheme.typography.headlineMedium,
//        color = Color.White,
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyFirstApplicationTheme {
//        AnimatedGreetingScreen()
//    }
}