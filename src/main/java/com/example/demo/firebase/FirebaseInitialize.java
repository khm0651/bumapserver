package com.example.demo.firebase;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;



@Service
public class FirebaseInitialize {
	
    private static final String FIREBASE_CONFIG_PATH = "./firebaseServiceAccount.json";
    private static final String FIREBASE_DATABASE_PATH = "https://bumap-fe835.firebaseio.com";

    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(FIREBASE_CONFIG_PATH);

            FirebaseOptions options = new FirebaseOptions.Builder()
            		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            		  .setDatabaseUrl(FIREBASE_DATABASE_PATH)
            		  .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
