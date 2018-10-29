package com.example.morte.bennyapp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public static void export () {
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "BennyStats.xls";

        File directory = new File(sd.getAbsolutePath());

        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale(Locale.ENGLISH.getLanguage(), Locale.ENGLISH.getCountry()));

            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            //Excel sheetA first sheetA
            WritableSheet sheetA = workbook.createSheet("sheet A", 0);

            // column and row titles
            sheetA.addCell(new Label(0, 0, "sheet A 1"));
            sheetA.addCell(new Label(1, 0, "Sheet A 2"));
            sheetA.addCell(new Label(0, 1, "Sheet A 3"));
            sheetA.addCell(new Label(1, 1, "Sheet A 4"));


            // close workbook
            workbook.write();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void click (View view) {

        // Husk disse lines et sted hvor det giver mening
        // permissionForExcelExport(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST);
        // permissionForExcelExport(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST);
        // export();

    }


}
