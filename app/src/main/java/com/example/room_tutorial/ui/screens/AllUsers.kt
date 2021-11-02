package com.example.room_tutorial.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.room_tutorial.MainActivityViewModel
import com.example.room_tutorial.ui.MyAlertDialog
import com.example.room_tutorial.ui.MyAppBar
import com.example.room_tutorial.ui.MyCard
import com.example.room_tutorial.ui.MyFloatingActionButton


@Composable
fun AllUsers(navController: NavController?, viewModel:MainActivityViewModel = viewModel()) {

    val result by viewModel.readAllData.observeAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar= {
                MyAppBar(title = "All Users",
                    icon = Icons.Default.Delete,
                    description = "Delete All items"
                ) {
//                    navController?.navigate("delete_screen/${"all users"}")
                    viewModel.onShowDialogChange(true)
                }
        },
        bottomBar={},
        floatingActionButton = {
            MyFloatingActionButton(text = "Add User") {
                navController?.navigate("Add User")
            }
        }

    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            itemsIndexed(
                items = result,

            ){
                index, item ->

                MyCard(user = item
                ){
                    navController?.navigate("Update User/${item.id}")

                }
            }
        }

        val condition: Boolean by viewModel.showDialog.observeAsState(false)

        MyAlertDialog(
            title = "Delete Confirmation",
            condition = condition,
            showText = "Are you sure you want to delete all items?",
            confirmText = "Yes",
            dismissText = "No",
            dismissAction = {
                viewModel.onShowDialogChange(false)
            },
            dismissRequest = {
                viewModel.onShowDialogChange(false)
            }
        ){
            viewModel.deleteAllUser()
            viewModel.onShowDialogChange(false)
        }




    }

}



