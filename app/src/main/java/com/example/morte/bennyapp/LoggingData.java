package com.example.morte.bennyapp;


import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StreamCorruptedException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import jxl.demo.CSV;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    public String subFolder = "/LogData";
    public String filename = "LogFile.csv";
    
    public String csv = ".csv";
    private static final String TAG = "MEDIA";


    String eol = System.getProperty("line.separator");



    public HashMap<String, Integer> LogHashmap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_data);

        request1textview = (TextView) findViewById(R.id.request1textview);
        request2textview = (TextView) findViewById(R.id.request2textview);
        feedback1textview = (TextView) findViewById(R.id.feedback1textview);
        feedback2textview = (TextView) findViewById(R.id.feedback2textview);

        request1button = (Button) findViewById(R.id.button);
        feedback2button = (Button) findViewById(R.id.button2);
        request2button = (Button) findViewById(R.id.button5);
        feedback2button = (Button) findViewById(R.id.button6);

        readFile();
    }

    public void requestOne (View v) {
        save("Request1");
    }

    public void requestTwo (View v) {
        save("Request2");
    }

    public void save(String key) {

        if (LogHashmap.containsKey(key)) {
            LogHashmap.put(key, LogHashmap.get(key)+1);
            writeToFile();
        }

        else if (!LogHashmap.containsKey(key)) {
            LogHashmap.put(key, 1);
            writeToFile();
        }

        if (key == "Request1") {
            request1textview.setText(String.valueOf(LogHashmap.get(key)));
            Toast.makeText(this, "Request1 value is: " + LogHashmap.get(key), Toast.LENGTH_SHORT).show();
        }

        if (key == "Request2") {
            request2textview.setText(String.valueOf(LogHashmap.get(key)));
            Toast.makeText(this, "Request2 value is: " + LogHashmap.get(key), Toast.LENGTH_SHORT).show();
        }
    }

    

    // Creates .ser file - Not that useful
    public void writeToFile() {
        //write to file

        File cacheDir = null;
        File appDirectory = null;

        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File(cacheDir + subFolder);
        }

        else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File(BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists()) {
            appDirectory.mkdirs();
        }

        File file = new File(appDirectory, filename);

        FileOutputStream fos = null;
        ObjectOutputStream out = null;



        try (Writer writer = new FileWriter(file)) {
            for (Map.Entry<String, Integer> entry : LogHashmap.entrySet()) {
                writer.append(entry.getKey()).append(',').append(String.valueOf(entry.getValue())).append(eol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }







        /**try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(LogHashmap);
            System.out.println(LogHashmap);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                }
            } catch (Exception e) {

            }
        }**/
    }

    

    public void readFile() {

        File cacheDir = null;
        File appDirectory = null;

        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File (cacheDir + subFolder);
        } else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File (BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists()) {
            return;
        }

        File file = new File (appDirectory, filename);

        FileInputStream fis = null;
        ObjectInputStream in = null;



        try {
            BufferedReader br = new BufferedReader(new FileReader(appDirectory + "/" + filename));
            String line = "";
            StringTokenizer st = null;


            while ((line = br.readLine()) != null) {

                st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {

                    String key = st.nextToken();
                    String value = st.nextToken();

                    LogHashmap.put(key, Integer.valueOf(value));
                }
            }
        }

        /**try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            LogHashmap  = (HashMap<String, Integer>) in.readObject();
            Toast.makeText(this, "Count of hashmaps:: " + LogHashmap.size() + " " + LogHashmap, Toast.LENGTH_SHORT).show();


        }**/ catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }/** catch (ClassNotFoundException e) {
            e.printStackTrace();
        }**/ catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, ""+ appDirectory, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Complete hashmap: " + LogHashmap, Toast.LENGTH_SHORT).show();
    }



    public void reset (View v) {
        LogHashmap.clear();
        writeToFile();
    }

    public void newDay (View v) {

    }


    
}