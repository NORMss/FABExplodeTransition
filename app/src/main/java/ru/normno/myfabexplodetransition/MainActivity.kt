package ru.normno.myfabexplodetransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ru.normno.myfabexplodetransition.ui.theme.MyFABExplodeTransitionTheme

@Serializable
data object MainRoute

@Serializable
data object AddItemRoute

const val FAB_EXPLODE_BOUNDS_KEY = "fab_explode_bounds_key"

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFABExplodeTransitionTheme {
                val navController = rememberNavController()
                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = MainRoute,
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        composable<MainRoute> {
                            MainScreen(
                                fabColor = MaterialTheme.colorScheme.primaryContainer,
                                animatedVisibilityScope = this,
                                onFabClick = {
                                    navController.navigate(AddItemRoute)
                                },
                            )
                        }
                        composable<AddItemRoute> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = FAB_EXPLODE_BOUNDS_KEY
                                        ),
                                        animatedVisibilityScope = this,
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Add Item Screen",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}