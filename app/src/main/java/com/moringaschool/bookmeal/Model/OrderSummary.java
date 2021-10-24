package com.moringaschool.bookmeal.Model;

import android.view.SurfaceControl;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderSummary {

    @SerializedName("numberOfTransactions")
    @Expose
    private Integer numberOfTransactions;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderSummary() {
    }

    /**
     *
     * @param amount
     * @param numberOfTransactions
     * @param transactions
     */
    public OrderSummary(Integer numberOfTransactions, Integer amount, List<Transaction> transactions) {
        super();
        this.numberOfTransactions = numberOfTransactions;
        this.amount = amount;
        this.transactions = transactions;
    }

    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(Integer numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}