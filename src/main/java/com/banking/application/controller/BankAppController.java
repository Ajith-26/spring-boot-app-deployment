package com.banking.application.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.application.exception.AccountAlreadyExistsException;
import com.banking.application.exception.AccountNotFoundException;
import com.banking.application.exception.EmailIdAlreadyExistsException;
import com.banking.application.exception.InsufficientFundsException;
import com.banking.application.modal.Account;
import com.banking.application.service.BankAppService;

@RestController
@RequestMapping("account-mgmt")
@CrossOrigin
public class BankAppController {

	@Value("${create.error.message}")
	private String createErrorMessage;

	@Value("${get.error.message}")
	private String getErrorMessage;

	@Value("${update.error.message}")
	private String updateErrorMessage;

	@Value("${transfer.error.message}")
	private String transferErrorMessage;

	@Value("${empty.error.message}")
	private String emptyErrorMessage;

	@Autowired
	private BankAppService service;

	@PostMapping("/account")
	ResponseEntity<?> saveAccount(@RequestBody Account account) throws AccountAlreadyExistsException {
		ResponseEntity<?> response = null;
		Account savedAccount = service.saveAccount(account);
		if (savedAccount == null) {
			throw new AccountAlreadyExistsException(createErrorMessage);
		} else {
			response = ResponseEntity.ok(savedAccount);
		}
		return response;
	}

	@GetMapping("/name/{accountNo}")
	ResponseEntity<?> getCustomerNameByAccountNo(@PathVariable("accountNo") long accountNo) {
		ResponseEntity<?> response = new ResponseEntity<>(getErrorMessage, HttpStatus.FOUND);
		String customerName = service.getCustomerNameByAccountNo(accountNo);
		if (customerName != null) {
			response = ResponseEntity.ok("Customer name for the A/C no you have given: " + customerName);
		}
		return response;
	}

	@GetMapping("/mobileNo/{accountNo}")
	ResponseEntity<?> getCustomerMobileNoByAccountNo(@PathVariable("accountNo") long accountNo) {
		ResponseEntity<?> response = new ResponseEntity<>(getErrorMessage, HttpStatus.FOUND);
		Long customerMobileNo = service.getCustomerMobileNoByAccountNot(accountNo);
		if (customerMobileNo != null) {
			response = ResponseEntity.ok("Customer mobile No for the A/C no you have given: " + customerMobileNo);
		}
		return response;
	}

	@GetMapping("/details/{accountNo}")
	ResponseEntity<?> getCustomerDetailsByAccountNo(@PathVariable("accountNo") long accountNo) {
		ResponseEntity<?> response = new ResponseEntity<>(getErrorMessage, HttpStatus.FOUND);
		Account customerDetails = service.findByAccountNo(accountNo);
		if (customerDetails != null) {
			response = ResponseEntity.ok("Customer details for the A/C no you have given: " + customerDetails);
		}
		return response;
	}

	@GetMapping("/getBalance/{accountNo}")
	ResponseEntity<?> getBalance(@PathVariable("accountNo") Long accountNo) throws AccountNotFoundException {
		ResponseEntity<?> response = null;
		Double balance = service.checkBalance(accountNo);
		if (balance == null) {
			throw new AccountNotFoundException(getErrorMessage);
		} else {
			response = ResponseEntity.ok("Account Balance: " + balance);
		}
		return response;
	}

	@PostMapping("/depositAmount/{accountNo}")
	ResponseEntity<?> depositAmount(@PathVariable("accountNo") Long accountNo,
			@RequestBody LinkedHashMap<String, Object> vals) throws AccountNotFoundException {
		Double balance = Double.parseDouble(vals.get("amountToBeDeposited").toString());
		ResponseEntity<?> response = new ResponseEntity<>(getErrorMessage, HttpStatus.NOT_FOUND);
		Account account = service.depositAmount(accountNo, balance);
		if (account != null) {
			response = ResponseEntity.ok("Account Balance after deposit: " + account.getAccountBalance());
		} else
			throw new AccountNotFoundException(getErrorMessage);
		return response;
	}

	@PutMapping("/name/{accountNo}/{custName}")
	ResponseEntity<?> editCustomerNameByAccountNo(@PathVariable("accountNo") long accountNo,
			@PathVariable("custName") String name) {
		ResponseEntity<?> response = new ResponseEntity<>(updateErrorMessage, HttpStatus.NOT_FOUND);
		Account account = service.editCustomerNameByAccountNo(accountNo, name);
		if (account != null)
			response = ResponseEntity.ok("Account updated with name: " + name);
		return response;
	}

	@PutMapping("/emailId/{accountNo}/{emailId}")
	ResponseEntity<?> editCustomerEmailIdByAccountNo(@PathVariable("accountNo") long accountNo,
			@PathVariable("emailId") String emailId) throws EmailIdAlreadyExistsException {
		ResponseEntity<?> response = new ResponseEntity<>(updateErrorMessage, HttpStatus.NOT_FOUND);
		Account account = service.editCustomerNameByAccountNo(accountNo, emailId);
		if (account != null)
			response = ResponseEntity.ok("Account updated with emailId: " + emailId);
		else
			throw new EmailIdAlreadyExistsException(
					"Email Id already used by some other accounts. Please use different emailId");
		return response;
	}

	@PutMapping("/mobileNo/{accountNo}/{mobileNo}")
	ResponseEntity<?> editmobileNumberByAccountNo(@PathVariable("accountNo") long accountNo,
			@PathVariable("mobileNo") long mobileNo) throws EmailIdAlreadyExistsException {
		ResponseEntity<?> response = new ResponseEntity<>(updateErrorMessage, HttpStatus.NOT_FOUND);
		Account account = service.editMobileNumberByAccountNo(accountNo, mobileNo);
		if (account != null)
			response = ResponseEntity.ok("Account updated with mobileNo: " + mobileNo);
		return response;
	}

	@PutMapping("/transferAmount/{senderAccountNo}/{receiverAccountNo}/{amountToBeTransferred}")
	ResponseEntity<?> transferAmount(@PathVariable("senderAccountNo") long senderAccountNo,
			@PathVariable("receiverAccountNo") long receiverAccountNo,
			@PathVariable("amountToBeTransferred") String amount)
			throws AccountNotFoundException, InsufficientFundsException {
		ResponseEntity<?> response = null;
		if (senderAccountNo != receiverAccountNo) {
			Account sender = service.findByAccountNo(senderAccountNo);
			if (sender == null) {
				throw new AccountNotFoundException("Sender " + getErrorMessage);
			}
			Account receiver = service.findByAccountNo(receiverAccountNo);
			if (receiver == null) {
				throw new AccountNotFoundException("Receiver " + getErrorMessage);
			}
			double amountToBeTransferred = Double.parseDouble(amount);
			boolean transfer = service.transferAmount(senderAccountNo, receiverAccountNo, amountToBeTransferred);
			if (transfer) {
				response = ResponseEntity.ok("Successfully transferred Rs. " + amountToBeTransferred);
			} else {
				throw new InsufficientFundsException(transferErrorMessage);
			}
		}
		return response;
	}

	@GetMapping("/{id}")
	ResponseEntity<?> findById(@PathVariable int id) {
		Account account = service.findById(id);
		ResponseEntity<?> response = new ResponseEntity<>(emptyErrorMessage, HttpStatus.NOT_FOUND);
		if (account != null) {
			response = ResponseEntity.ok(account);
		}
		return response;
	}

	@GetMapping("/all")
	ResponseEntity<?> findAll() throws AccountNotFoundException {
		List<Account> accounts = service.findAllAccounts();
		ResponseEntity<?> response = null;
		if (accounts != null && !accounts.isEmpty()) {
			response = ResponseEntity.ok(accounts);
		} else {
			throw new AccountNotFoundException("No Accounts found");
		}
		return response;
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCustomerByAccountNo(@PathVariable("id") long accNo) throws AccountNotFoundException {
		ResponseEntity<?> response = null;
		Account acc = service.findByAccountNo(accNo);
		if (acc == null)
			throw new AccountNotFoundException(getErrorMessage);
		else {
			service.deleteCustomerByAccNo(accNo);
			response = ResponseEntity.ok("Deleted account with A/C no: " + acc.getAccountNo());
		}
		return response;
	}

	@DeleteMapping("/all")
	ResponseEntity<?> deleteAllCustomers() throws AccountNotFoundException {
		ResponseEntity<?> response = null;
		List<Account> accounts = service.findAllAccounts();
		if (accounts != null && !accounts.isEmpty()) {
			service.deleteAllCustomers();
			response = ResponseEntity.ok("Deleted all accounts");
		} else {
			throw new AccountNotFoundException(emptyErrorMessage);
		}
		return response;
	}
}