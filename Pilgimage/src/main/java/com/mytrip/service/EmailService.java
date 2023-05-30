package com.mytrip.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mytrip.modal.Email;

@Service
public class EmailService {
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void sendMail(Email email) {
	
		try {
			
			MimeMessage m=javaMailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(m);
			
			  helper.setFrom("mytripbooking123@gmail.com"); 
			  helper.setTo(email.getTo());
			  helper.setSubject(email.getSubject());
			  helper.setText(email.getMeassage(),true);
			 
             
             javaMailSender.send(m);     
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}


}
