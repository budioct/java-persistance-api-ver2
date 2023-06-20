package com.tutorial.entity;

import com.tutorial.embeddedable.DepartmentId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {

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

    @EmbeddedId
    private DepartmentId id;

    private String name;

    public Department() {
    }

    public DepartmentId getId() {
        return id;
    }

    public void setId(DepartmentId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
