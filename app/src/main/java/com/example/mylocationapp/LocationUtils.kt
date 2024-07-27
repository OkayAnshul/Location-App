package com.example.mylocationapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class LocationUtils(val context:Context) {

    //Fn to check the current permissionState

    fun hasLocationPermission(context: Context):Boolean{
        if(
            (ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED
                    )
            &&
            (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED
                    )
            )
            return true
        else
            return false
    }
}