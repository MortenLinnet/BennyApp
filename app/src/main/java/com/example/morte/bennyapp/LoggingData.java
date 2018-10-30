package com.example.morte.bennyapp;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class LoggingData extends AppCompatActivity {

    private TextView request1textview;
    private TextView request2textview;
    private TextView feedback1textview;
    private TextView feedback2textview;


    private Button request1button;
    private Button request2button;
    private Button feedback1button;
    private Button feedback2button;
    private Button newDay;
    private Button reset;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_data);

        request1textview = (TextView) findViewById(R.id.request1textview);
        request2textview = (TextView) findViewById(R.id.request2textview);
        feedback1textview = (TextView) findViewById(R.id.feedback1textview);
        feedback2textview = (TextView) findViewById(R.id.feedback2textview);
    }




}
