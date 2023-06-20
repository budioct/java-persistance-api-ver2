package com.tutorial.embeddedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentId that = (DepartmentId) o;
        return Objects.equals(companyId, that.companyId) && Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, departmentId);
    }

}
