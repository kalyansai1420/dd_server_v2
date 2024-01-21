package com.dod.dodbackend.service;

import javax.mail.MessagingException;


public interface EmailService {

	public String sendEmail(String to, String subject, String text) throws MessagingException;
}
