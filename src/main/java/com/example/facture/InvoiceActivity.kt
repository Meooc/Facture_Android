package com.example.facture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InvoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvoicePage()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InvoicePage() {
    var quantity by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }
    var taxRate by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var isLoyalCustomer by remember { mutableStateOf(false) }
    var amountExcludingTax by remember { mutableStateOf("") }
    var amountIncludingTax by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showDiscount by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = quantity,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    quantity = newValue
                }
            },
            label = { Text("Quantité") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        TextField(
            value = unitPrice,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    unitPrice = newValue
                }
            },
            label = { Text("Prix unitaire") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        TextField(
            value = taxRate,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    taxRate = newValue
                }
            },
            label = { Text("Taux TVA (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isLoyalCustomer,
                onCheckedChange = {
                    isLoyalCustomer = it
                    showDiscount = it
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Fidèle")
        }

        if (showDiscount) {
            TextField(
                value = discount,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        discount = newValue
                    }
                },
                label = { Text("Remise (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                val quantityValue = quantity.toFloatOrNull() ?: 0f
                val unitPriceValue = unitPrice.toFloatOrNull() ?: 0f
                val taxRateValue = taxRate.toFloatOrNull() ?: 0f
                val discountValue = discount.toFloatOrNull() ?: 0f

                val subTotal = quantityValue * unitPriceValue
                val discountAmount = subTotal * discountValue / 100
                val amountExcludingTaxValue = subTotal - discountAmount
                val taxAmount = amountExcludingTaxValue * taxRateValue / 100
                val amountIncludingTaxValue = amountExcludingTaxValue + taxAmount

                amountExcludingTax = amountExcludingTaxValue.toString()
                amountIncludingTax = amountIncludingTaxValue.toString()
                keyboardController?.hide()
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Calculer")
        }
        Button(
            onClick = {
                quantity = ""
                unitPrice = ""
                taxRate = ""
                discount = ""
                isLoyalCustomer = false
                amountExcludingTax = ""
                amountIncludingTax = ""
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Remise à zéro")
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Montant hors taxe: $amountExcludingTax €",
            fontSize = 18.sp
        )
        Text(
            text = "Montant TTC: $amountIncludingTax €",
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInvoicePage() {
    InvoicePage()
}
