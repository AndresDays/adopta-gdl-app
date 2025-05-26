package com.example.petbook.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults


data class PasswordOutputTransformation(val char: String) : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        replace(0, length, char.repeat(length))
    }
}

@Composable
fun FormField(
    modifier: Modifier,
    textFieldState: TextFieldState,
    text: String = "",
    inputType: KeyboardType,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Text(text = text)
        BasicTextField(
            outputTransformation = if (isPassword) PasswordOutputTransformation("●") else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = inputType
            ),
            modifier = Modifier.fillMaxSize(),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                    }
                }
            },
            state = textFieldState
        )
    }
}





@Composable
fun FormField2(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(32.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp)
                .padding(top = 0.dp)
        )
    }
}




@Composable
fun FormFieldArea(
    modifier: Modifier,
    textFieldState: TextFieldState,
    text: String = "",
    inputType: KeyboardType,
    isPassword: Boolean = false,
    maxHeightInLines: Int
) {
    Column(modifier = modifier) {
        Text(text = text)
        BasicTextField(
            outputTransformation = if (isPassword) PasswordOutputTransformation("●") else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = inputType
            ),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 128.dp),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = maxHeightInLines),
            decorator = { innerTextField ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        innerTextField()
                    }
                }
            },
            state = textFieldState
        )
    }
}