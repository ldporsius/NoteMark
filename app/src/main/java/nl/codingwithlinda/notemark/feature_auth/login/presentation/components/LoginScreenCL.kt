package nl.codingwithlinda.notemark.feature_auth.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.notemark.design_system.ui.theme.NoteMarkTheme

@Composable
fun LoginScreenCL(
    navToRegister: () -> Unit,
    modifier: Modifier = Modifier) {
    Layout(
        modifier = modifier,
        content = {
            Box(modifier = Modifier
                //.height(200.dp)
                .width(360.dp)
                .padding(16.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                LoginHeader(
                    textAlign = TextAlign.Center,
                    modifier = Modifier

                )
            }
            Box(modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .background(color = Color.Red)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navToRegister()
                    }
                ) {
                    BasicText(
                        text = "test a very long text",

                        )
                }
            }

        }
    ) { measureables, constraints ->
        println("LoginScreen measureables: $measureables")
        println("LoginScreen constraints: $constraints")

        val mConstraints = constraints.copy(
            minWidth = 360.dp.roundToPx(),
            maxWidth = 360.dp.roundToPx(),
        )
        val placeables = measureables.map {
            it.measure(mConstraints)
        }

        var xOffset = 0
        val items = mutableListOf<List<Placeable>>()
        var sublist = mutableListOf<Placeable>()

        placeables.forEachIndexed { index, placeable ->
            println("LoginScreen placeable: ${placeable.width}, ${placeable.height}")
            //println("LoginScreen xOffset: $xOffset, ")

            val fitsInRow = xOffset + placeable.width <= constraints.maxWidth
            val notFitsInRow = xOffset + placeable.width > constraints.maxWidth

            if(notFitsInRow) {
                items.add(sublist)
                sublist = mutableListOf()
                xOffset = 0

            }
            sublist.add(placeable)
            xOffset += placeable.width
        }
        if (sublist.isNotEmpty()) {
            items.add(sublist)
        }
        println("LoginScreen items: size = ${items.size}, $items")
        layout(constraints.maxWidth,
            constraints.maxHeight,

            ) {
            var xOffset = 0
            var yOffset = 0
            items.toList()
                .forEachIndexed { index, sublist ->
                    println("LoginScreen drawing items at index: $index")

                    xOffset = 0
                    sublist.forEach {placeable ->
                        println("LoginScreen xOffset: $xOffset, yOffset $yOffset")
                        try {
                            placeable.place(xOffset, yOffset)
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }

                        xOffset += placeable.width
                    }
                    yOffset += sublist.maxOf { it.height }
                }
        }
    }
}

@PreviewScreenSizes
@Preview(apiLevel = 35)
@Composable
private fun LoginScreenPreview() {
    NoteMarkTheme {
        LoginScreenCL(
            modifier = Modifier.wrapContentSize(),
            navToRegister = {}
        )

    }
}