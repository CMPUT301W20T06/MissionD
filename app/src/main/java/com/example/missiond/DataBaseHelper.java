package com.example.missiond;

import android.util.Log;

import androidx.annotation.Keep;
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
import com.google.firebase.firestore.SetOptions;

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
 * @author
 * Yifei Ma
 */
public class DataBaseHelper {
    private static DataBaseHelper instance = null;
    private final FirebaseFirestore db;
    private static final String TAG = "DataBaseHelper";
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
     * This update Driver class to firestore database
     */
    public void UpdateDriverData(Driver driver) {
        String collection = "Driver";
        db.collection(collection).document(driver.getUserName())
                .set(driver)
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
     * This update Rider class to firestore database
     */
    public void UpdateRiderData(Rider rider) {
        String collection = "Rider";
        db.collection(collection).document(rider.getUserName())
                .set(rider)
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
    public void getRider(String userName, final Consumer<Rider> consumer) {
        DocumentReference docRef = db.collection("Rider").document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Rider tempRider = documentSnapshot.toObject(Rider.class);
                consumer.accept(tempRider);
            }
        });
    }

    /**
     * This method get the driver class by its username
     * @return
     *  Return Driver object
     */
    public void getDriver(String userName, final Consumer<Driver> consumer) {
        DocumentReference docRef = db.collection("Driver").document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver tempDriver = documentSnapshot.toObject(Driver.class);
                consumer.accept(tempDriver);
            }
        });
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
    public String addOrder(Order order) {
//        Map<String, Object> orderData = new HashMap<>();
//        orderData.put("userName", userName);
//        orderData.put("order", order);
//        db.collection("Orders").add(order);
        DocumentReference documentReference = db.collection("Orders").document();
        String id = documentReference.getId();
        order.setId(id);
        documentReference.set(order);
        return id;
    }

    /**
     * This method update order in database
     */
    public void updateOrder(Order order) {
        String id = order.getId();
        db.collection("Orders").document(id).set(order);
    }


    /**
     * This method get order in database by it's id
     */
    @Keep
    public void getOrderById(String id, final Consumer<Order> consumer) {
        DocumentReference docRef = db.collection("Orders").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Order tempOrder = documentSnapshot.toObject(Order.class);
                consumer.accept(tempOrder);
            }
        });
    }

    /**
     * This method get the all orders of a specific order
     * @return
     *  Return a list of orders
     */
    @Keep
    public void GetUserOrders(String userName, final Consumer<List<Order>> consumer) {
        db.collection("Orders")
                .whereEqualTo("userName", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Order> orders = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                orders.add(document.toObject(Order.class));
                            }
                            consumer.accept(orders);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * This method get all drivers in database
     * @return
     *  Return a list of drivers
     */

    public void getDrivers(final Consumer<List<Driver>> consumer) {
        db.collection("Drivers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Driver> drivers = new ArrayList<>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                drivers.add(document.toObject(Driver.class));
                            }
                            consumer.accept(drivers);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * This method get all orders in database
     * @return
     *  Return a list of orders
     */
    @Keep
    public void getAllOrders(final Consumer<List<Order>> consumer) {
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Order> orders = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                orders.add(document.toObject(Order.class));
                            }
                            Log.d(TAG,"the size of list is "+ String.valueOf(orders.size()));
                            consumer.accept(orders);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
