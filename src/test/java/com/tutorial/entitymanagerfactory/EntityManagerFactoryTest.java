package com.tutorial.entitymanagerfactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerFactoryTest {

    @Test
    void testCreateEntityManagerFactory(){

        /**
         *  Entity Manager Factory
         *  Persistence.createEntityManagerFactory(nama);
         * ● Method tersebut akan membaca semua konfigurasi pada file META-INF/persistence.xml
         */

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");// EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) // Buat dan kembalikan EntityManagerFactory untuk unit persistensi bernama.

        Assertions.assertNotNull(entityManagerFactory.getCache());
        Assertions.assertNotNull(entityManagerFactory.getMetamodel());
        Assertions.assertNotNull(entityManagerFactory.getPersistenceUnitUtil());

        entityManagerFactory.close(); // void close() // Tutup pabrik, lepaskan semua sumber daya yang dimilikinya.

    }

}
