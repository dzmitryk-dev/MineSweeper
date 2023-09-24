package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    Column(
        Modifier.fillMaxSize().background(color = Color.Gray).padding(all = 8.dp),
    ) {
        Row(Modifier.fillMaxWidth().padding(all = 4.dp)) {
            Text(
                modifier = Modifier.background(color = Color.Black)
                    .padding(4.dp)
                    .weight(weight = 1.0f)
                    .align(Alignment.CenterVertically),
                text = "000",
                textAlign = TextAlign.Center,
                color = Color.Red
            )
            Spacer(modifier = Modifier.weight(2.0f))
            Button(modifier = Modifier, onClick = { }) {
                Text(text = ":)")
            }
            Spacer(modifier = Modifier.weight(2.0f))
            Text(
                modifier = Modifier.background(color = Color.Black)
                    .padding(4.dp)
                    .weight(weight = 1.0f, fill = true)
                    .align(Alignment.CenterVertically),
                text = "000",
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
        Field(9, 9)
    }
}

@Composable
fun Field(width: Int, height: Int) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.LightGray)) {
        for (x in 0 until width) {
            Row(modifier = Modifier.fillMaxWidth().weight(weight = 1.0f)) {
                for (y in 0 until height) {
                    Button(
                        modifier = Modifier.weight(weight = 1.0f).fillMaxHeight().padding(1.dp), onClick = { },
                        elevation = elevation(),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primaryVariant)
                    ) {
                        Text("${y + 1}")
                    }
                }
            }
        }
    }

}