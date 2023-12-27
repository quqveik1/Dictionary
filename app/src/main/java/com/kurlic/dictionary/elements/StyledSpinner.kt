package com.kurlic.dictionary.elements

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.kurlic.dictionary.R

@Composable
fun StyledSpinner(
    items: List<String>,
    onItemSelected: (Int) -> Unit
) {
    val context = LocalContext.current
    StyledCard() {
        AndroidView(factory = { ctx ->
            val spinner = Spinner(ctx)

            val adapter = ArrayAdapter(
                ctx,
                R.layout.spinner_selected_item,
                items
            )

            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

            spinner.adapter = adapter
            spinner.solidColor
            spinner.setPopupBackgroundResource(R.drawable.shape_dropdown)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    onItemSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinner
        },
            update = { spinner ->
            })
    }
}
