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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class ImageTest {

    /**
     * Saya mengalami masalah yang sama di aplikasi Spring Boot saya saat mengunggah file Gambar/Word/PDF dan memperbaikinya dengan langkah-langkah di bawah ini:
     *
     * Larutan
     *
     * Silakan lihat ukuran file yang Anda coba unggah.
     * Kemudian bandingkan ukuran file tersebut dengan ukuran Maks yang diizinkan oleh tipe FIELD Database yang dipetakan.
     * Misalnya untuk LOB:
     * TINYBLOB ≈ 255 byte, BLOB ≈ 64KB, MEDIUMBLOB ≈ 16MB dan LONGBLOB ≈ 4GB Run
     * Gunakan perintah Alter table sesuai kebutuhan Ruang Anda:
     *
     * ALTER TABLE 'TABLE_NAME' MODIFY 'FIELD_NAME' MEDIUMBLOB;
     *
     * Catatan: Selalu disarankan untuk menyimpan Tautan tetapi bukan file sebenarnya (berukuran besar) di DB.
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
    void testInsertImages() throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Image image = new Image();
        image.setName("Contoh Image");
        image.setDescription("Contoh Deksripsi Image");

        // proses Image
        //byte[] bytes = Files.readAllBytes(Path.of(getClass().getResource("/images/sample.png").getPath())); // path lerlalu panjang
        Path path = new java.io.File(getClass().getResource("/images/sample2.jpg").getFile()).toPath(); // mengambil resource di dalam resource nyaa
        byte[] bytes = Files.readAllBytes(path); // byte[] readAllBytes(Path path) // Membaca semua byte dari file. yang ada di path resource
        image.setImage(bytes);

        entityManager.persist(image);

        log.info("path: {}", path.getFileName());
        Assertions.assertEquals("sample2.jpg", image.getImage());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         images
         *         (description,image,name)
         *     values
         *         (?,?,?)
         */

    }



}
