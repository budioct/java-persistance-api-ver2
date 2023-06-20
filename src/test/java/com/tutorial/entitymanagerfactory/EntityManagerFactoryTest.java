package com.tutorial.entitymanagerfactory;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
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

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
        log.info("ini entity manager factory");
        this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @Test
    void testbro(){

        // test entity manager
        entityManagerFactory.createEntityManager().getTransaction().begin();
    }

}
