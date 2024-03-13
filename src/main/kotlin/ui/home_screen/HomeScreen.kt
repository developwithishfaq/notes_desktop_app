package ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.VerticalSpace
import core.data.models.NoteModel
import files_system.FilesHelper
import navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController, filesHelper: FilesHelper
) {
    val state by filesHelper.notes.collectAsState()

    var selectedModel by remember { mutableStateOf<NoteModel?>(null) }
    selectedModel?.let {
        Dialog(
            onDismissRequest = {
                selectedModel = null
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.65f),
                shape = RoundedCornerShape(10)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 20.dp)
                ) {
                    Text(
                        "Delete",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                    VerticalSpace()
                    Text(
                        "Are you sure you want to delete note?",
                        fontSize = 15.sp,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "No",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    selectedModel = null
                                }
                        )
                        Text(
                            "Yes",
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    selectedModel?.let {
                                        filesHelper.deleteItem(it)
                                        selectedModel = null
                                    }
                                }
                        )
                    }
                }
            }
        }
    }

    if (state.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state) { model ->
                NoteItemCard(model, onDelete = {
                    selectedModel = model
                })
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No Notes Found",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun NoteItemCard(noteModel: NoteModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = 3.dp,
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 8.dp, vertical = 8.dp
                ).weight(1f),
            ) {
                Text(
                    noteModel.title, color = Color.Black, fontSize = 15.sp
                )
                VerticalSpace()
                Text(
                    noteModel.content, fontSize = 15.sp
                )
            }
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null, modifier = Modifier.padding(
                horizontal = 10.dp
            ).size(30.dp).clickable {
                onDelete.invoke()
            })
        }
    }
}