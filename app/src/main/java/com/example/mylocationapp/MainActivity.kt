package com.example.mylocationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
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
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylocationapp.ui.theme.MyLocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context= LocalContext.current
            val viewModel:LocationViewModel =viewModel()
            MyLocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(Modifier.padding(innerPadding),context,viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier,context: Context,
          viewModel: LocationViewModel) {
    val locationUtils=LocationUtils(context)
    LocationDisplay(context = context,locationUtils,viewModel)
}
@Composable
fun LocationDisplay(context:Context,
                    locationUtils: LocationUtils,
viewModel: LocationViewModel){

    val permission= arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    val requestPermissionLauncher= rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGrantedPermission ->
            if(isGrantedPermission[Manifest.permission.ACCESS_FINE_LOCATION]==true &&
                isGrantedPermission[Manifest.permission.ACCESS_COARSE_LOCATION]==true)
            {//I have Access to location
                locationUtils.requestLocationUpdates(viewModel)
            }
            else
            {
                val rationaleRequired=ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                if(rationaleRequired)
                    Toast.makeText(context,"Please Allow to proceed",Toast.LENGTH_LONG).show()
                else
                {
                    Toast.makeText(context,"Please allow to proceed in the android settings",Toast.LENGTH_LONG).show()
                }
            }


        }
    )

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Address: ${viewModel.location.value?.latitude} ${viewModel.location.value?.longitude} "

        //        "\n $address"
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Button(onClick = {
            if(locationUtils.hasLocationPermission(context))
            {
                //permission already granted
                locationUtils.requestLocationUpdates(viewModel)
            }
            else
            requestPermissionLauncher.launch(permission)
        }) {
            Text(text = "Get Location")
            
        }
    }
}