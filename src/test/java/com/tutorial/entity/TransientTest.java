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
public class TransientTest {

    /**
     * Transient
     * ● Saat membuat attribute di Class Entity, secara default attribute tersebut akan dianggap sebagai kolom di Table
     * ● Kadang ada kasus kita ingin membuat attribute yang bukan kolom di table
     * ● Pada kasus ini, kita bisa menambahkan annotation Transient, untuk menandai bahwa attribute
     * tersebut bukan kolom di Table, sehingga akan di ignore oleh JPA
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/transient
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
    void testInsert4Transient(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
//        customer.setId("3");
        customer.setName("ependi");
        customer.setPrimaryEmail("ependi@test.com");
        customer.setAge((byte) 27);
        customer.setMarried(true);
        customer.setType(CustomerType.PREMIUM);
        customer.setFullName("Efendi Ruslan"); // Transiesnt // di bagian dari entity yang akan diabaikan ketika peroses ke DB

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();

        /**
         *
         * field fullName tidak di anggap karna diberi annotation @Transient
         *
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         customers
         *         (age,married,name,primary_email,type)
         *     values
         *         (?,?,?,?,?)
         */

    }

}
