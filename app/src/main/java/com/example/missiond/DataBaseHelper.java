package com.example.missiond;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

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
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * This is a helper class that make use of firestore to manage data
 */
public class DataBaseHelper {
    private static DataBaseHelper instance = null;
    private final FirebaseFirestore db;
    private static final String TAG = "DataBaseHelper";
    private Driver tempDriver;
    private Rider tempRider;
    private List<Order> list = new ArrayList<>();
    private List<Driver> list_driver = new ArrayList<>();
    private boolean isEmpty;

    /**
     * This is the constructor method that fulfills singleton class design
     */
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

    /**
     * This get the instance of DatabaseHelper class
     * @return
     *  Return DatabaseHelper instance
     */
    public static DataBaseHelper getInstance() {
        if (instance == null) {
            instance = new DataBaseHelper();
        }

        return instance;
    }

    /**
     * This stores rider class to firestore database
     * @return
     *  Return DocumentReference instance of that rider
     */
    public DocumentReference AddRider(Rider rider) {
        DocumentReference userReference = db.collection("Rider").document(rider.getUserName());
        userReference.set(rider);
        return userReference;
    }

    /**
     * This stores Driver class to firestore database
     * @return
     *  Return DocumentReference instance of that driver
     */
    public DocumentReference AddDriver(Driver driver) {
        DocumentReference userReference = db.collection("Driver").document(driver.getUserName());
        userReference.set(driver);
        return userReference;
    }

    /**
     * This update user class to firestore database
     */
    public void UpdateDriverData(Driver driver) {
        String collection = "Driver";
        db.collection(collection).document(driver.getUserName())
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

    public void UpdateRiderData(Rider rider) {
        String collection = "Rider";
        db.collection(collection).document(rider.getUserName())
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

    /**
     * This delete class that is stored in firestore database
     */
    public void DeleteUser(String userName, boolean isRider) {
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

    /**
     * This method get the rider class by its username
     * @return
     *  Return rider object
     */
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

    /**
     * This method get the driver class by its username
     * @return
     *  Return Driver object
     */
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

    /**
     * This method checks if a user exist in database
     * @return
     *  Return boolean
     */
    public void userExist(String userName, boolean isRider, final Consumer<Boolean> callback) {
        String collection = "Driver";
        if (isRider) {
            collection = "Rider";
        }

        DocumentReference docIdRef = db.collection(collection).document(userName);

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        isEmpty = true;
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        isEmpty = false;
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
                callback.accept(isEmpty);
            }
        });
    }

    /**
     * This method add order in database
     */
    public void addOrder(Order order) {
//        Map<String, Object> orderData = new HashMap<>();
//        orderData.put("userName", userName);
//        orderData.put("order", order);
        db.collection("Orders").add(order);
    }

    /**
     * This method get the all orders of a specific order
     * @return
     *  Return a list of orders
     */
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

    /**
     * This method get all drivers in database
     * @return
     *  Return a list of drivers
     */
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

    /**
     * This method get all orders in database
     * @return
     *  Return a list of orders
     */
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
