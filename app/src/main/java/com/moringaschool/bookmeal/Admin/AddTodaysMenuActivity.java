package com.moringaschool.bookmeal.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.bookmeal.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AddTodaysMenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button mDatePickerbtn;
    TextView menu_list;
    //TextInputLayout selectedDate;
    boolean[] selectFood;
    ArrayList<Integer> foodList = new ArrayList<>();
    String[] dayArray = {"M", "T", "M", "T", "M", "T"};


    TextInputEditText selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todays_menu);
        mDatePickerbtn = findViewById(R.id.datepickerbtn);
        // selectedDate=findViewById(R.id.selectedDate);
        menu_list = findViewById(R.id.menu_list);
        selectedDate = findViewById(R.id.selectedDate);
        mDatePickerbtn.setOnClickListener(this);

        selectFood = new boolean[dayArray.length];
        menu_list.setOnClickListener(this);
        // mDatePickerbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}