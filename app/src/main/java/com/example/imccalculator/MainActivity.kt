package com.example.imccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.imccalculator.ui.theme.IMCCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCCalculatorTheme {
                DataScreen()
            }
        }
    }
}

@Composable
fun DataScreen() {
    var nome by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var resultadoIMC by remember { mutableStateOf("") }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Nome: ")
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Digite seu nome") }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Peso (kg): ")
            TextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Digite seu peso") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Altura (m): ")
            TextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Digite sua altura") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Button(onClick = {
            if (nome.isNotBlank() && peso.isNotBlank() && altura.isNotBlank()) {
                val pesoDouble = peso.toDoubleOrNull()
                val alturaDouble = altura.toDoubleOrNull()
                if (pesoDouble != null && alturaDouble != null && alturaDouble > 0) {
                    val imc = pesoDouble / (alturaDouble * alturaDouble)
                    resultadoIMC = "$nome, seu IMC é: %.2f".format(imc)

                    resultadoIMC = when {
                        imc <= 16.9 -> "$nome, você está muito abaixo do peso ideal"
                        imc in 17.0..18.4 -> "$nome, você está abaixo do peso"
                        imc in 18.5..24.9 -> "$nome, seu peso está ideal"
                        imc in 25.0..29.9 -> "$nome, você está acima do peso"
                        imc in 30.0..34.9 -> "$nome, você está com obesidade grau 1"
                        imc in 35.0..39.9 -> "$nome, você está com obesidade grau 2"
                        else -> "$nome, você está com obesidade grau 3 (mórbida)"
                    }

                } else {
                    resultadoIMC = "Por favor, insira valores válidos"
                }
            } else {
                resultadoIMC = "Por favor, preencha todos os campos"
            }
        }) {
            Text("Calcular IMC")
        }
        Text(text = resultadoIMC)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataScreen() {
    IMCCalculatorTheme {
        DataScreen()
    }
}
