package com.example.administrator.fans;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.bean.ChatMessage;
import com.example.administrator.httputils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private EditText editText;
    private Button button;
    private ListView listView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> list;
    private static final int SHOW_MESSAGE=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_MESSAGE:
                    ChatMessage chatMessage= (ChatMessage) msg.obj;
                    list.add(chatMessage);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(list.size()-1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }
    public void initView(){
        editText= (EditText) findViewById(R.id.id_input_msg);
        button= (Button) findViewById(R.id.id_send_msg);
        listView= (ListView) findViewById(R.id.id_listview_msgs);

    }

    public void initData(){
        list=new ArrayList<ChatMessage>();
        ChatMessage chatMessage=new ChatMessage(new Date(), ChatMessage.Type.Incoming,"主人,小粉随时待命");
        ChatMessage chatMessage1=new ChatMessage(new Date(),ChatMessage.Type.Outcoming,"你好,小粉");
        list.add(chatMessage);
        list.add(chatMessage1);
        adapter=new ChatMessageAdapter(MainActivity.this,list);
        listView.setAdapter(adapter);
    }

    public void initListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    public void  sendMessage(){
        final String msg=editText.getText().toString();
        if(TextUtils.isEmpty(msg)){
            Toast.makeText(this,"发送消息不能为空",Toast.LENGTH_SHORT).show();
            return ;
        }
        ChatMessage sendMsg=new ChatMessage();
        sendMsg.setType(ChatMessage.Type.Outcoming);
        sendMsg.setMsg(msg);
        sendMsg.setDate(new Date());
        list.add(sendMsg);
        adapter.notifyDataSetChanged();
        listView.setSelection(list.size()-1);
        editText.setText("");

      new Thread(new Runnable() {
          @Override
          public void run() {
              ChatMessage chatMessage= HttpUtils.sendMessage(msg);
              Log.i("Tag",msg);
              Message message=Message.obtain();
              message.what=SHOW_MESSAGE;Log.i("Tag",chatMessage.getMsg());
              message.obj=chatMessage;
              handler.sendMessage(message);
          }
      }).start();
   }

}