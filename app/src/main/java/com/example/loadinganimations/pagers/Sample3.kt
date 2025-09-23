package com.example.loadinganimations.pagers

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent
import kotlin.math.absoluteValue

@Composable
@Preview(showBackground = true)
fun Sample3(){
    val awesomeColors = listOf(
        Color(0xFF4169E1), // Royal Blue
        Color(0xFFDC143C), // Crimson Red
        Color(0xFF50C878), // Emerald Green
        Color(0xFFFFD700), // Golden Yellow
        Color(0xFF673AB7), // Deep Purple
        Color(0xFF008080), // Teal
        Color(0xFFFF7F50), // Coral
        Color(0xFF40E0D0), // Turquoise
        Color(0xFFFF69B4), // Hot Pink
        Color(0xFF36454F)  // Charcoal Gray
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val pagerState = rememberPagerState{ 10 }
        val coroutineScope = rememberCoroutineScope()

        val interactionSource = remember{ MutableInteractionSource() }
        val pageIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
        val pageIsPressed by interactionSource.collectIsPressedAsState()
        val autoAdvance = !pageIsPressed && !pageIsDragged


        if(autoAdvance){
            LaunchedEffect (pagerState,interactionSource){
                while (true) {
                    delay(500)
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        pageOffsetFraction = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val pageOffset = pagerState.calculateOffsetForPage(page).absoluteValue
                val indicatorOffset = pagerState.indicatorOffsetForPage(page)

                val height = lerp(300.dp, 450.dp, 1 - pageOffset)
                val alpha = lerp(.5f, 1f, 1 - pageOffset)
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(height)
                        .alpha(alpha)
                        .background(
                            color = awesomeColors[page],
                            shape = RoundedCornerShape(16.dp)
                        )
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Page : $page",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .width(32.dp * pagerState.pageCount)
                .height(48.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount){index->
                val indicatorOffset=pagerState.indicatorOffsetForPage(index)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .weight(.5f+(indicatorOffset*3f))
                        .height(8.dp)
                        .background(
                            color = Color.Black,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){}


            }

        }

    }
}
fun PagerState.calculateOffsetForPage(page:Int): Float= (currentPage-page)+currentPageOffsetFraction
fun PagerState.indicatorOffsetForPage(page: Int): Float= 1 - calculateOffsetForPage(page).coerceIn(-1f,1f).absoluteValue