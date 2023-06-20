package com.tutorial.entity;

import com.tutorial.data.CustomerType;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    /**
     * Class Entity wajib menambahkan annotation @Entity dan @Id, syarat untuk membuat table ORM
     * Class Entity adalah sebuah class Java Bean, dimana tiap attribute (yang memiliki getter dan setter)
     * di Class tersebut merepresentasikan kolom di table database
     *
     * @Table
     * note: jika kita ingin membuat table namnya harus jamak, supaya berbeda dengan Entity seperti: Entity>Customer <=> Table>customers
     *
     * @Column
     * untuk merubah nama kolom pada table database
     *
     * @GeneratedValue
     * memiliki fitur membuat Primary Key yang diisi secara otomatis
     * // Generated Value Strategy --> untuk melakukan management Id, atau bahkan menggunakan UUID (jika ingin menggunakan string)
     *
     * Basic Data Type
     * Daftar Tipe Data
     * ● Semua Number (Byte, Short, Integer, Long, Float, Double)
     * ● Semua Big Number (BigInteger, BigDecimal)
     * ● Boolean
     * ● String & Character
     *
     * @Enumerated()
     * cara menyimpan enum ke table java, enum jpa memiliki 2 strategy menyimpan bentuk Integer dan String, Sangat disarankan menggunakan strategy String,
     * karea strategy Integer bisa berubah ketika terjadi penambahan Enum Value pada posisi yang acak
     *
     * @Transient
     * ingin membuat attribute yang bukan kolom di table
     * untuk menandai bahwa attribute tersebut bukan kolom di Table, sehingga akan di ignore oleh JPA
     *
     *
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerType type;

    @Column(name = "primary_email")
    private String primaryEmail;

    private Boolean married;

    private Byte age;

    @Transient
    private String fullName;

    /**
     * Class Entity wajib memiliki default constructor yang tidak memiliki parameter, hal ini agar JPA bisa
     * membuat object baru tanpa parameter ketika melakukan mapping data dari table ke object Entity
     */
    public Customer() {
    }

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

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
