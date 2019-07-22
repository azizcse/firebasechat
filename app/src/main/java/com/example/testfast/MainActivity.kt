package com.example.testfast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.example.testfast.Constants.*
import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    lateinit var chatNode: String
    lateinit var chatNode_1: String
    lateinit var chatNode_2: String
    lateinit var valueEventListener: ValueEventListener
    //Id =d66b57108f0d666a nexus
    //Id =792fa45f566fd86d roar
    lateinit var myId: String

    private val friendsId = "792fa45f566fd86d"

    private val items = ArrayList<ChatMessage>()
    lateinit var pfbd: ParseFirebaseData
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myId = getId()
        chatNode = "d66b57108f0d666a-aziz"
        pfbd = ParseFirebaseData()

        valueEventListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.addAll(pfbd.getMessagesForSingleUser(dataSnapshot.child(chatNode)))

                for (item : DataSnapshot in dataSnapshot.child(chatNode).children){
                    if(!item.child(NODE_RECEIVER_ID).getValue().toString().equals(myId)){
                        item.child(NODE_IS_READ).ref.runTransaction(object : Transaction.Handler{
                            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {

                            }

                            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                                mutableData.setValue(true)
                                return Transaction.success(mutableData)
                            }

                        })
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }


        }
        ref = FirebaseDatabase.getInstance().getReference(Constants.MESSAGE_CHILD)
        ref.addValueEventListener(valueEventListener)


        Log.e("SecureId_id", "Id =" + chatNode)
    }

    fun onButtonClick(view : View){
        val hm  = HashMap<String, Any>()
        hm.put(NODE_TEXT, "Hello "+System.currentTimeMillis())
        hm.put(NODE_TIMESTAMP, System.currentTimeMillis().toString())
        hm.put(NODE_RECEIVER_ID, friendsId)
        hm.put(NODE_RECEIVER_NAME, "dummy")
        hm.put(NODE_RECEIVER_PHOTO, "No photo")
        hm.put(NODE_SENDER_ID, myId)
        hm.put(NODE_SENDER_NAME, "me")
        hm.put(NODE_SENDER_PHOTO, "here")
        hm.put(NODE_IS_READ, false)

        ref.child(chatNode).push().setValue(hm)
    }


    private fun getId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
}
