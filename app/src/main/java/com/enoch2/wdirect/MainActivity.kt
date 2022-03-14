package com.enoch2.wdirect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.enoch2.wdirect.ui.theme.WDirectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WDirectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = { MyTopAppBAr() },
                        content = { WDirectScreen() }
                    )
                }
            }
        }
    }
}

@Composable
fun MyTopAppBAr(){
    TopAppBar(title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Info, "About", tint = Color.White)
            }
        })
}

@Composable
fun WDirectScreen(){
    val phoneNumber = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val context = LocalContext.current
    val link = remember { mutableStateOf("https://wa.me/")}
    val linkWithMsg = remember { mutableStateOf("https://wa.me/") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Enter whatsapp number (with country code)",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp))

            Column {
                OutlinedTextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Phone, null,
                            tint = colorResource(R.color.purple_500)
                        )
                    },
                    singleLine = true,
                    label = { Text("Phone number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    keyboardActions = KeyboardActions(onDone = {
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                        }
                    )
                )

                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .height(150.dp),
                    singleLine = false,
                    label = { Text("Enter Message") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(onDone = {
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                        }
                    )
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    link.value = link.value + phoneNumber.value
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.value)))
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)) {
                    Text("Open chat")
                }

                Button(onClick = {
                    linkWithMsg.value = linkWithMsg.value + phoneNumber.value + "?text=" +
                            createLinkWithMsg(message.value)
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(linkWithMsg.value)))
                },
                    modifier = Modifier.weight(1f)) {
                    Text("Send Message")
                }
            }
        }
    }
}

fun createLinkWithMsg(message: String): String {
    return message.replace(" ", "%20")
}
