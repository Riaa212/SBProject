package com.example.admin.service;

import com.example.admin.domain.EmailDetails;

public interface EmailService {

	 // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
