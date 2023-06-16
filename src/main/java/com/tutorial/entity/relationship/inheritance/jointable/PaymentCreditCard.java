package com.tutorial.entity.relationship.inheritance.jointable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments_credit_card")
public class PaymentCreditCard extends Payment {

    /**
     * ini class child relationship inheritance
     * Untuk Child Entity, kita cukup extends class Parent Entity
     * */

    @Column(name = "masked_card")
    private String maskedCard;

    @Column(name = "bank")
    private String bank;

    public String getMaskedCard() {
        return maskedCard;
    }

    public void setMaskedCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
