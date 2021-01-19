package com.example.calculatorcalorii;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AddFragment extends Fragment {
    private SQLiteDatabase caloriesDB;

    Spinner daysSpinner;
    Spinner monthsSpinner;
    Spinner yearsSpinner;
    EditText stepsField;
    EditText weightField;
    Button confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        CaloriesHelper dbHelper = new CaloriesHelper(getActivity());
        caloriesDB = dbHelper.getWritableDatabase();

        daysSpinner = rootView.findViewById(R.id.ziField);
        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.days_array, android.R.layout.simple_spinner_item);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(daysAdapter);

        monthsSpinner = rootView.findViewById(R.id.lunaField);
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.months_array, android.R.layout.simple_spinner_item);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(monthsAdapter);
        
        yearsSpinner = rootView.findViewById(R.id.anField);
        ArrayAdapter<CharSequence> yearsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.years_array, android.R.layout.simple_spinner_item);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsSpinner.setAdapter(yearsAdapter);

        stepsField = rootView.findViewById(R.id.pasiField);
        weightField = rootView.findViewById(R.id.greutateField);

        confirm = rootView.findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDB();
            }
        });

        return rootView;
    }

    public void addToDB(){
        final long userID = getArguments().getLong("id");
        final String date = daysSpinner.getSelectedItem().toString() + " "
                + monthsSpinner.getSelectedItem().toString() + " "
                + yearsSpinner.getSelectedItem().toString();
        final int steps = Integer.parseInt(stepsField.getText().toString());
        final float weight = Float.parseFloat(weightField.getText().toString());
        //Calorii = 0.0175 x MET(3.5 pentru mers) x greutate (kg) x pasi/100
        final float calories = (float) (0.0175 * 3.5 * weight * steps / 100);

        new AlertDialog.Builder(getActivity())
                .setTitle("Atentie")
                .setMessage("Doriti sa adaugati aceasta inregistrare?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ContentValues cv = new ContentValues();
                        cv.put(CaloriesContract.DaysEntry.COLUMN_DATE, date);
                        cv.put(CaloriesContract.DaysEntry.COLUMN_STEPS, steps);
                        cv.put(CaloriesContract.DaysEntry.COLUMN_WEIGHT, weight);
                        cv.put(CaloriesContract.DaysEntry.COLUMN_CALORIES, calories);
                        cv.put(CaloriesContract.DaysEntry.COLUMN_USER_ID, userID);
                        caloriesDB.insert(CaloriesContract.DaysEntry.TABLE_NAME, null, cv);

                        Toast.makeText(getActivity(), "Inregistrarea a fost adaugata cu success", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton("NU", null).show();
    }
}
