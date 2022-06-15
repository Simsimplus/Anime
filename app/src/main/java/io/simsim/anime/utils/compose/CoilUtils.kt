package io.simsim.anime.utils.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String?,
    imageSize: DpSize,
    showPlaceholderAlways: Boolean = false
) {
    var imageLoading by remember {
        mutableStateOf(true)
    }
    AsyncImage(
        modifier = modifier
            .size(imageSize)
            .clip(MaterialTheme.shapes.small)
            .placeholder(
                visible = showPlaceholderAlways || imageLoading,
            ),
        model = model,
        contentDescription = contentDescription,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        onState = {
            imageLoading = it is AsyncImagePainter.State.Loading
        }
    )

}