package com.ru.hazakura.ui.presentatin.appscreens.searchscreentab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.ru.hazakura.ui.viewModelScreen.FilterScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: FilterScreenViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    var isExpanded = remember{ mutableStateOf(false) }
    var kind = remember {
        mutableStateOf("")
    }
    var isExpandedOreder = remember{ mutableStateOf(false) }
    var order = remember {
        mutableStateOf("По популярности")
    }

    var isExpandedScore = remember{ mutableStateOf(false) }
    var score = remember {
        mutableStateOf("1")
    }

    var isExpandedStatus = remember{ mutableStateOf(false) }
    var status = remember {
        mutableStateOf<Array<String>>(emptyArray())
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = isExpanded.value,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {isExpanded.value = it}
        ) {
            OutlinedTextField(
                value = kind.value,
                onValueChange = {kind.value = it},
                readOnly = true,
                label = { Text("Категория") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value)},
                colors =  ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground, // цвет при получении фокуса
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
                    .menuAnchor()
                    .padding(8.dp)
                    .fillMaxWidth()

            )

            ExposedDropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = {false},
            ) {
                DropdownMenuItem(
                    text = { Text("Фильм") },
                    onClick = {
                        kind.value = "Фильм"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("ONA") },
                    onClick = {
                        kind.value = "ONA"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("OVA") },
                    onClick = {
                        kind.value = "OVA"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("Спэшл") },
                    onClick = {
                        kind.value = "Спэшл"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("ТВ-Спэшл") },
                    onClick = {
                        kind.value = "ТВ-Спэшл"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("ТВ-Сериал") },
                    onClick = {
                        kind.value = "ТВ-Сериал"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("13 серий") },
                    onClick = {
                        kind.value = "13 серий"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("24 серии") },
                    onClick = {
                        kind.value = "24 серии"
                        isExpanded.value = false
                    })
                DropdownMenuItem(
                    text = { Text("48 серий") },
                    onClick = {
                        kind.value = "48 серий"
                        isExpanded.value = false
                    })
            }
        }

        OutlinedTextField(
            value = status.value.joinToString(","),
            onValueChange = {},
            readOnly = true,
            label = {Text("Статус")},
            trailingIcon = { Icon(imageVector =Icons.Rounded.ArrowDropDown, contentDescription = null) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if(focusState.isFocused) {
                        isExpandedStatus.value = true
                        focusManager.clearFocus()
                    }
                }


        )
        ExposedDropdownMenuBox(
            expanded = isExpandedScore.value,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {isExpandedScore.value = it}
        ) {
            OutlinedTextField(
                value = score.value,
                onValueChange = {score.value = it},
                readOnly = true,
                label = { Text("Рейтинг") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedScore.value)},
                colors =  ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground, // цвет при получении фокуса
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
                    .menuAnchor()
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isExpandedScore.value,
                onDismissRequest = {false},
            ) {
                DropdownMenuItem(
                    text = { Text("1") },
                    onClick = {
                        score.value = "1"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("2") },
                    onClick = {
                        score.value = "2"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("3") },
                    onClick = {
                        score.value = "3"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("4") },
                    onClick = {
                        score.value = "4"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("5") },
                    onClick = {
                        score.value = "5"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("6") },
                    onClick = {
                        score.value = "6"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("7") },
                    onClick = {
                        score.value = "7"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("8") },
                    onClick = {
                        score.value = "8"
                        isExpandedScore.value = false
                    })
                DropdownMenuItem(
                    text = { Text("9") },
                    onClick = {
                        score.value = "9"
                        isExpandedScore.value = false
                    })
            }
        }

        ExposedDropdownMenuBox(
            expanded = isExpandedOreder.value,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {isExpandedOreder.value = it}
        ) {
            OutlinedTextField(
                value = order.value,
                onValueChange = {order.value = it},
                readOnly = true,
                label = { Text("Сортировка") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedOreder.value)},
                colors =  ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground, // цвет при получении фокуса
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
                    .menuAnchor()
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isExpandedOreder.value,
                onDismissRequest = {false},
            ) {
                DropdownMenuItem(
                    text = { Text("По дате выхода") },
                    onClick = {
                        order.value = "По дате выхода"
                        isExpandedOreder.value = false
                    })
                DropdownMenuItem(
                    text = { Text("По популярности") },
                    onClick = {
                        order.value = "По популярности"
                        isExpandedOreder.value = false
                    })
                DropdownMenuItem(
                    text = { Text("По рейтингу") },
                    onClick = {
                        order.value = "По рейтингу"
                        isExpandedOreder.value = false
                    })
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween) {
            Button(onClick = {
                kind.value = ""
                order.value = "По популярности"
                score.value = "1"
            }) {
                Text(text = "Сбросить")
            }

            Button(onClick = {

            }) {
                Text(text = "Применить")
            }

        }
    }
    if (isExpandedStatus.value){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)) {
                val allChecker = remember {
                    mutableStateOf(false)
                }
                Row(verticalAlignment = Alignment.CenterVertically){
                    val checkedState = remember { mutableStateOf(ToggleableState.Off) }
                    if (status.value.contains("ongoing")){
                        checkedState.value = ToggleableState.On
                    } else if( status.value.contains("!ongoing")){
                        checkedState.value = ToggleableState.Indeterminate
                    }
                    if (allChecker.value){
                        checkedState.value = ToggleableState.Off
                    }
                    TriStateCheckbox(
                        state = checkedState.value,
                        onClick = {
                            if (checkedState.value == ToggleableState.Off){
                                checkedState.value = ToggleableState.On
                                status.value += "ongoing"
                            }
                            else if(checkedState.value == ToggleableState.On){
                                checkedState.value = ToggleableState.Indeterminate
                                status.value = status.value.filter { it != "ongoing" }.toTypedArray()
                                status.value += "!ongoing"
                            }
                            else {
                                checkedState.value = ToggleableState.Off
                                status.value = status.value.filter { it != "!ongoing" }.toTypedArray()
                            }
                        })
                    Text("Онгоинг", modifier = Modifier.padding(4.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically){
                    val checkedState = remember { mutableStateOf(ToggleableState.Off) }
                    if (status.value.contains("released")){
                        checkedState.value = ToggleableState.On
                    } else if( status.value.contains("!released")){
                        checkedState.value = ToggleableState.Indeterminate
                    }
                    if (allChecker.value){
                        checkedState.value = ToggleableState.Off
                    }
                    TriStateCheckbox(
                        state = checkedState.value,
                        onClick = {
                            if (checkedState.value == ToggleableState.Off){
                                checkedState.value = ToggleableState.On
                                status.value += "released"
                            }
                            else if(checkedState.value == ToggleableState.On){
                                checkedState.value = ToggleableState.Indeterminate
                                status.value = status.value.filter { it != "released" }.toTypedArray()
                                status.value += "!released"
                            }
                            else {
                                checkedState.value = ToggleableState.Off
                                status.value = status.value.filter { it != "!released" }.toTypedArray()
                            }
                        })
                    Text("Завершено", modifier = Modifier.padding(4.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween) {
                    Button(onClick = {
                        status.value = emptyArray()
                        allChecker.value = true
                    }) {
                        Text(text = "Сбросить")
                    }

                    Button(onClick = {
                        isExpandedStatus.value = false

                    }) {
                        Text(text = "Применить")
                    }

                }
                if (allChecker.value){
                    allChecker.value = false
                }
            }
        }

    }

}