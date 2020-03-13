package com.example.missiond;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DataBaseHelper {
    private static DataBaseHelper instance = null;
    private final FirebaseFirestore db;
    private static final String TAG = "DataBaseHelper";
    private Driver tempDriver;
    private Rider tempRider;
    private List<Order> list = new ArrayList<>();
    private List<Driver> list_driver = new ArrayList<>();

    private DataBaseHelper() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        db.collection("Rider");
        db.collection("Orders");
        db.collection("Driver");
    }

    public static DataBaseHelper getInstance() {
        if (instance == null) {
            instance = new DataBaseHelper();
        }

        return instance;
    }

    public DocumentReference AddRider(Rider rider) {
        DocumentReference userReference = db.collection("Rider").document(rider.getUserName());
        userReference.set(rider);
        return userReference;
    }

    public DocumentReference AddDriver(Driver driver) {
        DocumentReference userReference = db.collection("Driver").document(driver.getUserName());
        userReference.set(driver);
        return userReference;
    }

    public void UpdateUserData(User user, Boolean isRider) {
        String collection = "Driver";
        if (isRider) {
            collection = "Rider";
        }
        db.collection(collection).document(user.getUserName())
                .update("capital", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void DeleteUser(String userName, Boolean isRider) {
        String collection = "Driver";
        if (isRider) {
            collection = "Rider";
        }

        db.collection(collection).document(userName).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public Rider getRider(String userName) {
        DocumentReference docRef = db.collection("Rider").document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tempRider = documentSnapshot.toObject(Rider.class);
            }
        });
        return tempRider;
    }

    public Driver getDriver(String userName) {
        String collection = "Driver";
        DocumentReference docRef = db.collection(collection).document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tempDriver = documentSnapshot.toObject(Driver.class);
            }
        });
        return tempDriver;
    }

    public Boolean userExist(String userName, Boolean isRider) {
        return true;
    }

    public void addOrder(Order order, String userName) {
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("userName", userName);
        orderData.put("order", order);
        db.collection("Orders").add(orderData);
    }

    public List<Order> GetUserOrders(String userName) {
        db.collection("Orders")
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list.add(document.toObject(Order.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return list;
    }

    public List<Driver> getDrivers() {
        db.collection("Drivers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list_driver.add(document.toObject(Driver.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return list_driver;
    }

    public List<Order> getAllOrders() {
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                list.add(document.toObject(Order.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return list;
    }
}
