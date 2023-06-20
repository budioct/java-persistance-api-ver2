package com.tutorial.entity.relationship.inheritance.isA.tableperclassinheritance;

import com.tutorial.entity.relationship.inheritance.tableperclass.Transaction;
import com.tutorial.entity.relationship.inheritance.tableperclass.TransactionCredit;
import com.tutorial.entity.relationship.inheritance.tableperclass.TransactionDebit;
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

@Slf4j
public class TablePerClassInheritanceTest {

    /**
     * Table Per Class Inheritance
     * ● Strategi terakhir untuk implementasi IS-A adalah dengan Table per Class
     * ● Yang artinya tiap Entity akan dibuatkan table masing-masing, artinya Parent Entity dan Child Entity akan memiliki table masing-masing
     * ● Strategi ini mirip seperti dengan JOIN, namun tiap table menyimpann full kolom
     * ● Harap bijak ketika menggunakan strategi ini, walaupun akan jadi lebih cepat kalo kita langsung
     *   melakukan find Child Entity (karena tidak butuh join), tapi saat melakukan find menggunakan
     *   Parent Entity, maka akan sangat lambat karena harus melakukan SELECT from SELECT
     */

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        log.info("On Entity Manager Factory");
        this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @AfterEach
    void tearDown() {
        log.info("Off Entity Manager Factory");
        this.entityManagerFactory.close();
    }

    @Test
    void testInsertTransactionInheritanceTablePerClass() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Transaction transaction = new Transaction();
        transaction.setId("T1");
        transaction.setCreateAt(LocalDateTime.now());
        transaction.setBalance(1_000_000L);
        entityManager.persist(transaction);

        TransactionDebit transactionDebit = new TransactionDebit();
        transactionDebit.setId("T2");
        transactionDebit.setCreateAt(LocalDateTime.now());
        transactionDebit.setBalance(2_000_000L);
        transactionDebit.setDebitAmount(1_000_000L);
        entityManager.persist(transactionDebit);

        TransactionCredit transactionCredit = new TransactionCredit();
        transactionCredit.setId("T3");
        transactionCredit.setCreateAt(LocalDateTime.now());
        transactionCredit.setBalance(1_000_000L);
        transactionCredit.setCreditAmount(1_000_000L);
        entityManager.persist(transactionCredit);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         transactions
         *         (balance,create_at,id)
         *     values
         *         (?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         transactions_debit
         *         (balance,create_at,debit_amount,id)
         *     values
         *         (?,?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         transactions_creadit
         *         (balance,create_at,credit_amount,id)
         *     values
         *         (?,?,?,?)
         */
    }

    @Test
    void testFindTransactionInheritanceTablePerClass() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Transaction credit = entityManager.find(Transaction.class, "T3");
//        Transaction debit = entityManager.find(Transaction.class, "T2");

        TransactionCredit transactionCredit = (TransactionCredit) credit;
//        TransactionDebit transactionDebit = (TransactionDebit) debit;

        Assertions.assertEquals(1000000L, transactionCredit.getCreditAmount());

        log.info("transaction credit: {}", transactionCredit.getCreditAmount());
//        log.info("transaction debit: {}", transactionDebit.getDebitAmount());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         t1_0.id,
         *         t1_0.clazz_,
         *         t1_0.balance,
         *         t1_0.create_at,
         *         t1_0.credit_amount,
         *         t1_0.debit_amount
         *     from
         *         ( select
         *             id,
         *             balance,
         *             create_at,
         *             null as debit_amount,
         *             null as credit_amount,
         *             0 as clazz_
         *         from
         *             transactions
         *         union
         *         all select
         *             id,
         *             balance,
         *             create_at,
         *             debit_amount,
         *             null as credit_amount,
         *             1 as clazz_
         *         from
         *             transactions_debit
         *         union
         *         all select
         *             id,
         *             balance,
         *             create_at,
         *             null as debit_amount,
         *             credit_amount,
         *             2 as clazz_
         *         from
         *             transactions_creadit
         *     ) t1_0
         * where
         *     t1_0.id=?
         */
    }


}
