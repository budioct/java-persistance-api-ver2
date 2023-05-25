package com.tutorial.entitytransaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class EntityTransactionTest {

    /**
     * Saat kita menggunakan database, fitur yang wajib kita mengerti adalah database transaction
     * Saat menggunakan JDBC, secara default operasi ke DB adalah auto commit, sehingga kita tidak perlu membuat transaction
     * Namun, di JPA, secara default kita diwajibkan menggunakan database transaction saat melakukan operasi manipulasi data entity
     */

    @Test
    void testCreateTransaction() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKCOY"); // EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) // Buat dan kembalikan EntityManagerFactory untuk unit persistensi bernama.
        EntityManager entityManager = entityManagerFactory.createEntityManager(); // EntityManager createEntityManager() // Buat aplikasi baru yang dikelola EntityManager.
        EntityTransaction transaction = entityManager.getTransaction(); // EntityTransaction getTransaction() // Kembalikan objek tingkat sumber daya EntityTransaction.

        try {
            transaction.begin(); // void begin() // Mulai transaksi sumber daya.
            // LAKUKAN SESUATU
            transaction.commit(); // void commit() // Komit transaksi sumber daya saat ini, tulis perubahan apa pun yang belum dihapus ke database.
        } catch (Throwable throwable) {
            transaction.rollback(); // void rollback() // Putar kembali transaksi sumber daya saat ini.
        }

        entityManager.close(); // void close() // Tutup manajer entitas yang dikelola aplikasi.
        entityManagerFactory.close(); // void close() // Tutup pabrik, lepaskan semua sumber daya yang dimilikinya.

    }

}
