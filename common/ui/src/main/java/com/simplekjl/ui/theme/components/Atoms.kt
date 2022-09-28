package com.simplekjl.ui.theme.components

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.simplekjl.ui.R
import com.simplekjl.ui.theme.SampleData
import com.simplekjl.ui.theme.base.TrackItColors
import com.simplekjl.ui.theme.base.TrackItTheme
import com.simplekjl.ui.theme.base.TrackItTypography

@Preview
@Composable
fun AddDeleteFabButtonPreview() {
    TrackItTheme {
        AddDeleteFabButton(onClick = {})
    }
}

@Composable
fun AddDeleteFabButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = TrackItColors.blue,
        content = {
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    )
}

@Preview
@Composable
fun PreviewOverflowMenu() {
    TrackItTheme {
        TrackItMainToolbar(R.string.app_name, R.string.menu_setting_content_description)
    }
}

@Composable
fun TrackItMainToolbar(
    @StringRes titleRes: Int,
    @StringRes menuSettingsDescRes: Int,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = titleRes)) },
        actions = {
            IconButton(onClick = { /*TODO jump tp the setting screen*/ }) {
                Icon(
                    Icons.Default.Settings,
                    tint = TrackItColors.white,
                    contentDescription = stringResource(menuSettingsDescRes)
                )
            }
        },
        backgroundColor = TrackItColors.blue,
        contentColor = TrackItColors.gray_4,
        elevation = 0.dp,
        modifier = modifier
    )
}

@Preview
@Composable
fun WeightValueElementTest() {
    TrackItTheme {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            WeightValueElement(
                metricNameRes = R.string.weight_current_label,
                pluralsRes = R.string.kg_format,
                weightValue = 82.0,
                colorRes = TrackItColors.plum,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            WeightValueElement(
                metricNameRes = R.string.weight_goal_label,
                pluralsRes = R.string.kg_format,
                weightValue = 74.0,
                colorRes = TrackItColors.cucumber
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

/* TextField won't work out of the box while composing
doesn't allow to populate the value in compose time*/
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeightValueElement(
    modifier: Modifier = Modifier,
    @StringRes metricNameRes: Int,
    @StringRes pluralsRes: Int,
    weightValue: Double,
    colorRes: Color,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = metricNameRes),
            style = TrackItTypography().h5,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)

        )
        ClickableText(
            text = AnnotatedString(stringResource(id = pluralsRes, weightValue.toString())),
            style = TrackItTypography().h6.copy(colorRes),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            onClick = { onClick("") }
        )
    }
}

@Preview
@Composable
fun ColorDescriptionPreview() {
    TrackItTheme {
        Row {
            ColorDescription(
                descriptionRes = R.string.weight_goal_label,
                color = TrackItColors.mint
            )
            Spacer(modifier = Modifier.padding(8.dp))
            ColorDescription(
                descriptionRes = R.string.weight_lost_label,
                color = TrackItColors.cucumber
            )
        }
    }
}

@Composable
fun ColorDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
    color: Color,
    shape: Shape = RoundedCornerShape(2.dp)
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .wrapContentWidth()
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(shape)
                .background(color)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(id = descriptionRes),
            modifier = Modifier.padding(start = 8.dp), style = TrackItTypography().caption
        )
    }
}

@Preview
@Composable
fun LinearChartProgressTest() {

    // ...
    TrackItTheme {
        LinearChartProgress(
            weightValues = SampleData.entries,
            topLimitLabel = R.string.app_name,
            bottomLimitLabel = R.string.weight_progress_label,
            lineColor = TrackItColors.mint,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}

// TODO do more flexibility to work on the paid version
@Composable
fun LinearChartProgress(
    modifier: Modifier = Modifier,
    weightValues: List<Entry> = emptyList(),
    @StringRes topLimitLabel: Int,
    @StringRes bottomLimitLabel: Int,
    lineColor: Color,
) {
    val topLabel = stringResource(id = topLimitLabel)
    val bottomLabel = stringResource(id = bottomLimitLabel)
    AndroidView(
        modifier = modifier,
        factory = { context -> LineChart(context) },
        update = { linearChart ->
            val dataSet = LineDataSet(weightValues, topLabel).apply {
                color = lineColor.toArgb()
            }
            val linearData = LineData(dataSet)
            linearChart.data = linearData
            linearChart.setPinchZoom(false)
            linearChart.setScaleEnabled(false)
            linearChart.setTouchEnabled(true)
            linearChart.isDoubleTapToZoomEnabled = false
            linearChart.axisRight.setDrawGridLines(false)
            linearChart.axisRight.isEnabled = false
            linearChart.xAxis.setDrawGridLines(false)
            linearChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

            linearChart.invalidate()
        }
    )
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}
