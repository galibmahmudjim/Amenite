package com.example.amenite.PROFILE;

import android.util.Log;

import com.example.amenite.DBRes.DBresources;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

public class User {
    private static final String TAG = "Amenite_check";
    public static String Emailid;
    public static String password;
    public static String Role;
    public static String Phonenumber;
    public static String UserID;
    public static String Username;
    public static String Fullname;
    public static String Gender;
    public static String Address;
    public static String Longitude;
    public static String latitude;
    public static String Date_of_Birth;

    public User() {
        RetriveData();
    }

    public static Task RetriveData() {
        DBresources dBresources = new DBresources();
        Task t1 = dBresources.database.collection("User").document(dBresources.firebaseUser.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.get("Email")!=null)
                                setEmailid(documentSnapshot.get("Email").toString());
                            else
                                setEmailid(" ");
                            if (documentSnapshot.get("Name")!=null)
                                setFullname(documentSnapshot.get("Name").toString());
                            else
                                setFullname(" ");
                            if (documentSnapshot.get("Username")!=null)
                                setUsername((documentSnapshot.get("Username").toString()));
                            else
                                setUsername(" ");
                            if (documentSnapshot.get("Address")!=null)
                                    setAddress(documentSnapshot.get("Address").toString());
                            else
                                setAddress(" ");
                            if (documentSnapshot.get("Gender")!=null)
                                setGender(documentSnapshot.get("Gender").toString());
                            else
                                setGender(" ");
                            if (documentSnapshot.get("Date_of_Birth")!=null)
                                setDate_of_Birth(documentSnapshot.get("Date_of_Birth").toString());
                            else
                                setDate_of_Birth(" ");
                            if (documentSnapshot.get("Password")!=null)
                                setPassword(documentSnapshot.get("Password").toString());
                            if (documentSnapshot.get("Role")!=null)
                                setRole(documentSnapshot.get("Role").toString());
                            setUserID(documentSnapshot.getId().toString());
                            if (documentSnapshot.get("Phone_Number")!=null)
                                setPhonenumber(documentSnapshot.get("Phone_Number").toString());
                            else
                                setPhonenumber(" ");
                        }
                    }
                });
        return t1;
    }

    public static boolean currentUser() {
        DBresources dBresources = new DBresources();
        if (dBresources.firebaseAuth.getCurrentUser() != null) {
            return true;
        } else
            return false;
    }

    public static String getEmailid() {
        return Emailid;
    }

    public static void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getRole() {
        return Role;
    }

    public static void setRole(String role) {
        Role = role;
    }

    public static String getPhonenumber() {
        return Phonenumber;
    }

    public static void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getFullname() {
        return Fullname;
    }

    public static void setFullname(String fullname) {
        Fullname = fullname;
    }

    public static String getGender() {
        return Gender;
    }

    public static void setGender(String gender) {
        Gender = gender;
    }

    public static String getAddress() {
        return Address;
    }

    public static void setAddress(String address) {
        Address = address;
    }

    public static String getLongitude() {
        return Longitude;
    }

    public static void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        User.latitude = latitude;
    }

    public static String getDate_of_Birth() {
        return Date_of_Birth;
    }

    public static void setDate_of_Birth(String date_of_Birth) {
        Date_of_Birth = date_of_Birth;
    }
}
