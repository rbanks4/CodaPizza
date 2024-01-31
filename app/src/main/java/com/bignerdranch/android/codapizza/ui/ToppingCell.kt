package com.bignerdranch.android.codapizza.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.codapizza.model.Topping
import com.bignerdranch.android.codapizza.model.ToppingPlacement
import com.bignerdranch.android.codapizza.ui.theme.CodaPizzaTheme

private const val TAG = "ToppingCell"

@Composable
fun ToppingCell(
    topping: Topping,
    placement: ToppingPlacement?,
    onClickTopping: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClickTopping() }
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Checkbox(checked = placement != null, onCheckedChange = { onClickTopping() })
        CheckboxLabel(topping, placement)
    }
}

@Composable
private fun CheckboxLabel(
    topping: Topping,
    placement: ToppingPlacement?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(start = 4.dp)
            .fillMaxWidth(1f)
    ) {
        Text(topping.name, style = MaterialTheme.typography.bodyMedium)
        placement?.let {
            Text(it.name, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToppingCellPreviewBasilAll() {
    CodaPizzaTheme {
        ToppingCell(Topping.Basil, ToppingPlacement.All, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ToppingCellPreviewPepperoniNone() {
    CodaPizzaTheme {
        ToppingCell(Topping.Pepperoni, null, {})
    }
}