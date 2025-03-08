package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.*;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import io.github.cdimascio.dotenv.Dotenv;

public class UserServices {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> usersList;
    private User user;
    private final String USERS_PATH = "app/src/main/java/org/example/db/users.json";

    public UserServices() throws IOException {
        loadUsers();
    }

    public  UserServices(User user) throws IOException {
        this.user=user;
        loadUsers();
    }

    private void loadUsers() throws IOException {
        usersList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser() {
        Optional<User> foundUser = usersList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUser(User user) throws IOException {
        try {
            usersList.add(user);
            saveUsers();
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUsers() throws IOException {
        File userFile = new File(USERS_PATH);
        objectMapper.writeValue(userFile,usersList);
    }

    public void saveBookedTicket(Ticket ticketBooked) throws IOException {
        File userFile = new File(USERS_PATH);

    }

    public void emailConfirmation(String emailRecipient) {
        Dotenv dotenv = Dotenv.load();
        String emailSender = dotenv.get("EMAIL_USERNAME");
        String appPassword = dotenv.get("EMAIL_PASSWORD");
        Properties properties = System.getProperties();
        String host = dotenv.get("SMTP_SERVER");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, appPassword);
            }
        });

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(emailSender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));

            String emailContent = new String(Files.readAllBytes(Paths.get("app/src/main/java/org/example/email_template.html")));
            // Set Subject: subject of the email
            message.setSubject("Confirmation for Booking of Train Ticket");

            // set body of the email.
            message.setContent(emailContent,"text/html");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
