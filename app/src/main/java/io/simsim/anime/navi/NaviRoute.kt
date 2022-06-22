package io.simsim.anime.navi

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NaviRoute(val route: String) {
    fun getRouteWithArgs(vararg argPair: Pair<String, Any>) = argPair.fold(route) { acc, pair ->
        acc.replace("{${pair.first}}", pair.second.toString())
    }

    object Main : NaviRoute("main")
    object Detail : NaviRoute("detail/{mail_id}") {
        const val argNameMalId = "mail_id"
        val navArgMalId = listOf(navArgument("mail_id") { type = NavType.IntType })
        fun getDetailRoute(mailId: Int) = getRouteWithArgs(argNameMalId to mailId)
    }

    object Search : NaviRoute("search")
    object Images : NaviRoute("images/{mail_id}") {
        const val argNameMalId = "mail_id"
        val navArgMalId = listOf(navArgument("mail_id") { type = NavType.IntType })
        fun getDetailRoute(mailId: Int) = getRouteWithArgs(argNameMalId to mailId)
    }
}
