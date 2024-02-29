package com.example.facture

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.facture.ui.theme.FactureTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginForm(this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun LoginForm(context: Context) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val usernameState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }

        TextField(
            value = usernameState.value,
            onValueChange = { usernameState.value = it },
            label = { Text("Nom d'utilisateur") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                val validUsername = "etudiant"
                val validPassword = "AzertY"
                if (usernameState.value == validUsername && passwordState.value == validPassword) {
                    val intent = Intent(context, InvoiceActivity::class.java)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        "Nom d'utilisateur ou mot de passe incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Se connecter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    val context = LocalContext.current

    FactureTheme {
        LoginForm(context)
    }
}