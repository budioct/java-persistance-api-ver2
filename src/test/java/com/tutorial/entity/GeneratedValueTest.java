package com.tutorial.entity;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class GeneratedValueTest {

    /**
     * Generated Value
     * ● Beberapa database, kadang memiliki fitur membuat Primary Key yang diisi secara otomatis
     * ● Contohnya di MySQL terdapat fitur Auto Increment untuk Primary Key dengan tipe Number
     * ● Hal ini menyebabkan dalam JPA, kita tidak bisa mengubah nilai Id nya, karena Id akan dibuat oleh database
     * ● Pada kasus seperti ini, kita bisa menandai bahwa value Id dibuat secara otomatis oleh database menggunakan annotation GeneratedValue
     *
     * Generated Value Strategy
     * ● Selain Auto Increment, terdapat cara lain untuk membuat Id secara otomatis, contohnya di
     * PostgreSQL menggunakan Sequence, atau menggunakan Table lain untuk melakukan management Id, atau bahkan menggunakan UUID
     * ● Oleh karena itu, kita perlu memberi tahu Strategy apa yang dilakukan untuk membuat Id nya
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/generationtype
     */

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
        log.info("On Entity Manager Factory");
        this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @AfterEach
    void tearDown(){
        log.info("Off Entity Manager Factory");
        this.entityManagerFactory.close();
    }


    @Test
    void testInsertCategoryGeneratedValueStrategy() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("jazz");
        category.setDescription("kamu bisa dengerin lagu jazz yang kamu sukai di sini");

        entityManager.persist(category); // void persist(Object var1) // Untuk menyimpan entity

        Assertions.assertNotNull(category.getId());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         categories
         *         (description,name)
         *     values
         *         (?,?)
         */

    }

}
