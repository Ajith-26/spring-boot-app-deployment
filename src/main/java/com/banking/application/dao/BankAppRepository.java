package com.banking.application.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking.application.modal.Account;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BankAppRepository extends JpaRepository<Account, Integer>{
	Account findByaccountNo(long accountNo);
	Account findBycustomerEmailId(String emailId);
	void deleteByaccountNo(long accNo);
	
	@Modifying
	@Query("UPDATE Account acc SET acc.accountBalance=:newBalance WHERE acc.accountNo=:accountNo")
	int updateBalance(long accountNo, double newBalance);
	
	@Modifying
	@Query("UPDATE Account acc SET acc.customerName=:newName WHERE acc.accountNo=:accountNo")
	int editCustomerNameByAccountNo(long accountNo, String newName);
	
	@Modifying
	@Query("UPDATE Account acc SET acc.customerMobileNo=:mobileNo WHERE acc.accountNo=:accountNo")
	int editCustomerMobileNoByAccountNo(long accountNo, long mobileNo);

	@Modifying
	@Query("UPDATE Account acc SET acc.customerEmailId=:emailId WHERE acc.accountNo=:accountNo")
	int editCustomerEmailIdByAccountNo(long accountNo, String emailId);
}