package com.example.loadinganimations.pagers

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
@Preview(showBackground = true)
fun Sample2(){
    val colorList = listOf(
        Color(0xFFDC143C), // Crimson
        Color(0xFF40E0D0), // Turquoise
        Color(0xFF50C878), // Emerald Green
        Color(0xFF0F52BA), // Sapphire Blue
        Color(0xFFFFBF00), // Amber
        Color(0xFFE6E6FA), // Lavender
        Color(0xFF36454F), // Charcoal Gray
        Color(0xFFFF7F50), // Coral
        Color(0xFF008080), // Teal
        Color(0xFF800000)  // Maroon
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        val interactionSource= remember{ MutableInteractionSource() }
        val pagerState= rememberPagerState{ 10 }

        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
        val pageIsPressed by interactionSource.collectIsPressedAsState()

        val autoAdvance= !pagerIsDragged && !pageIsPressed

        if(autoAdvance){
            LaunchedEffect(pagerState,interactionSource) {
                while (true) {
                    delay(2000)
                    val nextPage=(pagerState.currentPage+1)%pagerState.pageCount
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
        ) { page->
            Text(
                text = "Page : $page ${pagerState.currentPage}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .background(colorList[page], RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                        onClick = {}
                    )
                    .wrapContentSize()
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                repeat(pagerState.pageCount){index->
                    val isSelected= index==pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(if(isSelected) Color.DarkGray else Color.LightGray)
                            .size(16.dp)
                    )
                }
            }
        }

    }
}
