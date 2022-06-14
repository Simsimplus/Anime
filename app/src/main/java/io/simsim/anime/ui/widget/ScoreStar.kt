package io.simsim.anime.ui.widget

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoreStar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 10.0) score: Float
) {
    val totalStar = 5
    val fullStar = score.div(2f).toInt()
    val hasHalfStar = score.mod(2f) > 0.3f
    CompositionLocalProvider(LocalContentColor.provides(io.simsim.anime.ui.theme.ScoreColor)) {
        Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
            repeat(fullStar) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "full"
                )
            }
            if (hasHalfStar) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Rounded.StarHalf,
                    contentDescription = "half"
                )
            }
            repeat(totalStar - fullStar - (if (hasHalfStar) 1 else 0)) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Rounded.StarBorder,
                    contentDescription = "half"
                )
            }
            Text(text = score.toString(), style = MaterialTheme.typography.labelSmall)
        }
    }
}
