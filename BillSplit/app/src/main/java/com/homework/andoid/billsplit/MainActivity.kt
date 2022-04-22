package com.homework.andoid.billsplit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.andoid.billsplit.ui.theme.BillSplitTheme
import com.homework.andoid.billsplit.ui.theme.Pink
import com.homework.andoid.billsplit.ui.theme.Purple200
import com.homework.andoid.billsplit.ui.theme.Purple700
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Composable
fun MyApp(content: @Composable ()-> Unit){
    BillSplitTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Composable
fun TopHeader(amount: Float = 0f){
    Card(
        modifier = Modifier
            .size(348.dp,128.dp)
            ,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Purple200
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text="Total Per Person", style = TextStyle(fontSize = 28.sp))
            val roundAmount = String.format("%.2f", amount)
            Text(text="$$roundAmount", style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun MainContent(){
    var expand by remember {
        mutableStateOf(false)
    }
    var billTotal by remember {
        mutableStateOf(0f)
    }
    var numb by remember {
        mutableStateOf(1)
    }
    var tip by remember {
        mutableStateOf(0f)
    }
    Column(
        modifier = Modifier
            .padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        val totalPerPerson = (billTotal + tip) / numb
        TopHeader(totalPerPerson)
        Card (
            modifier = Modifier
                .padding(12.dp)
            ,
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(16.dp),

                ) {
            Column (
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                EnterBill(){
                    state: Boolean, total: Float ->
                        expand = state
                        billTotal = total
                }
                if (expand){
                    Split(numb){
                        result -> numb = result
                    }
                    Tip(billTotal, tip){
                        newTipVal: Float -> tip = newTipVal
                    }
                } else {
                    numb = 1
                    tip = 0.00f
                }
            }
        }
    }
}

@Composable
fun CircleButton(content: ImageVector = Icons.Default.Add, numb: Int, count: (Int) -> Unit){
    OutlinedButton(
        modifier = Modifier.size(38.dp),
        onClick = {
                  if (content == Icons.Default.Add){
                      count(numb + 1)
                  } else {
                      count(numb - 1)
                  }
        },
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape
    ){
        Icon(content, contentDescription = null)
    }
}


@Composable
fun EnterBill(openSplitAndTip: (Boolean, Float) -> Unit){
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = { Text(text = "Enter bill") },
        readOnly = false,
        enabled = true,
        onValueChange = {
            text = it
                        if (text != ""){
                            openSplitAndTip(true, text.toFloat())
                        } else {
                            openSplitAndTip(false, 0.00f)
                        }
        },
        placeholder = {Text("Enter your total bill")},
        leadingIcon = {Icon(Icons.Default.AttachMoney, null)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
}

@Composable
fun Split(numb: Int, setNumb:(value: Int) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Split", style= TextStyle(fontSize = 20.sp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleButton(Icons.Default.Remove, numb) {
                result -> if (result > 0 ) {
                    setNumb(result)
                }
            }
            Text(text = "$numb", style= TextStyle(fontSize = 20.sp))
            CircleButton(Icons.Default.Add, numb){
                result -> setNumb(result)
            }
        }
    }
}

@Composable
fun Tip(billTotal: Float, tip: Float, setTip:(newTipVal: Float) -> Unit){
    var percent by remember {
        mutableStateOf(0)
    }
    val newTip = percent/100.0 * billTotal
    setTip(newTip.toFloat())
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
            ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tip", style= TextStyle(fontSize = 20.sp))
            Text(text = "$$tip", style= TextStyle(fontSize = 20.sp))
        }
        TipSeekBar{
            newPercent -> percent = newPercent
        }
    }
}
@Composable
fun TipSeekBar(setTipPercent: (newVal: Int) -> Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var sliderPosition by
        remember { mutableStateOf(0f) }
        val percent = sliderPosition.toInt().toString() + "%"
        Text(text = percent, style= TextStyle(fontSize = 28.sp))
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                setTipPercent(it.toInt())
                            },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            steps = 5,
            colors = SliderDefaults.colors(
                thumbColor = Purple700,
                activeTrackColor = Purple700,
                inactiveTrackColor = Pink
            )
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MainContent()
    }
}