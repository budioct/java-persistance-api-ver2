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
public class BasicDataTypeTest {

    /**
     * Basic Data Type
     * ● JPA akan melakukan mapping data type secara otomatis dari table ke Class Entity
     * ● Secara default, tipe data dasar yang biasa digunakan di Java sudah didukung oleh JPA
     * ● Yang kita hanya perlukan adalah memastikan tipe data di Class Entity sama dengan tipe data di kolom Table di Database
     *
     * Daftar Tipe Data
     * ● Semua Number (Byte, Short, Integer, Long, Float, Double)
     * ● Semua Big Number (BigInteger, BigDecimal)
     * ● Boolean
     * ● String & Character
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
    void testInsertBasicDataType(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
//        customer.setId("3");
        customer.setName("wawan");
        customer.setPrimaryEmail("wawan@test.com");
        customer.setAge((byte) 30);
        customer.setMarried(true);
//        customer.setType(CustomerType.PREMIUM);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         customers
         *         (age,married,name,primary_email,id)
         *     values
         *         (?,?,?,?,?)
         */

    }

}
