package com.shoaib.jetpackcomposetest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.shoaib.jetpackcomposetest.ui.theme.JetpackComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            JetpackComposeTestTheme() {
//                Conversation(messages = messages)
                Counter()
            }

        }

    }

}

data class Message(val author: String, val body: String)

val messages = listOf<Message>(
    Message("Ali", "hello my name is ali"),
    Message(
        "Shoaib", "Shoaib is speaking from Ibex. Today NZ cancelled ODI series" +
                "with Pakistan"
    ),
    Message("Daniel Brown", "So how's your campaign about other cricketers."),
    Message("Daniel Brown", "So how's your campaign about other cricketers.")
)

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(message: Message) {

    Row(
        Modifier
            .padding(all = 8.dp)

    ) {


        Image(

            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Content Profile Pic",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)


        )


        Spacer(modifier = Modifier.width(8.dp))


        var isExpanded by remember {
            mutableStateOf(false)
        }


        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = message.author,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondaryVariant
            )
            Spacer(modifier = Modifier.height(4.dp))



            androidx.compose.material.Surface(
                shape = MaterialTheme.shapes.large,
                elevation = 1.dp
            ) {
                Text(
                    text = message.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1
                    )
            }

        }

    }

}


@Composable
fun Counter()
{
    val count = remember{ mutableStateOf(0)}

    Button(onClick = { count.value++ }) {
        Text("I have been clicked ${count.value} times.")
        Text("I have been clicked ${count.value} times.")
    }
}






@Composable
fun TestDesign()
{

    Column{
        Text("Shoaib Khalid")
        Text("Shoaib Khalid")
    }
}

@Preview(showBackground = true)

// Black Mode

//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
@Composable
fun DefaultPreview() {


    JetpackComposeTestTheme() {

        androidx.compose.material.Surface(Modifier.fillMaxSize()) {

//            MessageCard(
//                message = Message(
//                    "Shoaib",
//                    "AsalamOalikum Khalid, Hope you are doing well."
//                )
//            )

//            Conversation(messages = messages)

            TestDesign()
        }
    }
}

