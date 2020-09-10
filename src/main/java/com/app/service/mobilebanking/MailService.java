package com.app.service.mobilebanking;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.entity.corebankingdummy.CustomerDummy;
import com.app.entity.mobilebanking.Customer;
import com.app.entity.mobilebanking.TempOtp;

@Service
public class MailService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendRegisterOtp(CustomerDummy customer, TempOtp tempOtp) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH:mm:ss");
		String expiredDate = formatter.format(tempOtp.getExpired_date());
		
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(tempOtp.getToken() + " e-mail token MobileBanking for Login/Registration/Reset. Avalaible before " + expiredDate + ". Never share this code, it is confidential!");
		
		javaMailSender.send(mail);
	}
	
	public void sendLoginOtp(Customer customer, TempOtp tempOtp) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH:mm:ss");
		String expiredDate = formatter.format(tempOtp.getExpired_date());
		
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(tempOtp.getToken() + " e-mail token MobileBanking for Login/Registration/Reset. Available before " + expiredDate + ". Never share this code, it is confidential");
	
		javaMailSender.send(mail);
	}
	
	public void sendTransferOtp(Customer customer, TempOtp tempOtp, Double totalAmount, String currency, String accNumber, String accName, String bankName) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		String censoredAccNumber = accNumber.substring(accNumber.length()-4);
		String amount = String.format("%,.2f", totalAmount);
		SimpleDateFormat formatter = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH:mm:ss");
		String expiredDate = formatter.format(tempOtp.getExpired_date());
		
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(
				tempOtp.getToken() + 
				" e-mail token Mobile Banking for transfer to " + 
				bankName + 
				" ***" + 
				censoredAccNumber + 
				" " + accName +
				" " + currency + 
				" " + amount + 
				". " + "Available before " + expiredDate + ". Never share this code, it is confidential!"
		);
		
		javaMailSender.send(mail);
	}
	
}
