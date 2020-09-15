package com.google.firebase.example;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class Process extends Thread{
    private static final Logger logger = Logger.getLogger(Process.class.getName());
    Queue<Message> mQueue = new ArrayDeque<>(400000);
    FirebaseMessagingSnippets fbm = null;

    public Process() {
        fbm = new FirebaseMessagingSnippets();
    }
    public void fillQueue(int size) {
        String topic = "sales";
        for(int i = 1; i <= size; ++i) {
            Message msg = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Price drop " + i)
                            .setBody("5% off all electronics " + i)
                            .build())
                    .setTopic(topic)
                    .build();
            mQueue.add(msg);
        }
        logger.info("fill queue with number of element: " + mQueue.size());
    }

    @Override
    public void run() {
        logger.info("begin dequeue");
        while(true) {
            logger.info("size of queue: " + mQueue.size());
            if(mQueue.isEmpty()) {
                logger.info("message queue is empty");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                List<Message> messages = new ArrayList<>();
                for(int i = 0; i < 500; ++i) {
                    Message msg = mQueue.poll();
                    messages.add(msg);
                    if(mQueue.isEmpty()) {
                        break;
                    }
                }
                logger.info("created list msg notification with size: " + messages.size());
                try {
                    fbm.sendAll(messages);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
