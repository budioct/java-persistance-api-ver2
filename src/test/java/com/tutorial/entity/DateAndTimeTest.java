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

import java.time.LocalDateTime;
import java.util.Calendar;

@Slf4j
public class DateAndTimeTest {

    /**
     * Date dan Time
     * ● Selain tipe data dasar, JPA juga bisa melakukan mapping tipe data Date dan Time secara otomatis
     * ● Saat kita belajar JDBC, tipe data Date dan Time hanya terbatas pada tipe data di package java.sql
     * ● Namun jika menggunakan JPA, kita bisa menggunakan tipe data Date, Timestamp, Calendar,
     * bahkan class-class di Date and Time API seperti LocalDate, LocalTime dan lain-lain
     *
     * Mapping Date dan Time API
     * Class Java                               Tipe Data Database
     * java.sql.Date, java.time.LocalDate       DATE
     * java.sql.Time, java.time.LocalTime       TIME
     * java.sql.Timestamp,                     TIMESTAMP
     * java.time.Instant
     * java.time.LocalDateTime,
     * java.time.OffsetDateTime,
     * java.time.ZonedDateTime
     *
     * Java Util
     * ● Bagaimana dengan tipe data java.util.Date dan java.util.Calendar
     * ● Tipe data itu sedikit membingungkan karena bisa digunakan untuk Date, Time dan Timestamp di database
     * ● Oleh karena itu, direkomendasikan menggunakan package java.time
     * ● Namun jika menggunakan package java.util, disarankan menambahkan annotation Temporal,
     *   untuk memberi tahu detail tipe data di database, apakah Date, Time atau Timestamp
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/temporal
     *
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
    void testInsert2(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Food");
        category.setCreatedAt(Calendar.getInstance()); // Temporal
        category.setUpdateAt(LocalDateTime.now()); // java.time

        entityManager.persist(category);

        Assertions.assertNotNull(category);

        entityTransaction.commit();
        entityManager.close();

        /**
         * query result:
         * Hibernate:
         *     insert
         *     into
         *         categories
         *         (create_at,description,name,update_at)
         *     values
         *         (?,?,?,?)
         */

    }

}
