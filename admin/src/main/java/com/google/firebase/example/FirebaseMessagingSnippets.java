/*
 * Copyright 2018 Google Inc. All Rights Reserved.
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

import com.google.firebase.messaging.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class FirebaseMessagingSnippets {
  private static final Logger logger = Logger.getLogger(FirebaseMessagingSnippets.class.getName());
  public void sendToToken() throws Exception {
    // [START send_to_token]
    // This registration token comes from the client FCM SDKs.
    String registrationToken = "97098250087:android:70260adf103908ba79e1c2";

    // See documentation on defining a message payload.
    Message message = Message.builder()
        .putData("score", "850")
        .putData("time", "2:45")
        .setToken(registrationToken)
        .build();

    // Send a message to the device corresponding to the provided
    // registration token.
    String response = FirebaseMessaging.getInstance().send(message);
    // Response is a message ID string.
    System.out.println("Successfully sent message: " + response);
    // [END send_to_token]
  }

  public void sendToTopic() throws Exception {
    // [START send_to_topic]
    // The topic name can be optionally prefixed with "/topics/".
    String topic = "highScores";
    logger.info("send message to topic: " + topic);

    // See documentation on defining a message payload.
    Message message = Message.builder()
        .putData("score", "850")
        .putData("time", "2:45")
        .setTopic(topic)
        .build();

    // Send a message to the devices subscribed to the provided topic.
    String response = FirebaseMessaging.getInstance().send(message);
//    String test = FirebaseMessaging.getInstance().sendAllAsync().get();
    // Response is a message ID string.
    logger.info("Successfully sent message: " + response);
    // [END send_to_topic]
  }

  public void sendTopicAsync() throws Exception {
    // [START send_to_topic]
    // The topic name can be optionally prefixed with "/topics/".
    String topic = "highScores";
    logger.info("send message to topic: " + topic);

    // See documentation on defining a message payload.
    Message message = Message.builder()
            .putData("score", "850")
            .putData("time", "2:45")
            .setTopic(topic)
            .build();

    // Send a message to the devices subscribed to the provided topic.
    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
//    String test = FirebaseMessaging.getInstance().sendAllAsync().get();
    // Response is a message ID string.
    logger.info("Successfully sent message: " + response);
    // [END send_to_topic]
  }

  public void sendAll() throws FirebaseMessagingException {
    String registrationToken = "YOUR_REGISTRATION_TOKEN";

    // [START send_all]
    // Create a list containing up to 500 messages.
    String topic = "sales";
//    List<Message> messages = Arrays.asList(
//            Message.builder()
//                    .setNotification(Notification.builder()
//                            .setTitle("Price drop")
//                            .setBody("5% off all electronics")
//                            .build())
//                    .setToken(registrationToken)
//                    .build(),
//            // ...
//            Message.builder()
//                    .setNotification(Notification.builder()
//                            .setTitle("Price drop")
//                            .setBody("2% off all books")
//                            .build())
//                    .setTopic("readers-club")
//                    .build()
//    );

    List<Message> messages = new ArrayList<>();
    for(int i = 1; i < 500; ++i) {
      Message msg = Message.builder()
              .setNotification(Notification.builder()
                      .setTitle("Price drop " + i)
                      .setBody("5% off all electronics " + i)
                      .build())
              .setTopic(topic)
              .build();
      messages.add(msg);
    }
    logger.info("total msg prepare send: " + messages.size());
    BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
    // See the BatchResponse reference documentation
    // for the contents of response.
    logger.info(response.getSuccessCount() + " list messages were sent successfully");
    // [END send_all]
  }

  public void sendAllAsync() throws Exception {
    logger.info("send list msg: ");
    String registrationToken = "YOUR_REGISTRATION_TOKEN";

    // [START send_all]
    // Create a list containing up to 500 messages.
    String topic = "sales";
    List<Message> messages = new ArrayList<>();
    for(int i = 1; i < 500; ++i) {
      Message msg = Message.builder()
              .setNotification(Notification.builder()
                      .setTitle("Price drop " + i)
                      .setBody("5% off all electronics " + i)
                      .build())
              .setTopic(topic)
              .build();
      messages.add(msg);
    }
    logger.info("total msg prepare send: " + messages.size());
    BatchResponse response = FirebaseMessaging.getInstance().sendAllAsync(messages).get();
    // See the BatchResponse reference documentation
    // for the contents of response.
    logger.info(response.getSuccessCount() + " list messages were sent successfully");
    // [END send_all]
  }

  public void sendAll(List<Message> messages) throws FirebaseMessagingException {
    logger.info("total msg prepare send: " + messages.size());
    BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
    // See the BatchResponse reference documentation
    // for the contents of response.
    logger.info(response.getSuccessCount() + " list messages were sent successfully");
  }

  public void sendAllAsync(List<Message> messages) throws Exception {
    logger.info("total msg prepare send: " + messages.size());
    BatchResponse response = FirebaseMessaging.getInstance().sendAllAsync(messages).get();
    // See the BatchResponse reference documentation
    // for the contents of response.
    logger.info(response.getSuccessCount() + " list messages were sent successfully");
    // [END send_all]
  }

  public void sendMulticast() throws FirebaseMessagingException {
    // [START send_multicast]
    // Create a list containing up to 100 registration tokens.
    // These registration tokens come from the client FCM SDKs.
    List<String> registrationTokens = Arrays.asList(
            "YOUR_REGISTRATION_TOKEN_1",
            // ...
            "YOUR_REGISTRATION_TOKEN_n"
    );

    MulticastMessage message = MulticastMessage.builder()
            .putData("score", "850")
            .putData("time", "2:45")
            .addAllTokens(registrationTokens)
            .build();
    BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
    // See the BatchResponse reference documentation
    // for the contents of response.
    System.out.println(response.getSuccessCount() + " messages were sent successfully");
    // [END send_multicast]
  }

  public void sendToCondition() throws Exception {
    // [START send_to_condition]
    // Define a condition which will send to devices which are subscribed
    // to either the Google stock or the tech industry topics.
    String condition = "'stock-GOOG' in topics || 'industry-tech' in topics";

    // See documentation on defining a message payload.
//    Message message = Message.builder()
//        .setNotification(new Notification(
//            "$GOOG up 1.43% on the day",
//            "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day."))
//        .setCondition(condition)
//        .build();

    Message message = Message.builder()
            .setNotification(Notification.builder()
                    .setTitle("$GOOG up 1.43% on the day")
                    .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                    .build())
            .setCondition(condition)
            .build();

    // Send a message to devices subscribed to the combination of topics
    // specified by the provided condition.
    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    // Response is a message ID string.
    logger.info("Successfully sent message: " + response);
    // [END send_to_condition]
  }

  public void sendDryRun() throws Exception {
    Message message = Message.builder()
        .putData("score", "850")
        .putData("time", "2:45")
        .setToken("token")
        .build();

    // [START send_dry_run]
    // Send a message in the dry run mode.
    boolean dryRun = true;
    String response = FirebaseMessaging.getInstance().sendAsync(message, dryRun).get();
    // Response is a message ID string.
    logger.info("Dry run successful: " + response);
    // [END send_dry_run]
  }

  public Message androidMessage() {
    // [START android_message]
    Message message = Message.builder()
        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000) // 1 hour in milliseconds
            .setPriority(AndroidConfig.Priority.NORMAL)
            .setNotification(AndroidNotification.builder()
                .setTitle("$GOOG up 1.43% on the day")
                .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                .setIcon("stock_ticker_update")
                .setColor("#f45342")
                .build())
            .build())
        .setTopic("industry-tech")
        .build();
    // [END android_message]
    return message;
  }

  public Message apnsMessage() {
    // [START apns_message]
    Message message = Message.builder()
        .setApnsConfig(ApnsConfig.builder()
            .putHeader("apns-priority", "10")
            .setAps(Aps.builder()
                .setAlert(ApsAlert.builder()
                    .setTitle("$GOOG up 1.43% on the day")
                    .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                    .build())
                .setBadge(42)
                .build())
            .build())
        .setTopic("industry-tech")
        .build();
    // [END apns_message]
    return message;
  }

  public Message webpushMessage() {
    // [START webpush_message]
    Message message = Message.builder()
        .setWebpushConfig(WebpushConfig.builder()
            .setNotification(new WebpushNotification(
                "$GOOG up 1.43% on the day",
                "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.",
                "https://my-server/icon.png"))
            .build())
        .setTopic("industry-tech")
        .build();
    // [END webpush_message]
    return message;
  }

  public Message allPlatformsMessage() {
    // [START multi_platforms_message]
    Message message = Message.builder()
//        .setNotification(new Notification(
//            "$GOOG up 1.43% on the day",
//            "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day."))
        .setNotification(Notification.builder()
                .setTitle("$GOOG up 1.43% on the day")
                .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                .build())

        .setAndroidConfig(AndroidConfig.builder()
            .setTtl(3600 * 1000)
            .setNotification(AndroidNotification.builder()
                .setIcon("stock_ticker_update")
                .setColor("#f45342")
                .build())
            .build())
        .setApnsConfig(ApnsConfig.builder()
            .setAps(Aps.builder()
                .setBadge(42)
                .build())
            .build())
        .setTopic("industry-tech")
        .build();
    // [END multi_platforms_message]
    return message;
  }

  public void subscribeToTopic() throws Exception {
    String topic = "highScores";
    // [START subscribe]
    // These registration tokens come from the client FCM SDKs.
    List<String> registrationTokens = Arrays.asList(
        "YOUR_REGISTRATION_TOKEN_1",
        // ...
        "YOUR_REGISTRATION_TOKEN_n"
    );

    // Subscribe the devices corresponding to the registration tokens to the
    // topic.
    TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopicAsync(
        registrationTokens, topic).get();
    // See the TopicManagementResponse reference documentation
    // for the contents of response.
    System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
    // [END subscribe]
  }

  public void unsubscribeFromTopic() throws Exception {
    String topic = "highScores";
    // [START unsubscribe]
    // These registration tokens come from the client FCM SDKs.
    List<String> registrationTokens = Arrays.asList(
        "YOUR_REGISTRATION_TOKEN_1",
        // ...
        "YOUR_REGISTRATION_TOKEN_n"
    );

    // Unsubscribe the devices corresponding to the registration tokens from
    // the topic.
    TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopicAsync(
        registrationTokens, topic).get();
    // See the TopicManagementResponse reference documentation
    // for the contents of response.
    System.out.println(response.getSuccessCount() + " tokens were unsubscribed successfully");
    // [END unsubscribe]
  }
}
