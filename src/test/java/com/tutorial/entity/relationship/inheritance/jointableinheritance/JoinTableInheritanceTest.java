package com.tutorial.entity.relationship.inheritance.jointableinheritance;

import com.tutorial.entity.relationship.inheritance.jointable.Payment;
import com.tutorial.entity.relationship.inheritance.jointable.PaymentCreditCard;
import com.tutorial.entity.relationship.inheritance.jointable.PaymentGopay;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class JoinTableInheritanceTest {

    /**
     * Joined Table Inheritance
     * ● Selanjutnya implementasi IS-A adalah menggunakan Join Table
     * ● Yang artinya, tiap Child Entity memiliki table masing-masing, namun akan melakukan join primary key dengan table Parent Entity
     * ● Pada Joined Table Inheritance, kita tidak perlu menggunakan Discriminator Column lagi, karena data nya sudah terpisah table
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
    void testInsertPaymentJoinTableInheritance() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        PaymentGopay gopay = new PaymentGopay();
        gopay.setId("gopay2");
        gopay.setAmount(120_000L);
        gopay.setGopayId("08999912285");
        entityManager.persist(gopay);

        PaymentCreditCard creditCard = new PaymentCreditCard();
        creditCard.setId("cc2");
        creditCard.setAmount(200_000L);
        creditCard.setMaskedCard("4555-22222");
        creditCard.setBank("BNI");
        entityManager.persist(creditCard);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         payments
         *         (amount,id)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         payments_gopay
         *         (gopay_id,id)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         payments
         *         (amount,id)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         payments_credit_card
         *         (bank,masked_card,id)
         *     values
         *         (?,?,?)
         */

    }

    @Test
    void testFindPaymentJoinTableInheritance() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Payment payment = entityManager.find(Payment.class, "cc2");

        PaymentCreditCard paymentCreditCard = (PaymentCreditCard) payment;

        Assertions.assertEquals("BNI", paymentCreditCard.getBank());
        Assertions.assertEquals("4555-22222", paymentCreditCard.getMaskedCard());

        log.info("Bank: {}", paymentCreditCard.getBank());
        log.info("masked_card: {}", paymentCreditCard.getMaskedCard());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         p1_0.id,
         *         case
         *             when p1_1.id is not null then 1
         *             when p1_2.id is not null then 2
         *             when p1_0.id is not null then 0
         *         end,
         *         p1_0.amount,
         *         p1_1.bank,
         *         p1_1.masked_card,
         *         p1_2.gopay_id
         *     from
         *         payments p1_0
         *     left join
         *         payments_credit_card p1_1
         *             on p1_0.id=p1_1.id
         *     left join
         *         payments_gopay p1_2
         *             on p1_0.id=p1_2.id
         *     where
         *         p1_0.id=?
         */

    }


}
