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
import com.app.entity.mobilebanking.TransferOtp;

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
	
	public void sendTransferOtp(Customer customer, TransferOtp otp, Double totalAmount, String currency, String accNumber, String accName, String bankName) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		String censoredAccNumber = accNumber.substring(accNumber.length()-4);
		String amount = String.format("%,.2f", totalAmount);
		mail.setTo(customer.getEmail());
		mail.setSubject("Mobile Banking OTP Token");
		mail.setText(
				otp.getToken() + 
				" e-mail token Mobile Banking for transfer to " + 
				bankName + 
				" ***" + 
				censoredAccNumber + 
				" " + accName +
				" " + currency + 
				" " + amount + 
				". " + " Never share this code, it is confidential!"
		);
		
		javaMailSender.send(mail);
	}
	
}
