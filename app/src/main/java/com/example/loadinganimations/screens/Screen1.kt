package com.example.loadinganimations.screens


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MovableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.customcomponents.progressBars.CircularBarRotation2
import com.example.customcomponents.progressBars.MovingBoxProgressBar
import com.example.loadinganimations.pagers.calculateOffsetForPage
import com.example.loadinganimations.pagers.indicatorOffsetForPage
import com.example.loadinganimations.progressBars.BubbleDotProgressBar
import com.example.loadinganimations.progressBars.CircularBarRotation1
import com.example.loadinganimations.progressBars.CircularProgressBar
import com.example.loadinganimations.progressBars.ExpandedBarProgressBar
import com.example.loadinganimations.progressBars.LoadingAnimation
import com.example.loadinganimations.ui.theme.BackgroundColor1
import com.example.loadinganimations.ui.theme.BackgroundColor2
import com.example.loadinganimations.ui.theme.BackgroundColor3
import com.example.loadinganimations.ui.theme.MagicGradient
import com.example.loadinganimations.ui.theme.RelaxingRedGradient
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent
import kotlin.math.absoluteValue

@Composable
@Preview(showBackground = true)
fun Screen1(){
    val animationsNames=listOf(
        "Radial Expansion Animation",
        "Tile Transition Animation",
        "Petal Bloom Animation",
        "Scaling Dots Animation",
        "Bar Loader Animation",
        "Bouncing Dots Animation",
        "Circular Spinner Animation",
        "Circular Orbit Animation"
    )
    val animations: List<@Composable () -> Unit> = listOf(
        { CircularBarRotation1() },
        { MovingBoxProgressBar() },
        { CircularBarRotation2(height = 45.dp, padding = 75.dp, shape = RoundedCornerShape(32.dp)) },
        { BubbleDotProgressBar() },
        { ExpandedBarProgressBar() },
        { LoadingAnimation(circleColor = Color.DarkGray) },
        { CircularProgressBar() },
        { CircularBarRotation2(height = 30.dp, padding = 120.dp, shape = RoundedCornerShape(32.dp), brush = RelaxingRedGradient) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundColor1,
                        BackgroundColor2,
                        BackgroundColor3
                    )
                )
            )
    ){
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x= (-50).dp,y=100.dp)
                .clip(CircleShape)
                .background(color = Color.White.copy(alpha = 0.1f))
        )
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x=250.dp,y=300.dp)
                .clip(CircleShape)
                .background(color = Color.Yellow.copy(alpha = 0.2f))
        )



        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // pager stays centered
        ) {
            val pagerState= rememberPagerState{ 8 }
            val coroutineScope= rememberCoroutineScope()

            val interactionSource = remember { MutableInteractionSource() }
            val isPageDragged by pagerState.interactionSource.collectIsDraggedAsState()
            val isPagePressed by interactionSource.collectIsPressedAsState()

            val autoAdvance = !isPageDragged && !isPagePressed

            if(autoAdvance){
                LaunchedEffect (pagerState,interactionSource){
                    while (true) {
                        delay(3000)
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(
                            page = nextPage,
                            pageOffsetFraction = 0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.wrapContentHeight()
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val pageOffset = pagerState.calculateOffsetForPage(page).absoluteValue
                    val height = lerp(300.dp, 450.dp, 1 - pageOffset)
                    if(page%2==0)
                    GlassmorphicCard(
                        modifier = Modifier.height(height).width(300.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            animations[page].invoke()

                            Text(
                                text = animationsNames[page],
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(alignment = Alignment.BottomCenter)
                            )
                        }
                    }
                    else{
                        EnhancedGlassmorphicCard(
                            modifier = Modifier.height(height).width(300.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                animations[page].invoke()

                                Text(
                                    text = animationsNames[page],
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.align(alignment = Alignment.BottomCenter)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(34.dp * pagerState.pageCount)
                    .height(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { index ->
                    val indicatorOffset = pagerState.calculateIndicatorOffset(index)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(.5f + (indicatorOffset * 3f))
                            .height(8.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

fun PagerState.calculatePageOffset(page:Int) : Float = (currentPage-page)+ currentPageOffsetFraction
fun PagerState.calculateIndicatorOffset(page: Int) : Float = 1- calculatePageOffset(page).coerceIn(-1f,1f).absoluteValue





@Composable
fun GlassmorphicCard(
    modifier: Modifier= Modifier,
    content: @Composable ColumnScope.()-> Unit
){
    val glassBrush= Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.25f),
            Color.White.copy(alpha = 0.1f)
        )
    )
    val borderBrush= Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.1f)
        )
    )
    val cardShape= RoundedCornerShape(20.dp)

    Card (
        modifier=modifier
            .clip(cardShape)
            .background(glassBrush)
            .border (
                width = 1.dp,
                brush = borderBrush,
                shape = cardShape
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ){
        Column(
            modifier= Modifier.padding(24.dp),
            content = content
        )
    }
}
@Composable
fun EnhancedGlassmorphicCard(
    modifier: Modifier= Modifier,
    content:@Composable ColumnScope.()-> Unit
){
    val cardShape= RoundedCornerShape(24.dp)

    val backgroundBush= Brush.radialGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.05f),
        )
    )
    val borderBrush= Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.8f),
        )
    )

    Box(
        modifier=modifier
            .clip(cardShape)
    ){
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = backgroundBush)
        )
        Card(
            modifier= Modifier
                .matchParentSize()
                .border(
                    width = 1.5.dp,
                    brush = borderBrush,
                    shape = cardShape
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ){
            Column(
                modifier = Modifier.padding(28.dp),
                content = content
            )
        }
    }

}
