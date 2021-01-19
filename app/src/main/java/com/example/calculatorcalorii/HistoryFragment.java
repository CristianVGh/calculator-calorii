package com.example.calculatorcalorii;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private SQLiteDatabase caloriesDB;
    HistoryAdapter adapter;
    RecyclerView recyclerView;

    List<Day> days = new ArrayList<>();
    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        username = getArguments().getString("user");
        recyclerView = rootView.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //nu face nimic la mutare
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // la swipe sterge un item din DB
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = Long.parseLong(viewHolder.itemView.getTag().toString());
                removeDay(id);
                days.remove(id);
                adapter.notifyItemRemoved((int)id);
            }
        }).attachToRecyclerView(recyclerView);

        CaloriesHelper dbHelper = new CaloriesHelper(getActivity());
        caloriesDB = dbHelper.getWritableDatabase();

        days = getAllDays();
        adapter = new HistoryAdapter(getActivity(), days);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private List<Day> getAllDays(){
        String[] values = {username};
        Cursor cursor = caloriesDB.query(
                CaloriesContract.UsersEntry.TABLE_NAME + " , " + CaloriesContract.DaysEntry.TABLE_NAME,
                null,
                CaloriesContract.DaysEntry.COLUMN_USER_ID + " = " + CaloriesContract.UsersEntry.TABLE_NAME +
                        "." + CaloriesContract.UsersEntry._ID + " AND " + CaloriesContract.UsersEntry.COLUMN_USERNAME + " = ?",
                values,
                null,
                null,
                CaloriesContract.DaysEntry._ID
        );

        while(cursor.moveToNext()){
            long idd = cursor.getLong(cursor.getColumnIndex(CaloriesContract.DaysEntry._ID));
            String date = cursor.getString(cursor.getColumnIndex(CaloriesContract.DaysEntry.COLUMN_DATE));
            int steps = cursor.getInt(cursor.getColumnIndex(CaloriesContract.DaysEntry.COLUMN_STEPS));
            float weight = cursor.getFloat(cursor.getColumnIndex(CaloriesContract.DaysEntry.COLUMN_WEIGHT));
            float calories = cursor.getFloat(cursor.getColumnIndex(CaloriesContract.DaysEntry.COLUMN_CALORIES));
            Day day = new Day(idd, date, steps, weight, calories);
            days.add(day);
        }

        return days;
    }

    public boolean removeDay(long id){
        return caloriesDB.delete(CaloriesContract.DaysEntry.TABLE_NAME, CaloriesContract.DaysEntry._ID + "=" + id, null) > 0;
    }
}
