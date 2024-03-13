package ui.add_note_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.VerticalSpace
import core.data.models.NoteModel
import files_system.FilesHelper
import kotlinx.coroutines.launch
import navigation.NavController
import ui.components.Screens

@Composable
fun AddNoteScreen(
    navController: NavController,
    filesHelper: FilesHelper
) {
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val (showSnackBar, setShowSnackBar) = remember {
        mutableStateOf(false)
    }
    if (showSnackBar) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = "Note Saved",
                actionLabel = "View"
            )
            when (result) {
                SnackbarResult.Dismissed -> {
                    setShowSnackBar(false)
                }

                SnackbarResult.ActionPerformed -> {
                    setShowSnackBar(false)
                    navController.navigate(Screens.HomeScreen.name)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var title by remember {
                mutableStateOf("")
            }
            var content by remember {
                mutableStateOf("")
            }
            AddNoteField(title, placeHolder = "Title..", onChange = {
                title = it
            })
            VerticalSpace()
            AddNoteField(content, placeHolder = "Content..", onChange = {
                content = it
            })
            VerticalSpace()
            Button(
                onClick = {
                    filesHelper.saveNote(NoteModel(title, content))
                    setShowSnackBar(true)
                },
            ) {
                Text("Save Note")
            }
        }
    }
}

@Composable
fun AddNoteField(value: String, placeHolder: String, onChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        value = value,
        onValueChange = {
            onChange.invoke(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Black
        ),
        placeholder = {
            Text(placeHolder)

        }
    )
}