package com.matteo.mybaby2.modules.statistics.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.matteo.mybaby2.R
import com.matteo.mybaby2.common.converters.DateConverters
import com.matteo.mybaby2.modules.statistics.StatisticRead
import com.matteo.mybaby2.modules.statistics.StatisticsViewModel
import com.matteo.mybaby2.modules.statistics.UiState
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import org.koin.androidx.compose.koinViewModel

@Composable
fun Statistics(viewModel: StatisticsViewModel = koinViewModel()) {
    LaunchedEffect(1) {
        viewModel.getAllBreastFeedings()
        viewModel.getAllPoopingsAndPissings()
    }
    when (viewModel.uiState.value) {
        is UiState.Error -> Text("Error") // TODO show error message
        UiState.Loading -> CircularProgressIndicator()
        UiState.Success -> {
            return Inner(viewModel.breastFeedings.value, viewModel.diaperChanges.value, viewModel.breastFeedingMinutes.value)

        }
    }

}

@Composable
private fun Inner(breastFeedingStats: List<StatisticRead>, poopingStats: List<StatisticRead>, breastFeedingMinutesStats: List<StatisticRead>) {

    return LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (breastFeedingStats.isNotEmpty()) {
            item {
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(300.dp),
                    data = listOf(
                        Line(
                            label = stringResource(R.string.breastfeeding_number),
                            values = breastFeedingStats.map { it.amount.toDouble() },
                            color = SolidColor(MaterialTheme.colorScheme.primary),
                            firstGradientFillColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    animationMode = AnimationMode.Together(delayBuilder = {
                        it * 500L
                    })
                )
            }
            item { HorizontalDivider() }
        }
        if(breastFeedingMinutesStats.isNotEmpty()){
            item{
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(300.dp),
                    data = listOf(
                        Line(
                            label = stringResource(R.string.minutes_per_day),
                            values = breastFeedingMinutesStats.map { it.amount.toDouble() },
                            color = SolidColor(MaterialTheme.colorScheme.primary),
                            firstGradientFillColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    animationMode = AnimationMode.Together(delayBuilder = {
                        it * 500L
                    })
                )
            }
            item{HorizontalDivider()}
        }
        if (poopingStats.isNotEmpty()) {
            item {
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp),
                    data = listOf(
                        Line(
                            label = stringResource(R.string.pooping),
                            values = poopingStats.map { it.amount.toDouble() },
                            color = SolidColor(MaterialTheme.colorScheme.secondary),
                            firstGradientFillColor = MaterialTheme.colorScheme.secondary.copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    animationMode = AnimationMode.Together(delayBuilder = {
                        it * 500L
                    }),
                )
            }
        }
        if (poopingStats.isEmpty() && breastFeedingStats.isEmpty()) {
            item {
                Text(
                    stringResource(R.string.no_data),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}
