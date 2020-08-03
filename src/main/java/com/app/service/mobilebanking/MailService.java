package com.app.service.mobilebanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.LoginOtp;
import com.app.entity.mobilebanking.RegisterOtp;

@Service
public class MailService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail(CustomerDummy customer, RegisterOtp otp) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(otp.getToken() + " e-mail token MobileBanking for Login/Registration/Reset. Avalaible before " + otp.getExpired_date() + ". Never share this code, it is confidential!");
		
		javaMailSender.send(mail);
	}
	
	public void sendLoginOtp(Customer customer, LoginOtp otp) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(otp.getToken() + " e-mail token MobileBanking for Login/Registration/Reset. Available before " + otp.getExpired_date() + ". Never share this code, it is confidential");
	
		javaMailSender.send(mail);
	}
	
}
