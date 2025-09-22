package com.example.loadinganimations.pagers

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import kotlin.math.absoluteValue

@Composable
@Preview(showBackground = true)
fun Sample1(){
    val pagerState= rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            11
        }
    )
    val coroutineScope= rememberCoroutineScope()
    val fling= PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        snapPosition = SnapPosition.End,
        verticalAlignment = Alignment.CenterVertically,
        flingBehavior = fling,
        contentPadding = PaddingValues(0.dp),
    ) { pageIndex ->

        val pageOffset = pagerState.getOffsetDistanceInPages(pageIndex).absoluteValue
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(
//                   when(pageIndex){
//                       0-> Color.Red
//                       1-> Color.Green
//                       in 2..5 -> Color.Gray
//                       in 5 until 9 -> Color.Yellow
//                       9 -> Color.Magenta
//                       else -> Color.Blue
//                   }
//            )
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val height = lerp(450, 300, pageOffset)
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(height.dp)
                    .graphicsLayer{
                        alpha=lerp(1f,.1f,pageOffset)
                    }
                    .background(
                        color = Color.Cyan,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(width = 2.dp, color = Color.Red, RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "page no ${pageIndex + 1} and page offset is $pageOffset"
                )
            }

//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.animateScrollToPage(2)
//                    }
//                },
//                modifier = Modifier.padding(24.dp)
//            ) {
//                Text(
//                    text = "go to page 3"
//                )
//            }


        }
    }
        Row (
            modifier = Modifier.fillMaxWidth().padding(horizontal = 65.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            repeat(pagerState.pageCount){index->
                val isCurrentPage=index==pagerState.currentPage
                Box(
                    modifier = Modifier
                        .size(if(isCurrentPage) 24.dp else 16.dp)
                        .background(
                            color = if(isCurrentPage) Color.Green else Color.Red,
                            shape = CircleShape
                        )
                )
            }
        }

}