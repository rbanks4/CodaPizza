package com.bignerdranch.android.codapizza.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.codapizza.R
import com.bignerdranch.android.codapizza.model.Pizza
import com.bignerdranch.android.codapizza.model.Topping
import com.bignerdranch.android.codapizza.model.ToppingPlacement.*

@Preview
@Composable
private fun PizzaHeroImagePreview() {
    PizzaHeroImage(
        pizza = Pizza(
            toppings = mapOf(
                Topping.Pineapple to All,
                Topping.Pepperoni to Left,
                Topping.Basil to Right
            )
        )
    )
}

@Composable
fun PizzaHeroImage(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .aspectRatio(1f)
    ) {
        var currentPizzaToppings by remember { mutableStateOf(pizza.toppings.keys.toString() + pizza.toppings.values.toString()) }
        Crossfade(targetState = currentPizzaToppings) { newToppings ->
            newToppings
        Image(
            painter = painterResource(id = R.drawable.pizza_crust),
            contentDescription = stringResource(id = R.string.pizza_preview),
            modifier = Modifier.fillMaxSize()
        )


            pizza.toppings.forEach { (topping, placement) ->
                Image(
                    painter = painterResource(id = topping.pizzaOverlayImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = when (placement) {
                        Left -> Alignment.TopStart
                        Right -> Alignment.TopEnd
                        All -> Alignment.TopCenter
                    },
                    modifier = Modifier
                        .focusable(false)
                        .aspectRatio(
                            when (placement) {
                                Left, Right -> 0.5f
                                All -> 1.0f
                            }
                        )
                        .align(
                            when (placement) {
                                Left -> Alignment.CenterStart
                                Right -> Alignment.CenterEnd
                                All -> Alignment.Center
                            }
                        )
                )
            }
        }
    }
}