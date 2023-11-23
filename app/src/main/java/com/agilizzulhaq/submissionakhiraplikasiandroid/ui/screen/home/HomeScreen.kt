package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agilizzulhaq.submissionakhiraplikasiandroid.R
import com.agilizzulhaq.submissionakhiraplikasiandroid.di.Injection
import com.agilizzulhaq.submissionakhiraplikasiandroid.model.Agent
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.ViewModelFactory
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.common.UiState
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.components.AgentItem
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.components.EmptyList
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.components.Search

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.searchAgent(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::searchAgent,
                    listAgent = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateAgent(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listAgent: List<Agent>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.black)
    ) {
        Column {
            Search(
                query = query,
                onQueryChange = onQueryChange
            )
            if (listAgent.isNotEmpty()) {
                ListAgent(
                    listAgent = listAgent,
                    onFavoriteIconClicked = onFavoriteIconClicked,
                    navigateToDetail = navigateToDetail
                )
            } else {
                EmptyList(
                    alert = stringResource(R.string.empty_data),
                    modifier = Modifier
                        .testTag("emptyList")
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListAgent(
    listAgent: List<Agent>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("lazy_list")
    ) {
        items(listAgent, key = { it.id }) { item ->
            AgentItem(
                id = item.id,
                name = item.name,
                image = item.image,
                role = item.role,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}
