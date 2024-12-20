package com.example.fetch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetch.R
import com.example.fetch.ui.theme.FetchTestTheme


@Composable
fun FetchListScreen(
    uiState: FetchUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        if (uiState.errorMessage != null) {
            // TODO show snackbar
        }

        if (uiState.isLoading) {
            MyProgress()
        } else {
            FetchList(uiState.list)
        }
    }
}

// TODO extract to commonUi
@Composable
fun MyProgress() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.please_wait)
        )
    }
}

@Composable
fun FetchList(fetchItems: List<FetchItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(items = fetchItems) { index, fetchItem ->
            if (index == 0) {
                FetchListHeaderItem(fetchItem.listId)
            } else {
                HorizontalDivider(thickness = 1.dp)

                if (fetchItems[index - 1].listId != fetchItem.listId) {
                    FetchListHeaderItem(fetchItem.listId)
                }
            }
            FetchListItem(item = fetchItem)
        }
    }
}

@Composable
fun FetchListHeaderItem(listId: Int) {
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(text = "Section ${listId}", style = MaterialTheme.typography.titleLarge)
        HorizontalDivider(thickness = 4.dp)
    }
}

@Composable
fun FetchListItem(item: FetchItem) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun FetchItemPreview() {
    FetchTestTheme {
        FetchListItem(FetchItem(id = 99, listId = 459, name = "Test"))
    }
}