package dev.meru.paperwings.core.engine.controller;

import dev.meru.paperwings.core.engine.model.MessageDTO;
import dev.meru.paperwings.messaging.model.Message;
import dev.meru.paperwings.messaging.repository.MessageRepository;
import dev.meru.paperwings.messaging.services.MessageService;
import dev.meru.paperwings.users.model.User;
import dev.meru.paperwings.users.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MessagingController {

    private final MessageService messageService;
    private final UserService userService;
    private final MessageRepository messageRepository;

    public MessagingController(MessageService messageService, UserService userService, MessageRepository messageRepository) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageRepository = messageRepository;
    }


    @MessageMapping("/getAllMessages")
    @SendTo("/topic/allMessages")
    public String retrieveMessages() {
        //System.out.println("Received HTML content: " + htmlContent);
        StringBuilder messageBuilder = new StringBuilder();
        List<Message> messages = messageService.getAllMessages();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        messageBuilder.append("");
        for (Message message : messages) {
            User sender = userService.findUserById(message.getSenderId());
            System.out.println(sender.getUsername());
            String messageHtml = "<div><strong>" + sender.getUsername() + " (" + message.getTimestamp().format(formatter) + "):</strong> " + message.getContent() + "</div>";
            messageBuilder.append(messageHtml);
        }
        return messageBuilder.toString();
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp());
        messageRepository.save(message);
    }

}
