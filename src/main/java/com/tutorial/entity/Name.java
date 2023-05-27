package com.tutorial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

    /**
     * kadang menyulitkan jika kita harus tetap membuat atribut nya pada Class Entity yang sama, karena lama-lama atribut nya akan semakin banyak
     * Untungnya di JPA terdapat fitur Embedded, dimana kita bisa menambahkan atribut berupa Class lain, namun tetap mapping ke table yang sama
     *
     * @Embeddable
     * Untuk menandai bahwa sebuah class itu bisa digunakan sebagai atribut di Class Entity atau istilahnya embedded atribut, maka kita wajib menambahkan annotation Embeddable di Class nya
     */

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    public Name() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
