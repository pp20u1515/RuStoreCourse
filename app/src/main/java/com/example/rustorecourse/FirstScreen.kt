package com.example.rustorecourse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rustorecourse.additiona_funcs.checkPhoneNumber

class FirstScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var text by remember { mutableStateOf("") }

            firstScreenUI(
                text = text,
                onTextChange = { newText -> text = newText },
                onButtonClicked = {
                    if (text.isNotEmpty()){
                        val intent = Intent(this, SecondScreen::class.java).apply {
                            putExtra("User_Text", text)
                        }
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Вы забыли написать сообщение!", Toast.LENGTH_SHORT).show()
                    }
                },
                onButtonClicked2 = {
                    if (checkPhoneNumber(text)) {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$text")
                        }
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Неверный номер телефона!", Toast.LENGTH_SHORT).show()
                    }
                },
                onButtonClicked3 = {
                    if (text.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, text)
                        }
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Вы забыли написать сообщение!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun firstScreenUI(
    text: String,
    onTextChange: (String) -> Unit,
    onButtonClicked: () -> Unit,
    onButtonClicked2: () -> Unit,
    onButtonClicked3: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onButtonClicked,
            modifier = Modifier.width(400.dp)
        ) {
            Text("Открыть вторую Activity")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onButtonClicked2,
            modifier = Modifier.width(400.dp)
        ) {
            Text("Позвонить другу")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onButtonClicked3,
            modifier = Modifier.width(400.dp)
        ) {
            Text("Поделиться текстом")
        }
    }
}