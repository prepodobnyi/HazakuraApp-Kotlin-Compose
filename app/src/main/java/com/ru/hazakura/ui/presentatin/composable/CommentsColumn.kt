package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ru.hazakura.domain.model.Comments

@Composable
fun CommentsItem(
    comments: Comments,
    selectedCommentValue: MutableState<String?>
) {
    Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 32.dp)) {
        AsyncImage(
            model = comments.user.avatar,
            contentDescription = "Avatar",
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(48.dp)),
            contentScale = ContentScale.Crop
        )

        Column {
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = comments.user.nickname)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = comments.created_at)
            }
            if (comments.html_body.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = comments.html_body, modifier = Modifier.padding(start = 8.dp))
            }
            if (comments.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(modifier = Modifier.padding(start = 8.dp)) {
                    items(comments.images.size) { image ->
                        AsyncImage(
                            model = comments.images[image],
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .height(180.dp)
                                .padding(end = 8.dp)
                                .clickable { selectedCommentValue.value = comments.images[image] },
                            contentScale = ContentScale.Crop
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun CommentsColumn(comments: List<Comments>, selectedCommentValue: MutableState<String?>) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 700.dp)) {
        items(comments.size) { comment ->
            CommentsItem(comments = comments[comment], selectedCommentValue)
        }
    }
}