package com.example.morte.bennyapp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * MORTEN don't look further!
 */

public class NilanTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilan_test);


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

        // NilanTestActivity.export();
        export();
    }
}
