package io.simsim.anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.ui.screen.detail.AnimeDetailScreen
import io.simsim.anime.ui.screen.main.MainScreen
import io.simsim.anime.ui.screen.search.SearchScreen
import io.simsim.anime.ui.theme.AnimeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeTheme {
                Surface {
                    MainHost()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainHost() {
    val nvc = rememberAnimatedNavController()
    AnimatedNavHost(navController = nvc, startDestination = NaviRoute.Main.route) {
        composable(route = NaviRoute.Main.route) {
            MainScreen(
                nvc = nvc
            )
        }
        composable(
            route = NaviRoute.Detail.route,
            arguments = listOf(NaviRoute.Detail.navArgMalId)
        ) { backStackEntry ->
            AnimeDetailScreen(malId = backStackEntry.arguments?.getInt(NaviRoute.Detail.argNameMalId)!!)
        }
        composable(
            route = NaviRoute.Search.route,
            enterTransition = {
                fadeIn()
            },
            exitTransition = {
                fadeOut()
            }
        ) {
            SearchScreen(nvc = nvc)
        }
    }
}
