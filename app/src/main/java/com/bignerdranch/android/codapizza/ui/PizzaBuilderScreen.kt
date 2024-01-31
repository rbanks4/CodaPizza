package com.bignerdranch.android.codapizza.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.codapizza.R
import com.bignerdranch.android.codapizza.model.Pizza
import com.bignerdranch.android.codapizza.model.Topping
import com.bignerdranch.android.codapizza.ui.theme.CodaPizzaTheme
import java.text.NumberFormat
import java.util.Locale

private const val TAG = "PizzaBuilderScreen"
//private var pizza = mutableStateOf(Pizza())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaBuilderScreen(modifier: Modifier = Modifier) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { 
                Text(stringResource(id = R.string.app_name)) }
            )
                 },
        content = {
            Column (Modifier.padding(it)) {
                ToppingList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true),
                    pizza = pizza,
                    onEditPizza = { pizza = it }
                )

                OrderButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    pizza
                )
            }
        }
    )
}

@Composable
private fun ToppingList(
    modifier: Modifier = Modifier,
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit
) {


    var currentTopping by rememberSaveable { mutableStateOf<Topping?>(null)}

    currentTopping?.let { topping ->
        ToppingPlacementDialog(
            onDismissRequest = {
                currentTopping = null
            },
            topping = topping,
            onSelect = { placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            }
        )
    }

    LazyColumn(modifier)  {
        item {
            PizzaHeroImage(
                pizza = pizza,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(Topping.entries) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    currentTopping = topping
                }
            )
        }
    }

}

@Composable
private fun OrderButton(
    modifier: Modifier = Modifier,
    pizza: Pizza
) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
    val price = currencyFormatter.format(pizza.price)
    val context = LocalContext.current
    Button(
        modifier = modifier,
        onClick = {
            Toast.makeText(context, R.string.order_placed_toast, Toast.LENGTH_LONG)
                .show()
        }
    ) {
        Text(stringResource(id = R.string.place_order_button, price).uppercase(Locale.getDefault()), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview()
@Composable
private fun Preview(){
    CodaPizzaTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PizzaBuilderScreen()
        }
    }
}