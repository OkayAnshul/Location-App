package com.example.mylocationapp

import android.content.Context
import android.os.Bundle
import android.Manifest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.mylocationapp.ui.theme.MyLocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context= LocalContext.current
            MyLocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(Modifier.padding(innerPadding),context)
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier,context: Context) {
    LocationDisplay(context = context)
}
@Composable
fun LocationDisplay(context:Context){

    val permission= arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    val requestPermissionLauncher= rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGrantedPermission ->
            if(isGrantedPermission[Manifest.permission.ACCESS_FINE_LOCATION]==true &&
                isGrantedPermission[Manifest.permission.ACCESS_COARSE_LOCATION]==true)
            {//I have Access to location
            }
            else
            {
                val rationaleRequired=ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                if(rationaleRequired)
                    Toast.makeText(context,"1",Toast.LENGTH_LONG).show()
                else
                {
                    Toast.makeText(context,"2",Toast.LENGTH_LONG).show()
                }
            }


        }
    )

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "No Location")
        Spacer(modifier = Modifier.padding(2.dp))
        Button(onClick = {
            requestPermissionLauncher.launch(permission)
        }) {
            Text(text = "Get Location")
            
        }
    }
}