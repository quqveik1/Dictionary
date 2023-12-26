package com.kurlic.dictionary.elements

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun StyledSpinner(
    items: List<String>,
    onItemSelected: (Int) -> Unit
) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            val spinner = Spinner(ctx)

            val adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                    onItemSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinner
        },
        update = { spinner ->
        }
    )
}
