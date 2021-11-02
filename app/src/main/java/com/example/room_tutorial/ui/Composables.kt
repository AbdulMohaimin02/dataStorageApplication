package com.example.room_tutorial.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.room_tutorial.data.User
import com.example.room_tutorial.ui.theme.Teal200

@Composable
fun MyCard(user:User, clickFunc: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { clickFunc.invoke() }
            .wrapContentHeight(),
        elevation = 5.dp,
        shape = RoundedCornerShape(15),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${user.id})",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text =  user.firstName,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = user.lastName,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = user.age,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(10.dp)
            )


        }
    }
}

@Composable
fun MyButton(name: String, clickFunc: () -> Unit) {
    Button(
        onClick = {
            clickFunc.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),

        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Teal200,
            contentColor = Color.White

        )

    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun MyFloatingActionButton (text: String, clickFunc: () -> Unit) {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        },
        onClick = {
            clickFunc.invoke()
        },
        modifier = Modifier.padding(24.dp)
    )
}

@Composable
fun MyOutlinedTextFields(value:String, label:String, onTextChange: (String) -> Unit){
    val textState =
    OutlinedTextField(

        value = value,
        onValueChange = onTextChange,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    )
}


@Composable
fun MyAppBar(title: String, icon: ImageVector, description: String, iconClickAction: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },

        backgroundColor = Color.White,

        actions = {
            Icon(
                imageVector = icon, contentDescription = description,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        }

    )
}



@Composable
fun MyAlertDialog(
    condition: Boolean?,
    title:String,
    showText: String,
    confirmText:String,
    dismissText:String,
    dismissAction: () -> Unit,
    dismissRequest: () -> Unit,
    confirmAction: () -> Unit
) {

    if (condition == true){
        MaterialTheme {
            Column {

                AlertDialog(


                    onDismissRequest = {
                        dismissRequest.invoke()

                    },
                    title = {
                        Text(text = title)
                    },
                    text = {
                        Text(showText)
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                confirmAction.invoke()
                            }) {
                            Text(confirmText)
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                dismissAction.invoke()
                            }) {
                            Text(dismissText)
                        }
                    }
                )


            }
        }

    }

}

//@Composable
//fun presentDialog(condition: Boolean?,onclocse) {
//    if(condition ==  true)
//        AlertDialog(
//            onCloseRequest = {
//                showDialog.value = false
//            },
//            title = {
//                Text(text = "Alert Dialog")
//            },
//            text = {
//                Text("JetPack Compose Alert Dialog!")
//            },
//            confirmButton = {
//                Button("Confirm", onClick = {
//                    // reset the value to false, which will make the dialog go away
//                    showDialog.value = false
//                })
//            }
//        )
//}
//}