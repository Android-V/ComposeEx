package com.beombeom.composeex.presentation.examples.videoPlayer

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.beombeom.composeex.presentation.MainHeader

class VideoPlayerViewModel : ViewModel() {
    var currentUri: Uri? = null
    var currentPosition: Long = 0L
    var playWhenReady: Boolean = true
}

@Composable
fun VideoPlayerEx(title : String) {
    val viewModel = viewModel<VideoPlayerViewModel>()
    var selectedVideoUri by rememberSaveable { mutableStateOf(viewModel.currentUri) }

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedVideoUri = uri
        viewModel.currentUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MainHeader(title = title)

        Button(
            onClick = { pickVideoLauncher.launch("video/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("영상 가져오기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedVideoUri != null) {
            VideoPlayerComponent(videoUri = selectedVideoUri!!, viewModel)
        } else {
            Text("동영상을 선택해주세요.", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun VideoPlayerComponent(videoUri: Uri, viewModel: VideoPlayerViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val videoHeight = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        250.dp
    } else {
        200.dp
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            prepare()
            seekTo(viewModel.currentPosition)
            playWhenReady = viewModel.playWhenReady
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(horizontal = 10.dp)
            .height(videoHeight)
    )

    DisposableEffect(videoUri) {
        onDispose {
            viewModel.currentPosition = exoPlayer.currentPosition
            viewModel.playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
    }
}