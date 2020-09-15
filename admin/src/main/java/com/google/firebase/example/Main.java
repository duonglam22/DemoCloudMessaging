/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void getServiceAccountAccessToken() throws IOException {
        // https://firebase.google.com/docs/reference/dynamic-links/analytics#api_authorization
        // [START get_service_account_tokens]
        System.out.println("get service account access token");
        FileInputStream serviceAccount = new FileInputStream("/home/lamdn/Downloads/MessageCloudExample-7d0bdd0d8227.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        credentials.refresh();
        String accessToken = credentials.getAccessToken().getTokenValue();
        long expirationTime = credentials.getAccessToken().getExpirationTime().getTime();

        System.out.printf("access token: " + accessToken);
        System.out.println("expiration time: " + expirationTime);
        // Attach accessToken to HTTPS request in the
        //   "Authorization: Bearer" header
        // After expirationTime, you must generate a new access
        //   token
        // [END get_service_account_tokens]
    }

    public static void main(String[] args) {
        // write your code here
        logger.info("application start!");

        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("/home/lamdn/Downloads/MessageCloudExample-7d0bdd0d8227.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://messagecloudexample.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);

        //test
        Process process = new Process();

        System.out.println("input number of msg: ");
        int numberOfMsg;
        Scanner scanner = new Scanner(System.in);
        numberOfMsg = scanner.nextInt();
        process.fillQueue(numberOfMsg);
        process.start();

        //test
//        FirebaseMessagingSnippets fbm = new FirebaseMessagingSnippets();
//        try {
//            for(int i = 1; i <= 10; ++i) {
//                logger.info("send msg index: " + i);
////                fbm.sendAll();
//                fbm.sendAllAsync();
//
////                fbm.sendToTopic();
////                fbm.sendTopicAsync();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
