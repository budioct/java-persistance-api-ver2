package com.tutorial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Embeddable
public class DepartmentId implements Serializable {

    /**
     * Embedded ID
     * ● Id adalah representasi dari Primary Key di Table
     * ● Seperti yang sudah kita bahas di materi sebelumnya, Class Entity wajib memiliki Id
     * ● Namun bagaimana jika ternyata ada table dengan Primary Key lebih dari satu kolom?
     * ● Pada kasus seperti ini, kita tidak bisa membuat dua atribut dengan annotation Id, karena
     *   method-method yang terdapat di EntityManager hanya menggunakan 1 parameter sebagai ID
     * ● Oleh karena itu, kita perlu menggunakan Class Embedded
     * ● Dan untuk menandai bahwa atribut itu adalah Id, kita gunakan annotation EmbeddedId
     * ● Khusus untuk class Embedded untuk Primary Key, direkomendasikan implements Serializable
     */

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "department_id")
    private String departmentId;

    public DepartmentId() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
