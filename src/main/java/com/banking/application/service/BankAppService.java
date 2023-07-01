package com.banking.application.service;

import java.util.List;

import com.banking.application.modal.Account;

public interface BankAppService {
	Account saveAccount(Account account);
	String getCustomerNameByAccountNo(long accountNo);
	Long getCustomerMobileNoByAccountNot(long accountNo);
	Account findById(int id);
	Account findByAccountNo(long accountNo);
	Double checkBalance(long accountNo);
	Account depositAmount(long accountNo, double balance);
	Account editCustomerNameByAccountNo(long accountNo, String name);
	Account editCustomerEmailIdByAccountNo(long accountNo, String emailId);
	Account editMobileNumberByAccountNo(long accountNo, long mobileNo);
	boolean transferAmount(long senderAccountNo, long receiverAccountNo, double amountToBeTransferred);
	List<Account> findAllAccounts();
	void deleteCustomerByAccNo(long accNo);
	void deleteAllCustomers();
}