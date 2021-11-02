package com.example.room_tutorial.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import com.example.room_tutorial.ui.MyAlertDialog
import com.example.room_tutorial.ui.MyButton
import com.example.room_tutorial.ui.MyOutlinedTextFields
import kotlinx.coroutines.launch

@Composable
fun UpdateUser(navController: NavController?, viewModel: MainActivityViewModel = viewModel(), userId: Int){

    viewModel.onCurrentItemChange(userId)

    // Fetch the correct item from the data base that called this screen
    val result by viewModel.readAllData.observeAsState(initial = emptyList())
    for(item in result){
        if(item.id == userId){
            val userProfile = item

            // Fill out the correct values in the input fields that are already present
            viewModel.onFirstChange(userProfile.firstName)
            viewModel.onLastChange(userProfile.lastName)
            viewModel.onAgeChange(userProfile.age)

        }
    }





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
                title = { Text(text = "Update User") },

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
                },

               actions = {
                   Icon(
                       imageVector = Icons.Default.Delete, contentDescription = "Delete Item",
                       modifier = Modifier
                           .padding(8.dp)
                           .clickable(onClick = {
                               viewModel.onShowDialogChange(true)

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

                MyButton(name = "Update Details") {
                    if(viewModel.updateValues()){

                        navController?.navigate("All Users")

                    } else {
//                        Toast.makeText(composeContext,"Please fill all entry fields",Toast.LENGTH_LONG).show()
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please fill out all the fields",duration = SnackbarDuration.Short)
                        }
                    }

                }


            }

            val condition: Boolean by viewModel.showDialog.observeAsState(false)

            MyAlertDialog(
                title = "Delete Confirmation",
                condition = condition,
                showText = "Are you sure you want to delete this items?",
                confirmText = "Yes",
                dismissText = "No",
                dismissAction = {
                    viewModel.onShowDialogChange(false)
                    navController?.navigate("All Users")
                },
                dismissRequest = {
                
                    viewModel.onShowDialogChange(false)
                }
            ){
                viewModel.deleteThisUser()
                viewModel.onShowDialogChange(false)
                navController?.navigate("All Users")
            }

        }
    }

}























