package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.util.formatDate
import com.example.weatherapp.util.formatDateTime
import com.example.weatherapp.util.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                formatDate(weather.dt)
                    .split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherImageState(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)

            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weather.temp.max) + "º")

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray)
                ){
                    append(formatDecimals(weather.temp.min) + "º")
                }
            })
        }
    }
}


@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(30.dp))
            Text(
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption )
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(30.dp))
            Text(
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption )
        }
    }
}


@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean){
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row(

        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure}psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row(

        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)}" + if(isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }

    }

}




@Composable
fun WeatherImageState(imageUrl: String) {
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build(),
        contentDescription = "icon",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(100.dp))
}