package com.example.amenite.PROFILE;

import com.example.amenite.DBRes.DBresources;

public class User {
        public static String Emailid;
        public static String password;
        public static String Role;
        public static String Phonenumber;
        public static String UserID;
        public static String Username;
        public static boolean currentUser()
        {
                DBresources dBresources = new DBresources();
                if(dBresources.firebaseAuth.getCurrentUser()!=null)
                {
                        return true;
                }
                else
                        return false;
        }
}
