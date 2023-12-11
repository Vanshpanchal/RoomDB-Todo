package com.example.jetpackcrashcourse

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: TaskState, onEvent: (ScreenEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val msg = "Enter Task"
        OutlinedTextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                textColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                cursorColor = Color.Black,
                placeholderColor = Color.DarkGray,
                unfocusedTrailingIconColor = Color.Red,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer

            ),
            placeholder = { Text(text = msg) }, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ), value = state.Task_Name, onValueChange = {
                onEvent(ScreenEvent.setTaskName(it))
            }, modifier = Modifier

        )

        Button(
            onClick = {
                if (state.Task_Name.isNotBlank()) {
                    onEvent(ScreenEvent.save)
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(text = "Add")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val btnColor: Color = if (!state.sortbyDate) {
//                Color.Green
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.inverseOnSurface
            }

            TextButton(
                onClick = { onEvent(ScreenEvent.sort) }, modifier = Modifier
                    .wrapContentWidth()
                    .padding(4.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = btnColor,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = "Sort By Date", color = MaterialTheme.colorScheme.onSurface)
            }


        }
        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn {
                items(state.Tasks) { item ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .focusable(false),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text(
                            text = item.TaskName,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                onClick = {
                                    onEvent(
                                        ScreenEvent.isDone(
                                            id = item.Id,
                                            sts = !item.isSelected
                                        )
                                    )
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done",
                                    tint = if (item.isSelected) colorResource(id = R.color.DarkGreen) else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            IconButton(
                                onClick = {
                                    onEvent(ScreenEvent.deleted(item))

                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}