package com.tutorial.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    /**
     * CRUD
     * ● Untuk melakukan proses CRUD (Create, Read, Update, Delete) ke database, kita bisa menggunakan EntityManager
     * ● Secara otomatis JPA akan membuatkan perintah SQL untuk INSERT, UPDATE, DELETE dan SELECT dari Entity Class yang kita gunakan
     *
     * Entity Manager Method
     * Method               Keterangan
     * persist(entity)      Untuk menyimpan entity
     * find(Class, id)      Untuk mendapatkan entity berdasarkan id
     * merge(entity)        Untuk mengupdate entity
     * remove(entity)       Untuk menghapus entity
     *
     */

    @Test
    void insertEntityTest(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("ismail");
        customer.setPrimaryEmail("ismail@gmail.com");
        entityManager.persist(customer); // void persist(Object var1) // Untuk menyimpan entity

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result:
         * Hibernate:
         *     insert
         *     into
         *         customers
         *         (name,id)
         *     values
         *         (?,?)
         */

    }

    @Test
    void findEntityTest(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1"); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id
        Assertions.assertEquals("1", customer.getId());
        Assertions.assertEquals("jamal", customer.getName());
        Assertions.assertEquals("jamal@gmail.com", customer.getPrimaryEmail());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.name
         *     from
         *         customers c1_0
         *     where
         *         c1_0.id=?
         */

    }

    @Test
    void updateEntityTest(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1"); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id
        customer.setName("abdul rojak");
        customer.setPrimaryEmail("rojak@gmail.com");
        entityManager.merge(customer); // <T> T merge(T var1) // Untuk mengupdate entity

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.name
         *     from
         *         customers c1_0
         *     where
         *         c1_0.id=?
         * Hibernate:
         *     update
         *         customers
         *     set
         *         name=?
         *     where
         *         id=?
         */

    }

    @Test
    void deleteEntityTest(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1"); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id
        entityManager.remove(customer); // void remove(Object var1) // Untuk menghapus entity

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.name
         *     from
         *         customers c1_0
         *     where
         *         c1_0.id=?
         * Hibernate:
         *     delete
         *     from
         *         customers
         *     where
         *         id=?
         */

    }

}
