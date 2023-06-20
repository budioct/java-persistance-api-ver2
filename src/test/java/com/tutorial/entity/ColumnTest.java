package com.tutorial.entity;

import com.tutorial.data.CustomerType;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class ColumnTest {

    /**
     * Column
     * ● Secara default, nama attribute di Class Entity akan di mapping sebagai nama kolom di Table
     * ● Namun terkadang, nama kolom sering berbeda format dengan attribute di Class, misal di attribute
     *   Class menggunakan camelCase, sedangkan kolom di Table menggunakan snake_case
     * ● Untuk melakukan mapping kolom, kita bisa menggunakan annotation Column
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/column
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
    void testInsertColumn(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
//        customer.setId("3");
        customer.setName("wawan");
        customer.setPrimaryEmail("wawan@test.com");

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         customers
         *         (name,primary_email)
         *     values
         *         (?,?)
         */

    }

}
