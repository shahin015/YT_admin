package com.shahindemunav.admin.ui.notifications;

import android.content.Context;

import java.util.ArrayList;

public class PaymentData {
    String accountNumber,bank,key, refcode,trasngationid,amount,status;

    public PaymentData() {
    }

    public PaymentData(String accountNumber, String bank, String key, String refcode, String trasngationid, String amount,String status) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.key = key;
        this.refcode = refcode;
        this.trasngationid = trasngationid;
        this.amount = amount;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public String getTrasngationid() {
        return trasngationid;
    }

    public void setTrasngationid(String trasngationid) {
        this.trasngationid = trasngationid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
