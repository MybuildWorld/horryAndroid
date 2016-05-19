package com.example.administrator.fans;


import com.example.administrator.bean.ChatMessage;
import com.example.administrator.bean.Result;
import com.example.administrator.httputils.HttpUtils;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage=HttpUtils.sendMessage("世界上最帅的人是谁？");
        System.out.println(chatMessage.getMsg());

    }

}