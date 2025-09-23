package com.example.loadinganimations.glassmorphism

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.io.path.Path

@Composable
@Preview(showBackground = true)
fun Sample1(){
//    GlassWithBlobsDemo()
    OrganicBlobsWithGlass()
}
//@Composable
//fun GlassWithBlobsDemo() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black) // try Color.White too
//    ) {
//        // Animated blobs background
//        BlobsBackground()
//
//        // Glassmorphic Card in the center
//        GlassCard(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .padding(32.dp)
//        )
//    }
//}
//
//@Composable
//fun BlobsBackground() {
//    val infiniteTransition = rememberInfiniteTransition()
//
//    // Two animated positions for blobs
//    val offsetX1 by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 600f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(6000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//    val offsetY1 by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1800f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(8000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawCircle(
//            color = Color(0xFF00FFAA).copy(alpha = 0.3f),
//            radius = 200f,
//            center = Offset(offsetX1, offsetY1)
//        )
//        drawCircle(
//            color = Color(0xFFAA00FF).copy(alpha = 0.3f),
//            radius = 250f,
//            center = Offset(size.width - offsetX1, offsetY1 / 2)
//        )
//    }
//}
//
//@Composable
//fun GlassCard(modifier: Modifier = Modifier) {
//    Box(
//        modifier = modifier
//            .clip(RoundedCornerShape(24.dp))
//            .background(
//                Brush.verticalGradient(
//                    listOf(
//                        Color.White.copy(alpha = 0.2f),
//                        Color.White.copy(alpha = 0.05f)
//                    )
//                )
//            )
//            .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
//            .padding(24.dp)
//    ) {
//        Text(
//            text = "Glassmorphism ✨",
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}

@Composable
fun OrganicBlobsWithGlass() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // or white, but colorful blobs look better on dark
    ) {
        // Wobbly animated blob
        AnimatedBlob(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF00FFAA).copy(alpha = 0.35f),
            durationMillis = 6000,
            maxRadius = 250f
        )
        AnimatedBlob(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFAA00FF).copy(alpha = 0.35f),
            durationMillis = 8000,
            maxRadius = 300f
        )

        // Glassmorphic Card on top
        GlassCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        )
    }
}

@Composable
fun AnimatedBlob(
    modifier: Modifier,
    color: Color,
    durationMillis: Int,
    maxRadius: Float
) {
    val infiniteTransition = rememberInfiniteTransition()

    // Animate radius fluctuation
    val animatedRadius by infiniteTransition.animateFloat(
        initialValue = maxRadius * 0.8f,
        targetValue = maxRadius,
        animationSpec = infiniteRepeatable(
            tween(durationMillis, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    // Animate movement
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis * 2, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        val path = Path().apply {
            val cx = offsetX % size.width
            val cy = offsetY % size.height

            // Create a wobbly "blob" shape using Bezier curves
            moveTo(cx, cy - animatedRadius)
            cubicTo(
                cx + animatedRadius, cy - animatedRadius, // top-right curve control
                cx + animatedRadius, cy + animatedRadius, // bottom-right curve control
                cx, cy + animatedRadius
            )
            cubicTo(
                cx - animatedRadius, cy + animatedRadius,
                cx - animatedRadius, cy - animatedRadius,
                cx, cy - animatedRadius
            )
            close()
        }

        drawPath(path = path, color = color, style = Fill)
    }
}

@Composable
fun GlassCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.05f)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Text(
            text = "Glass + Blobs ✨",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

