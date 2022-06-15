package io.simsim.anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.ui.screen.AnimeDetailScreen
import io.simsim.anime.ui.screen.main.MainScreen
import io.simsim.anime.ui.theme.AnimeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeTheme {
                MainHost()
            }
        }
    }
}

@Composable
fun MainHost() {
    val nvc = rememberNavController()
    NavHost(navController = nvc, startDestination = NaviRoute.Main.route) {
        composable(route = NaviRoute.Main.route) {
            MainScreen(
                nvc = nvc
            )
        }
        composable(
            route = NaviRoute.Detail.route,
            arguments = listOf(NaviRoute.Detail.navArgMalId)
        ) { backStackEntry ->
            AnimeDetailScreen(mailId = backStackEntry.arguments?.getInt(NaviRoute.Detail.argNameMalId)!!)
        }
    }
}
