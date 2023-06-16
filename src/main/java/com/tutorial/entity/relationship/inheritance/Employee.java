package com.tutorial.entity.relationship.inheritance;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("EMPLOYEE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {

    /**
     * ini class parent relationship inheritance
     * Parent entity berisikan attribute yang tersedia di semua Child Entity nya
     * Dan khusus untuk Parent Entity, kita harus menyebutkan strategy Inheritance nya menggunakan annotation @Inheritance
     * Pada kasus Single Table Inheritance, kita wajib menggunakan @Inheritance Type SINGLE_TABLE
     * kita perlu menambahkan annotation @DiscriminatorColumn pada Parent. name = nama colum yang dibuat selain field java bean
     */

    @Id
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
