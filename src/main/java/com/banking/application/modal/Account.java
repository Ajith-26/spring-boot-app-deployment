package com.banking.application.modal;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_emailid", unique = true)
	private String customerEmailId;

	@Column(name = "customer_mobileno")
	private long customerMobileNo;

	@Column(name = "account_no", unique = true)
	private long accountNo;

	@Column(name = "balance")
	private double accountBalance;

	public Account(String customerName, String customerEmailId, long customerMobileNo, long accountNo,
			double accountBalance) {
		super();
		this.customerName = customerName;
		this.customerEmailId = customerEmailId;
		this.customerMobileNo = customerMobileNo;
		this.accountNo = accountNo;
		this.accountBalance = accountBalance;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Double.doubleToLongBits(accountBalance) == Double.doubleToLongBits(other.accountBalance)
				&& accountNo == other.accountNo && Objects.equals(customerEmailId, other.customerEmailId)
				&& customerMobileNo == other.customerMobileNo && Objects.equals(customerName, other.customerName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountNo, customerEmailId, customerMobileNo, customerName, id);
	}
}