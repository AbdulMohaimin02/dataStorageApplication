package com.example.room_tutorial.ui.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.room_tutorial.MainActivityViewModel
import com.example.room_tutorial.ui.MyButton
import com.example.room_tutorial.ui.MyOutlinedTextFields
import kotlinx.coroutines.launch

@Composable
fun AddUser(navController: NavController?, viewModel: MainActivityViewModel = viewModel()){

    // Context
//    val composeContext = LocalContext.current

    // Coroutine scope for compose

    val scope = rememberCoroutineScope()

    // Live data observers for each of the text entry fields
    val firstName: String by viewModel.firstName.observeAsState("")
    val lastName: String by viewModel.lastName.observeAsState("")
    val age: String by viewModel.age.observeAsState("")

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar= {
            TopAppBar(
                title = { Text(text = "Add User") },

                backgroundColor = Color.White,

                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                navController?.navigateUp()
                            })
                    )
                }

            )
        },
        bottomBar={},
        scaffoldState =  scaffoldState

    ) {
        Surface(
            modifier = Modifier.fillMaxSize()


        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {

                MyOutlinedTextFields(value = firstName, label ="First Name" ){
                    viewModel.onFirstChange(it)
                }

                MyOutlinedTextFields(value = lastName, label = "Last Name" ){
                    viewModel.onLastChange(it)
                }

                MyOutlinedTextFields(value = age, label = "Age"){
                    viewModel.onAgeChange(it)
                }
                    
                MyButton(name = "Add") {
                    if(viewModel.insertToDatabase()){

                        navController?.navigate("All Users")

                    } else {
//                        Toast.makeText(composeContext,"Please fill all entry fields",Toast.LENGTH_LONG).show()
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please fill out all the fields",duration = SnackbarDuration.Short)
                        }
                    }

                }






            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        AllUsers(navController = null)
//    }
//}