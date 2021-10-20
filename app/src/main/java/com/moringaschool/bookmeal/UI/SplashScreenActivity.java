package com.moringaschool.bookmeal.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.bookmeal.Authentication.RegisterActivity;
import com.moringaschool.bookmeal.R;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(this)){
            showCustomDialog();
        }

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),NoConnectionActivity.class));
                        finish();
                    }
                });
    }

    private boolean isConnected(SplashScreenActivity splashScreenActivity) {
        ConnectivityManager connectivityManager=(ConnectivityManager) splashScreenActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiConn !=null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

}