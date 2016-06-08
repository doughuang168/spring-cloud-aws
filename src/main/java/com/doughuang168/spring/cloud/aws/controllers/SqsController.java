package com.doughuang168.spring.cloud.aws.controllers;


import com.amazonaws.util.IOUtils;
import com.doughuang168.spring.cloud.aws.messaging.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/sqs")
public class SqsController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/write",method = RequestMethod.POST)
    public void write(HttpServletRequest servletRequest,HttpServletResponse servletResponse) throws IOException {

        InputStream inputStream = servletRequest.getInputStream();

        String message = IOUtils.toString(inputStream);

        messageService.sendMessage(message);
    }

}

