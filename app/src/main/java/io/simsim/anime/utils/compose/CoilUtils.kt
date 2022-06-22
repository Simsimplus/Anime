package io.simsim.anime.utils.compose

import android.graphics.Bitmap
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.memory.MemoryCache
import coil.request.ImageRequest

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String?,
    showPlaceholderAlways: Boolean = false
) {
    val ctx = LocalContext.current
    var imageLoading by remember {
        mutableStateOf(true)
    }
    val imageRequest = ImageRequest.Builder(ctx).data(model).allowHardware(true).build()
    val imageModifier = modifier
        .clip(MaterialTheme.shapes.small)
        .placeholder(
            visible = showPlaceholderAlways || imageLoading,
        )
    AsyncImage(
        modifier = imageModifier,
        model = imageRequest,
        contentDescription = contentDescription,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        onState = {
            imageLoading = it is AsyncImagePainter.State.Loading
        },
    )
}

@Composable
fun getImagePalette(imageKey: String): Palette? {
    val ctx = LocalContext.current
    return Coil.imageLoader(ctx).memoryCache?.get(MemoryCache.Key(imageKey))?.bitmap?.let {
        Palette.from(it.copy(Bitmap.Config.ARGB_8888, false)).generate()
    }
}
