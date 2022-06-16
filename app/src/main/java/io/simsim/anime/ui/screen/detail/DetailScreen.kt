package io.simsim.anime.ui.screen.detail

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Target
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.ui.widget.CenterAlignRow
import io.simsim.anime.ui.widget.ScoreStars
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.getImagePalette
import io.simsim.anime.utils.compose.placeholder

@Composable
fun AnimeDetailScreen(
    vm: AnimeDetailVM = hiltViewModel(),
    mailId: Int
) {
    val ctx = LocalContext.current
    val animeFullData by vm.getAnimeDetailFull(mailId)
        .collectAsState(initial = AnimeFullResponse.AnimeFullData())
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
    LaunchedEffect(bgColor) {
        (ctx as? Activity)?.let {
            it.window.statusBarColor = bgColor.toArgb()
        }
    }
    BoxWithConstraints {
        val imageWidth = maxWidth / 3
        val imageHeight = imageWidth.times(1.618f)
        val imageSize = DpSize(imageWidth, imageHeight)
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
                        modifier = Modifier.placeholder(loading),
                        cardBgColor = Color(
                            ColorUtils.blendARGB(
                                bgColor.toArgb(),
                                Color.Black.toArgb(),
                                0.2f
                            )
                        ),
                        anime = animeFullData
                    )
                    Synopsis(
                        modifier = Modifier
//                            .weight(1f)
                            .fillMaxSize()
                            .placeholder(loading),
                        synopsis = animeFullData.synopsis
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
        "${anime.status}/$genres/${anime.aired.from.substringBefore("T")}/${anime.episodes} episodes"
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
    anime: AnimeFullResponse.AnimeFullData
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor
        )
    ) {
        CenterAlignRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = anime.score.toString(), style = MaterialTheme.typography.displayMedium)
            Column {
                ScoreStars(
                    score = anime.score.toFloat(),
                    starSize = 20.dp
                )
                Text(
                    text = "by ${anime.scoredBy}",
                    style = MaterialTheme.typography.labelSmall
                )
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