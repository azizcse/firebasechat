package com.example.testfast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.testfast.Constants.*;

/**
 * Created by Bibaswann on 23-06-2017.
 */

public class ParseFirebaseData {



    public List<ChatMessage> getMessagesForSingleUser(DataSnapshot dataSnapshot) {
        List<ChatMessage> chats = new ArrayList<>();
        String text = null, msgTime = null, senderId = null, senderName = null, senderPhoto = null, receiverId = null, receiverName = null, receiverPhoto = null;
        Boolean read = Boolean.TRUE;
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            text = data.child(NODE_TEXT).getValue().toString();
            msgTime = data.child(NODE_TIMESTAMP).getValue().toString();
            senderId = data.child(NODE_SENDER_ID).getValue().toString();
            senderName = data.child(NODE_SENDER_NAME).getValue().toString();
            senderPhoto = data.child(NODE_SENDER_PHOTO).getValue().toString();
            receiverId = data.child(NODE_RECEIVER_ID).getValue().toString();
            receiverName = data.child(NODE_RECEIVER_NAME).getValue().toString();
            receiverPhoto = data.child(NODE_RECEIVER_PHOTO).getValue().toString();
            //Node isRead is added later, may be null
            read = data.child(NODE_IS_READ).getValue() == null || Boolean.parseBoolean(data.child(NODE_IS_READ).getValue().toString());

            chats.add(new ChatMessage(text, msgTime, receiverId, receiverName, receiverPhoto, senderId, senderName, senderPhoto, read));
        }
        return chats;
    }
/*
    public ArrayList<ChatMessage> getAllLastMessages(DataSnapshot dataSnapshot) {
        // TODO: 11/09/18 Return only last messages of every conversation current user is involved in
        ArrayList<ChatMessage> lastChats = new ArrayList<>();
        ArrayList<ChatMessage> tempMsgList;
        long lastTimeStamp;
        String text = null, msgTime = null, senderId = null, senderName = null, senderPhoto = null, receiverId = null, receiverName = null, receiverPhoto = null;
        Boolean read = Boolean.TRUE;
        for (DataSnapshot wholeChatData : dataSnapshot.getChildren()) {

            tempMsgList = new ArrayList<>();
            lastTimeStamp = 0;

            for (DataSnapshot data : wholeChatData.getChildren()) {
                msgTime = data.child(NODE_TIMESTAMP).getValue().toString();
                if (Long.parseLong(msgTime) > lastTimeStamp)
                    lastTimeStamp = Long.parseLong(msgTime);
                text = data.child(NODE_TEXT).getValue().toString();
                senderId = data.child(NODE_SENDER_ID).getValue().toString();
                senderName = data.child(NODE_SENDER_NAME).getValue().toString();
                senderPhoto = data.child(NODE_SENDER_PHOTO).getValue().toString();
                receiverId = data.child(NODE_RECEIVER_ID).getValue().toString();
                receiverName = data.child(NODE_RECEIVER_NAME).getValue().toString();
                receiverPhoto = data.child(NODE_RECEIVER_PHOTO).getValue().toString();
                //Node isRead is added later, may be null
                read = data.child(NODE_IS_READ).getValue() == null || Boolean.parseBoolean(data.child(NODE_IS_READ).getValue().toString());

                tempMsgList.add(new ChatMessage(text, msgTime, receiverId, receiverName, receiverPhoto, senderId, senderName, senderPhoto, read));
            }

            for (ChatMessage oneTemp : tempMsgList) {
                if ((set.readSetting(Constants.PREF_MY_ID).equals(oneTemp.getReceiver().getId())) || (set.readSetting("myid").equals(oneTemp.getSender().getId()))) {
                    if (oneTemp.getTimestamp().equals(String.valueOf(lastTimeStamp))) {
                        lastChats.add(oneTemp);
                    }
                }
            }
        }
        return lastChats;
    }

    public ArrayList<ChatMessage> getAllUnreadReceivedMessages(DataSnapshot dataSnapshot) {
        ArrayList<ChatMessage> lastChats = new ArrayList<>();
        ArrayList<ChatMessage> tempMsgList;
        long lastTimeStamp;
        String text = null, msgTime = null, senderId = null, senderName = null, senderPhoto = null, receiverId = null, receiverName = null, receiverPhoto = null;
        Boolean read = Boolean.TRUE;
        for (DataSnapshot wholeChatData : dataSnapshot.getChildren()) {

            tempMsgList = new ArrayList<>();
            lastTimeStamp = 0;

            for (DataSnapshot data : wholeChatData.getChildren()) {
                msgTime = data.child(NODE_TIMESTAMP).getValue().toString();
                if (Long.parseLong(msgTime) > lastTimeStamp)
                    lastTimeStamp = Long.parseLong(msgTime);
                text = data.child(NODE_TEXT).getValue().toString();
                senderId = data.child(NODE_SENDER_ID).getValue().toString();
                senderName = data.child(NODE_SENDER_NAME).getValue().toString();
                senderPhoto = data.child(NODE_SENDER_PHOTO).getValue().toString();
                receiverId = data.child(NODE_RECEIVER_ID).getValue().toString();
                receiverName = data.child(NODE_RECEIVER_NAME).getValue().toString();
                receiverPhoto = data.child(NODE_RECEIVER_PHOTO).getValue().toString();
                //Node isRead is added later, may be null
                read = data.child(NODE_IS_READ).getValue() == null || Boolean.parseBoolean(data.child(NODE_IS_READ).getValue().toString());

                tempMsgList.add(new ChatMessage(text, msgTime, receiverId, receiverName, receiverPhoto, senderId, senderName, senderPhoto, read));
            }

            for (ChatMessage oneTemp : tempMsgList) {
                if ((set.readSetting(Constants.PREF_MY_ID).equals(oneTemp.getReceiver().getId()))) {
                    if (oneTemp.getTimestamp().equals(String.valueOf(lastTimeStamp)) && !oneTemp.isRead()) {
                        lastChats.add(oneTemp);
                    }
                }
            }
        }
        return lastChats;
    }

    private String encodeText(String msg) {
        return msg.replace(",", "#comma#").replace("{", "#braceopen#").replace("}", "#braceclose#").replace("=", "#equals#");
    }

    private String decodeText(String msg) {
        return msg.replace("#comma#", ",").replace("#braceopen#", "{").replace("#braceclose#", "}").replace("#equals#", "=");
    }*/
}
