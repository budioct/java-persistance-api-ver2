package com.tutorial.entity.relationship.inheritance.jointable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments_gopay")
public class PaymentGopay extends Payment {

    /**
     * ini class child relationship inheritance
     * Untuk Child Entity, kita cukup extends class Parent Entity
     * */

    @Column(name = "gopay_id")
    private String gopayId;

    public String getGopayId() {
        return gopayId;
    }

    public void setGopayId(String gopayId) {
        this.gopayId = gopayId;
    }

}
