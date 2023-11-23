package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agilizzulhaq.submissionakhiraplikasiandroid.R
import com.agilizzulhaq.submissionakhiraplikasiandroid.di.Injection
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.ViewModelFactory
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.components.EmptyList
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.home.ListAgent

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteAgent()
            }
            is UiState.Success -> {
                FavoriteInformation(
                    listAgent = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateAgent(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteInformation(
    listAgent: List<Agent>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
    ) {
        Column {
            if (listAgent.isNotEmpty()) {
                ListAgent(
                    listAgent = listAgent,
                    onFavoriteIconClicked = onFavoriteIconClicked,
                    contentPaddingTop = 16.dp,
                    navigateToDetail = navigateToDetail
                )
            } else {
                EmptyList(
                    alert = stringResource(R.string.empty_favorite),
                    textColor = Color.White
                )
            }
        }
    }
}