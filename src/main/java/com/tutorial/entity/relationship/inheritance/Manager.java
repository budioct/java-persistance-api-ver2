package com.tutorial.entity.relationship.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends Employee{

    /**
     * ini class child relationship inheritance
     * Untuk Child Entity, kita cukup extends class Parent Entity
     * kita harus menambahkan annotation @DiscriminatorValue
     * */

    @Column(name = "total_employee")
    private Integer totalEmployee;

    public Integer getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(Integer totalEmployee) {
        this.totalEmployee = totalEmployee;
    }
}
