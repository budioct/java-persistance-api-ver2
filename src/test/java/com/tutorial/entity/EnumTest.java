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
public class EnumTest {

    /**
     * Enum
     * ● Di Java, terdapat fitur Enum
     * ● Bagaimana cara menyimpan data Enum di Java ke table di database?
     * ● Jika kita memiliki attribute dengan tipe data Enum, maka kita perlu memberitahu JPA bagaimana
     *   caranya menyimpan data Enum nya ke Table dengan menggunakan annotation Eumerated
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/enumerated
     *
     * Enum Type
     * ● Saat menyimpan data Enum, JPA menyediakan dua strategy, menyimpan dalam bentuk Integer, atau menyimpan dalam bentuk String
     * ● Kita bisa memilih nya dengan menggunakan EnumType pada annotation Enumerated
     * ● Sangat disarankan menggunakan strategy String, karea strategy Integer bisa berubah ketika terjadi penambahan Enum Value pada posisi yang acak
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/enumtype
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
    void testInsertEnum(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
//        customer.setId("3");
        customer.setName("wawan");
        customer.setPrimaryEmail("wawan@test.com");
        customer.setAge((byte) 30);
        customer.setMarried(true);
        customer.setType(CustomerType.PREMIUM);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();

        /**
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
