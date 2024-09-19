package com.nightcode.pokedex.presentation.ui.info

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.nightcode.pokedex.data.network.model.Pokemon
import com.nightcode.pokedex.utils.serializableType
import com.nightcode.pokedex.utils.toColor
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class PokemonInfoScreenRoute(
    val pokemon: Pokemon,
    val backgroundColor: String,
) {
    companion object {
        val typeMap = mapOf(typeOf<Pokemon>() to serializableType<Pokemon>())

        fun from(savedStateHandle: SavedStateHandle) = savedStateHandle.toRoute<Pokemon>(typeMap)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonInfoScreen(
    pokemon: Pokemon,
    backgroundColor: String,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Scaffold(
        containerColor = backgroundColor.toColor(),
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor.toColor()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = pokemon.url,
                    contentDescription = null,
                    modifier =
                        Modifier.sharedElement(
                            state = rememberSharedContentState(key = pokemon.name),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            },
                        ),
                )
                Text(
                    modifier =
                        Modifier
                            .padding(12.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp),
                            ).padding(horizontal = 16.dp, vertical = 8.dp),
                    text = pokemon.name,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                )
            }
            IconButton(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .clip(CircleShape)
                        .size(32.dp)
                        .background(color = Color.White),
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                )
            }
        }
    }
}