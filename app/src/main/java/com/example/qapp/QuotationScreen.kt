package com.example.qapp

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qapp.ui.theme.QAppTheme
import com.example.qapp.QuotationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotationScreen(viewModel: QuotationViewModel = viewModel(), modifier: Modifier = Modifier) {
    val selectedOffice = viewModel.selectedOffice.value
    val expanded = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quotation") },
                actions = {
                    IconButton(onClick = { /* Handle Check */ }) {
                        Icon(Icons.Filled.Check, contentDescription = "Check")
                    }
                }
            )
        },
        content = {
            Column(modifier = modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(48.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }
                ) {
                    TextField(
                        value = selectedOffice,
                        onValueChange = {
                            viewModel.selectedOffice.value = it
                            expanded.value = true
                        },
                        label = { Text("Office") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded.value
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(),
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        listOf("Auckland Offices", "Other Office").forEach { office ->
                            DropdownMenuItem(
                                text = { Text(office) },
                                onClick = {
                                    viewModel.selectedOffice.value = office
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Tabs for General / Items
                TabRow(selectedTabIndex = if (viewModel.selectedTab.value == "General") 0 else 1) {
                    Tab(selected = viewModel.selectedTab.value == "General", onClick = { viewModel.selectedTab.value = "General" }) {
                        Text("General")
                    }
                    Tab(selected = viewModel.selectedTab.value == "Items", onClick = { viewModel.selectedTab.value = "Items" }) {
                        Text("Items")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Net amount
                Text("Net Amount: ${viewModel.totalAmount}", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))

                // Form Inputs
                TextField(
                    value = viewModel.itemName.value,
                    onValueChange = { viewModel.itemName.value = it },
                    label = { Text("Item") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = viewModel.reason.value,
                    onValueChange = { viewModel.reason.value = it },
                    label = { Text("Reason") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = viewModel.price.value,
                    onValueChange = { viewModel.price.value = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Quantity and Discount Row
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = viewModel.quantity.value,
                        onValueChange = { viewModel.quantity.value = it },
                        label = { Text("Qty") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = viewModel.discount.value,
                        onValueChange = { viewModel.discount.value = it },
                        label = { Text("Discount %") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Add Button
                Button(
                    onClick = { viewModel.addItem() },
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ADD")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // List of Items
                Column {
                    Row(Modifier.fillMaxWidth()) {
                        Text("Item", modifier = Modifier.weight(1f))
                        Text("Price", modifier = Modifier.weight(1f))
                        Text("Qty", modifier = Modifier.weight(1f))
                        Text("Discount", modifier = Modifier.weight(1f))
                        Text("Total", modifier = Modifier.weight(1f))
                    }
                    Divider()
                    viewModel.items.forEach { item ->
                        Row(Modifier.fillMaxWidth()) {
                            Text(item.name, modifier = Modifier.weight(1f))
                            Text(item.price.toString(), modifier = Modifier.weight(1f))
                            Text(item.quantity.toString(), modifier = Modifier.weight(1f))
                            Text(item.discount.toString(), modifier = Modifier.weight(1f))
                            Text(item.total.toString(), modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun QuotationScreenPreview() {
    QAppTheme {
        QuotationScreen()
    }
}
