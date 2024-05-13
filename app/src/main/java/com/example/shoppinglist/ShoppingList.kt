package com.example.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.PurpleGrey40

//We will use the list through data classes
data class sItem(
    val id :Int,
    var name:String,
    var quantity:Int,
    var isEdited:Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shoppingList(){
    var shopItem by remember { mutableStateOf(listOf<sItem>()) }
    var showDialog  by remember { mutableStateOf(false)}
    var itemName by remember { mutableStateOf("")}
    var itemQuantity by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")

        }
        //Lazy Coloumn is a composable that render
        // the things only requird out of the list of infinite objects
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(shopItem){
                shoppingListItem( it,{},{})

            }

        }

    }
    //creating an alertdialog which is like a popup
    // but we can use it for several purposes
    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = { /*TODO*/ },
            title = {Text("Add Item")},
            text = {
                Column {
                    Text("Name of item" )
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
//                        placeholder = "Enter name of item",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Text("Quantity" )
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
//                        placeholder = "Enter name of item",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(onClick = {
                            if(itemName.isNotBlank()){
                                val newItem = sItem(
                                    id = shopItem.size + 1,
                                    name = itemName,
                                    quantity = itemQuantity.toInt()
                                )
                                shopItem = shopItem + newItem
                                showDialog = false
                                itemName = ""

                            }
                        }) {
                            Text("Add")
                        }
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }

                    }
                }
            }

        )
    }
}

@Composable
fun shoppingListItem(
    item : sItem,
    onEditClick:()->Unit,
    onDeleteClick:()->Unit,
){
    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0xff018786)),
                shape = RoundedCornerShape(20)
            )
    ){
        Text(text = item.name, modifier = Modifier.padding(8.dp))
    }
}