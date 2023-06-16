package com.tutorial.entity.relationship.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VP")
public class VicePresident extends Employee {

    /**
     * ini class child relationship inheritance
     * Untuk Child Entity, kita cukup extends class Parent Entity
     * kita harus menambahkan annotation @DiscriminatorValue
     * */

    @Column(name = "total_manager")
    private Integer totalManager;

    public Integer getTotalManager() {
        return totalManager;
    }

    public void setTotalManager(Integer totalManager) {
        this.totalManager = totalManager;
    }
}
