package com.tutorial.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {

    @Test
    void testCreateEntityManager(){

        /**
         * EntityManagerFactory itu mirip seperti DataSource, digunakan untuk melakukan management EntityManager
         * EntityManager mirip seperti Connection pada JDBC, dimana jika kita ingin berinteraksi dengan database, maka kita akan menggunakan EntityManager
         */

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKCOY"); // EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) // Buat dan kembalikan EntityManagerFactory untuk unit persistensi bernama.
        EntityManager entityManager = entityManagerFactory.createEntityManager(); // EntityManager createEntityManager() // Buat aplikasi baru yang dikelola EntityManager.

        entityManager.close(); // void close() // Tutup manajer entitas yang dikelola aplikasi.
        entityManagerFactory.close(); // void close() // Tutup pabrik, lepaskan semua sumber daya yang dimilikinya.

    }

}
