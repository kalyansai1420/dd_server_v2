package com.dod.dodbackend.service.impl;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.dod.dodbackend.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Override
	public String sendEmail(String to, String subject, String text) throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

}
