package io.simsim.anime.ui.screen.detail

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.palette.graphics.Target
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.data.entity.AnimeStatisticsResponse
import io.simsim.anime.data.entity.getScoreMap
import io.simsim.anime.ui.theme.ScoreColor
import io.simsim.anime.ui.widget.CenterAlignRow
import io.simsim.anime.ui.widget.ScoreStars
import io.simsim.anime.ui.widget.YTBVideo
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.getImagePalette
import io.simsim.anime.utils.compose.placeholder

@Composable
fun AnimeDetailScreen(
    nvc: NavHostController,
    vm: AnimeDetailVM = hiltViewModel(),
    malId: Int,
) {
    LaunchedEffect(malId) {
        vm.getAnimeDetailFull(malId)
        vm.getAnimeStatistic(malId)
    }
    val ctx = LocalContext.current
    val animeFullData by vm.animeDetailFull
        .collectAsState(initial = AnimeFullResponse.AnimeFullData())
    val animeStatistics by vm.animeStatistics.collectAsState(initial = AnimeStatisticsResponse.AnimeStatisticsData())
    val loading = animeFullData.malId == 0
    val imageUrl = animeFullData.images.webp.imageUrl
    val imagePalette = getImagePalette(imageKey = imageUrl)
    val primaryColor = MaterialTheme.colorScheme.primary
    val bgColor = if (loading) {
        MaterialTheme.colorScheme.surface
    } else {
        imagePalette?.getSwatchForTarget(Target.DARK_MUTED)?.let {
            Color(it.rgb)
        } ?: (primaryColor)
    }
    DisposableEffect(key1 = bgColor) {
        (ctx as? Activity)?.let {
            it.window.statusBarColor = bgColor.toArgb()
        }
        onDispose {
            (ctx as? Activity)?.let {
                it.window.statusBarColor = primaryColor.toArgb()
            }
        }
    }
    LaunchedEffect(bgColor) {
        (ctx as? Activity)?.let {
            it.window.statusBarColor = bgColor.toArgb()
        }
    }
    BackHandler {
        nvc.navigateUp()
        (ctx as? Activity)?.let {
            it.window.statusBarColor = primaryColor.toArgb()
        }
    }
    BoxWithConstraints {
        val imageWidth = maxWidth / 3
        val imageHeight = imageWidth.times(1.618f)
        val imageSize = DpSize(imageWidth, imageHeight)
        val videoSize = DpSize(maxWidth, maxWidth.times(maxWidth.div(maxHeight)))
        CompositionLocalProvider(LocalContentColor.provides(Color.White)) {
            Surface(
                color = bgColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CoilImage(
                            model = imageUrl,
                            contentDescription = "image",
                            imageSize = imageSize,
                            showPlaceholderAlways = loading
                        )
                        AnimeIntro(anime = animeFullData, loading = loading)
                    }
                    ScoreBar(
                        modifier = Modifier.placeholder(animeStatistics.total == 0 || loading),
                        cardBgColor = Color(
                            ColorUtils.blendARGB(
                                bgColor.toArgb(),
                                Color.Black.toArgb(),
                                0.2f
                            )
                        ),
                        anime = animeFullData,
                        statistics = animeStatistics
                    )
                    Synopsis(
                        modifier = Modifier
//                            .weight(1f)
                            .fillMaxWidth()
                            .placeholder(loading),
                        synopsis = animeFullData.synopsis
                    )
                    YTBVideo(
                        modifier = Modifier
                            .size(videoSize)
                            .placeholder(loading),
                        ytbId = animeFullData.trailer.youtubeId
                    )
                }
            }
        }
    }
}

@Composable
fun AnimeIntro(
    anime: AnimeFullResponse.AnimeFullData,
    loading: Boolean
) {
    val genres = anime.genres.joinToString("/") {
        it.name
    }
    val label =
        "${anime.status}/" +
                (if (anime.airing) "${anime.broadcast.string}/" else "") +
                "$genres/" +
                "${anime.aired.from.substringBefore("T")}/" +
                "${anime.episodes} episodes"
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            modifier = Modifier
                .placeholder(loading)
                .fillMaxWidth(),
            text = anime.title.takeIf { !loading } ?: "Fullmetal Alchemist: Brotherhood",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier
                .placeholder(loading)
                .fillMaxWidth(),
            text = "${anime.titleJapanese}(${anime.year})".takeIf { !loading }
                ?: "鋼の錬金術師 FULLMETAL ALCHEMIST(2009)",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            modifier = Modifier
                .placeholder(loading)
                .fillMaxWidth(),
            text = label.takeIf { !loading }
                ?: "Finished Airing/Action/Adventure/Drama/Fantasy/2009-04-05/64 episodes",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBar(
    modifier: Modifier = Modifier,
    cardBgColor: Color,
    anime: AnimeFullResponse.AnimeFullData,
    statistics: AnimeStatisticsResponse.AnimeStatisticsData
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CenterAlignRow(
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Column {
                    Text(
                        text = anime.score.toString(),
                        style = MaterialTheme.typography.displayMedium
                    )
                    ScoreStars(
                        score = anime.score.toFloat(),
                        starSize = 15.dp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                BoxWithConstraints {
                    val scoreBarWidth = maxWidth / 2
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        val scoreStatistic = statistics.getScoreMap()
                        (5 downTo 1).forEach { score ->
                            val scorePercentage = scoreStatistic[score] ?: 0.0f
                            CenterAlignRow {
                                repeat(score) {
                                    Icon(
                                        modifier = Modifier.size(12.dp),
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = "stars",
                                        tint = ScoreColor
                                    )
                                }
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .width(scoreBarWidth)
                                        .height(8.dp)
                                        .clip(MaterialTheme.shapes.extraSmall),
                                    progress = scorePercentage.div(100f),
                                    color = ScoreColor,
                                    trackColor = Color.LightGray
                                )
                            }
                        }
                        Text(
                            text = "by ${anime.scoredBy}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Divider(
                color = Color.LightGray
            )
            CenterAlignRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalTextStyle.provides(MaterialTheme.typography.labelSmall)) {
                    Text(text = "watched:${statistics.completed + statistics.watching}")
                    Text(text = "planned:${statistics.planToWatch + statistics.onHold}")
                }
            }
        }
    }
}

@Composable
fun Synopsis(modifier: Modifier = Modifier, synopsis: String) {
    Column(modifier = modifier) {
        Text(text = "Synopsis", style = MaterialTheme.typography.titleSmall)
        Text(
            text = synopsis,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
