package me.hxdong.vntour.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.hxdong.vntour.data.Tourism

@Composable
fun RecommendationCard(modifier: Modifier, tourism: Tourism, onClickCard: () -> Unit) {
    ElevatedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(18.dp))
                .clickable { onClickCard() }
        ) {
            Image(
                painter = painterResource(id = tourism.picture),
                contentDescription = tourism.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(all = 10.dp)
                    .size(70.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
            )
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = tourism.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = tourism.name,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                )
            }
        }
    }
}