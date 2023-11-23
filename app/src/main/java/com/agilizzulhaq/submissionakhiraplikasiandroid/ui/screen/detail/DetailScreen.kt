package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agilizzulhaq.submissionakhiraplikasiandroid.R
import com.agilizzulhaq.submissionakhiraplikasiandroid.di.Injection
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.ViewModelFactory
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState


@Composable
fun DetailScreen(
    agentId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAgentById(agentId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    id = data.id,
                    name = data.name,
                    image = data.image,
                    role = data.role,
                    origin = data.origin,
                    gender = data.gender,
                    biography = data.biography,
                    personality = data.personality,
                    appearance = data.appearance,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack
                ) { id, state ->
                    viewModel.updateAgent(id, state)
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    @DrawableRes image: Int,
    name: String,
    role: String,
    origin: String,
    gender: String,
    biography: String,
    personality: String,
    appearance: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 72.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.Transparent)
                        .border(2.dp, colorResource(id = R.color.red), shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = "agent_image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PrivacyTip,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = role,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = origin,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val genderIcon = if (gender.equals("Female", ignoreCase = true)) {
                            Icons.Default.Female
                        } else {
                            Icons.Default.Male
                        }

                        Icon(
                            imageVector = genderIcon,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = gender,
                            color = Color.White
                        )
                    }
                }
            }

            Divider(modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(colorResource(id = R.color.red))
            )

            Text(
                text = stringResource(id = R.string.biography),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = biography,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Divider(modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(colorResource(id = R.color.red))
            )

            Text(
                text = stringResource(id = R.string.personality),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = personality,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Divider(modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(colorResource(id = R.color.red))
            )

            Text(
                text = stringResource(id = R.string.appearance),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = appearance,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = colorResource(id = R.color.red))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("back_home")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }

                Text(
                    text = stringResource(R.string.detail_agent),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )

                IconButton(
                    onClick = {
                        onFavoriteButtonClicked(id, isFavorite)
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("favorite_detail_button")
                ) {
                    Icon(
                        imageVector = if (!isFavorite) Icons.Default.StarBorder else Icons.Default.Star,
                        contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(R.string.delete_favorite),
                        tint = if (!isFavorite) Color.White else Color.Yellow
                    )
                }
            }
        }
    }
}