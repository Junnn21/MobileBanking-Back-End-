package com.app.responseBody;

import java.util.ArrayList;

import com.app.entity.corebankingdummy.AccountStatementDummy;

public class AccountStatementResponse {

	private String accountNumber;
	private ArrayList<AccountStatementDummy> statements = new ArrayList<AccountStatementDummy>();
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public ArrayList<AccountStatementDummy> getStatements() {
		return statements;
	}
	public void setStatements(ArrayList<AccountStatementDummy> statements) {
		this.statements = statements;
	}
	
}
