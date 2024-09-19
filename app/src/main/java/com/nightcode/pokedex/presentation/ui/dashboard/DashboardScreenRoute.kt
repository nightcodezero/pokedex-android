package com.nightcode.pokedex.presentation.ui.dashboard

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import com.nightcode.pokedex.data.network.model.Pokemon
import com.nightcode.pokedex.data.repository.FakeHomeRepository
import com.nightcode.pokedex.presentation.ui.info.CommonState
import com.nightcode.pokedex.presentation.ui.info.PokemonInfoScreenRoute
import com.nightcode.pokedex.presentation.ui.info.PokemonViewModel
import com.nightcode.pokedex.utils.reachedBottom
import com.nightcode.pokedex.utils.toHexString
import io.ktor.http.Url
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
object DashboardScreenRoute

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.DashboardScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: PokemonViewModel = koinViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.pokemonUiState.collectAsStateWithLifecycle()
    val colorCache = rememberSaveable { mutableMapOf<String, Color>() }
    val gridState = rememberLazyGridState()
    val reachedBottom: Boolean by remember { derivedStateOf { gridState.reachedBottom() } }
    val favoritePokemon by viewModel.favoritePokemon.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getFavoritePokemon()
        if (uiState is CommonState.Idle) {
            viewModel.fetchPokemonListAndDetail()
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            Timber.tag("DashboardScreen").d("Reached bottom")
            viewModel.fetchMorePokemon()
        }
    }

    Scaffold(
        containerColor = Color.LightGray.copy(alpha = 0.5f),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .height(40.dp)
                                    .background(Color.Blue), // Top color
                        )
                        Text(
                            text = "Pokedex",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                        )
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(30.dp) // Half of the box height
                                    .background(Color.Red) // Bottom color
                                    .align(Alignment.BottomCenter), // Align to the bottom
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
        ) {
            when (val state = viewModel.pokemonUiState.collectAsState().value) {
                is CommonState.Idle -> {}
                is CommonState.Loading -> {
                }

                is CommonState.Success -> {}
                is CommonState.Error -> {
                    Timber.e(state.message)
                    Toast
                        .makeText(
                            LocalContext.current,
                            state.message,
                            Toast.LENGTH_SHORT,
                        ).show()
                }
            }
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(viewModel.pokemonList.value.size) { item ->
                    val pokemon = viewModel.pokemonList.value[item]
                    val dominantColor =
                        colorCache[pokemon.name] ?: run {
                            val networkLoader = rememberNetworkLoader()
                            val dominantColorState =
                                rememberDominantColorState(
                                    loader = networkLoader,
                                    defaultColor = Color.Transparent,
                                )
                            LaunchedEffect(Unit) {
                                dominantColorState.updateFrom(Url(pokemon.imageUrl))
                                colorCache[pokemon.name] =
                                    dominantColorState.color
                            }
                            dominantColorState.color
                        }

                    Box(
                        modifier =
                            Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .height(210.dp)
                                .background(color = dominantColor)
                                .clickable {
                                    val encodedUrl =
                                        URLEncoder.encode(
                                            pokemon.imageUrl,
                                            StandardCharsets.UTF_8.toString(),
                                        )
                                    navController.navigate(
                                        PokemonInfoScreenRoute(
                                            pokemon.copy(url = encodedUrl),
                                            dominantColor.toHexString(),
                                        ),
                                    )
                                },
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            IconButton(
                                modifier =
                                    Modifier
                                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                                        .size(24.dp)
                                        .background(color = Color.White)
                                        .padding(4.dp)
                                        .align(Alignment.Start),
                                onClick = {
                                    if (favoritePokemon.contains(pokemon.name)) {
                                        viewModel.removeFavoritePokemon(pokemon.name)
                                        Toast
                                            .makeText(
                                                context,
                                                "Removed from favorite",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                    } else {
                                        viewModel.saveFavoritePokemon(pokemon.name)
                                        Toast
                                            .makeText(
                                                context,
                                                "Added to favorite",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint =
                                        if (favoritePokemon.contains(pokemon.name)) {
                                            Color.Red
                                        } else {
                                            Color.Gray
                                        },
                                )
                            }
                            AsyncImage(
                                model = pokemon.imageUrl,
                                contentDescription = null,
                                modifier =
                                    Modifier.sharedElement(
                                        state = rememberSharedContentState(key = pokemon.name.orEmpty()),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(
                                                durationMillis = 1000,
                                            )
                                        },
                                    ),
                            )
                            Box(
                                modifier =
                                    Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 8.dp,
                                                bottomEnd = 8.dp,
                                            ),
                                        ).background(color = Color.White)
                                        .padding(8.dp)
                                        .align(Alignment.End),
                            ) {
                                Text(
                                    text =
                                        pokemon.name,
                                    style = TextStyle(fontWeight = FontWeight.Bold),
                                )
                            }
                        }
                    }
                }
            }

            if (uiState is CommonState.Loading) {
                val fakeData =
                    List(20) { Pokemon(nameField = "", url = "") }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(fakeData.size) { _ ->
                        Box(
                            modifier =
                                Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .height(210.dp)
                                    .background(color = Color.Gray)
                                    .shimmerEffect(),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier =
                                        Modifier
                                            .clip(RoundedCornerShape(bottomEnd = 8.dp))
                                            .size(24.dp)
                                            .background(color = Color.LightGray)
                                            .align(Alignment.Start)
                                            .shimmerEffect(),
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier =
                                        Modifier
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 8.dp,
                                                    bottomEnd = 8.dp,
                                                ),
                                            ).width(100.dp)
                                            .height(24.dp)
                                            .background(color = Color.Gray)
                                            .align(Alignment.End)
                                            .shimmerEffect(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier =
    composed {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        val transition = rememberInfiniteTransition(label = "")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec =
                infiniteRepeatable(
                    animation = tween(1000),
                ),
            label = "",
        )

        background(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            Color(0xFFB8B5B5),
                            Color(0xFF8F8B8B),
                            Color(0xFFB8B5B5),
                        ),
                    start = Offset(startOffsetX, 0f),
                    end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
                ),
        ).onGloballyPositioned {
            size = it.size
        }
    }

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    val viewModel = PokemonViewModel(repository = FakeHomeRepository())

    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            DashboardScreen(animatedVisibilityScope = this, viewModel = viewModel, navController = navController)
        }
    }
}