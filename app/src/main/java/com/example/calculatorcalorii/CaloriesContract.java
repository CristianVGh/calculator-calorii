package com.example.calculatorcalorii;

import android.provider.BaseColumns;

public class CaloriesContract {
    public static final class UsersEntry implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static final class DaysEntry implements BaseColumns{
        public static final String TABLE_NAME = "days";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STEPS = "steps";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_USER_ID = "userID";
    }
}
