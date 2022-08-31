package com.example.amenite.DBRes;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.amenite.PROFILE.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DBresources {
    private static final String TAG = "DBResources";
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://amenite-default-rtdb.firebaseio.com/");
    public FirebaseFirestore database = FirebaseFirestore.getInstance();
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public Boolean Signup(Map<String,String> userDetails)
    {
        User user = new User();
        final int[] flag = {0};
        DocumentReference documentReference = database.collection("User").document(user.UserID);
        Task t1 = documentReference.set(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    flag[0] = 1;
                }
                else
                {
                    flag[0] = 0;
                }
            }
        });
        if(Tasks.whenAllComplete(t1).isSuccessful())
        {
            Log.d(TAG, "Signup: added "+flag[0]);
            if(flag[0]==1)
            return true;
            else
                return false;
        }
        else
            return false;

    }

}
