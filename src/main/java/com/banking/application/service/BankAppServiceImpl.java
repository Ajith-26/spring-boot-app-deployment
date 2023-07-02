package com.banking.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.application.dao.BankAppRepository;
import com.banking.application.modal.Account;

@Service
public class BankAppServiceImpl implements BankAppService {

	@Autowired
	private BankAppRepository repo;

	private long accountNo = 100;

	@Override
	public Account saveAccount(Account account) {
		Account response = null;
		Account foundAccount = repo.findBycustomerEmailId(account.getCustomerEmailId());
		System.out.println(foundAccount);
		if (foundAccount == null) {
			account.setAccountNo(accountNo++);
			response = repo.save(account);
		}
		return response;
	}

	@Override
	public String getCustomerNameByAccountNo(long accountNo) {
		String response = null;
		Account foundAccount = repo.findByaccountNo(accountNo);
		System.out.println(foundAccount);
		if (foundAccount != null) {
			response = foundAccount.getCustomerName();
		}
		return response;
	}

	@Override
	public Long getCustomerMobileNoByAccountNot(long accountNo) {
		Long response = null;
		Account foundAccount = repo.findByaccountNo(accountNo);
		if (foundAccount != null) {
			response = foundAccount.getCustomerMobileNo();
		}
		return response;
	}

	@Override
	public Account findById(int id) {
		Account account = repo.findById(id).orElse(null);
		return account;
	}

	@Override
	public Account findByAccountNo(long accountNo) {
		return repo.findByaccountNo(accountNo);
	}

	@Override
	public Double checkBalance(long accountNo) {
		Double response = null;
		Account foundAccount = repo.findByaccountNo(accountNo);
		if (foundAccount != null) {
			response = foundAccount.getAccountBalance();
		}
		return response;
	}

	@Override
	public Account depositAmount(long accountNo, double amountToBeAdded) {
		Account foundAccount = findByAccountNo(accountNo);
		if (foundAccount != null) {
			double amountAvailable = foundAccount.getAccountBalance();
			repo.updateBalance(accountNo, amountAvailable + amountToBeAdded);
			foundAccount.setAccountBalance(amountAvailable + amountToBeAdded);
		}
		return foundAccount;
	}

	@Override
	public Account editCustomerNameByAccountNo(long accountNo, String name) {
		Account foundAccount = findByAccountNo(accountNo);
		Account response = null;
		if (foundAccount != null) {
			repo.editCustomerNameByAccountNo(accountNo, name);
			response = repo.findByaccountNo(accountNo);
		}
		return response;
	}

	@Override
	public Account editCustomerEmailIdByAccountNo(long accountNo, String emailId) {
		Account foundAccount = findByAccountNo(accountNo);
		Account response = null;
		if (foundAccount != null) {
			repo.editCustomerEmailIdByAccountNo(accountNo, emailId);
			response = repo.findByaccountNo(accountNo);
		}
		return response;
	}

	@Override
	public Account editMobileNumberByAccountNo(long accountNo, long mobileNo) {
		Account foundAccount = findByAccountNo(accountNo);
		Account response = null;
		if (foundAccount != null) {
			repo.editCustomerMobileNoByAccountNo(accountNo, mobileNo);
			response = repo.findByaccountNo(accountNo);
		}
		return response;
	}

	@Override
	public boolean transferAmount(long senderAccountNo, long receiverAccountNo, double amountToBeTransferred) {
		boolean response = false;
		Account senderAccount = repo.findByaccountNo(senderAccountNo);
		Account receiverAccount = repo.findByaccountNo(receiverAccountNo);
		double senderAcBalance = senderAccount.getAccountBalance();
		double receiverAcBalance = receiverAccount.getAccountBalance();
		if (senderAcBalance > amountToBeTransferred) {
			repo.updateBalance(receiverAccountNo, receiverAcBalance + amountToBeTransferred);
			repo.updateBalance(senderAccountNo, senderAcBalance - amountToBeTransferred);
			response = true;
		}
		return response;
	}

	@Override
	public List<Account> findAllAccounts() {
		return repo.findAll();
	}

	@Override
	public void deleteCustomerByAccNo(long accNo) {
		repo.deleteByaccountNo(accNo);
	}

	@Override
	public void deleteAllCustomers() {
		repo.deleteAll();
	}

}