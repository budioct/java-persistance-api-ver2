package com.tutorial.entity.relationship.inheritance.jointable;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {

    /**
     * Joined Table Inheritance
     * Yang artinya, tiap Child Entity memiliki table masing-masing, namun akan melakukan join primary key dengan table Parent Entity
     * Pada Joined Table Inheritance, kita tidak perlu menggunakan Discriminator Column lagi, karena data nya sudah terpisah table
     */

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "amount")
    private Long amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
