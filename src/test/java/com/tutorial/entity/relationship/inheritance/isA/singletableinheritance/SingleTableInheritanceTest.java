package com.tutorial.entity.relationship.inheritance.isA.singletableinheritance;

import com.tutorial.entity.relationship.inheritance.singletable.Employee;
import com.tutorial.entity.relationship.inheritance.singletable.Manager;
import com.tutorial.entity.relationship.inheritance.singletable.VicePresident;
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
public class SingleTableInheritanceTest {

    /**
     * Single Table Inheritance
     * ● Single Table Inheritance artinya kita menyimpan seluruh Entity untuk relasi IS-A dalam satu table
     * ● Artinya semua kolom di semua Entity akan digabung dalam satu table
     * ● + Plus nya dari strategi ini adalah mudah dan cepat, tidak butuh melakukan join
     * ● - Minus nya, kita harus membuat semua kolom jadi nullable, karena tiap record hanya milik satu Entity
     * <p>
     * Parent Entity
     * ● Saat membuat Entity untuk IS-A Relationship, kita perlu membuat Parent Entity nya terlebih dahulu
     * ● Parent entity berisikan attribute yang tersedia di semua Child Entity nya
     * ● Dan khusus untuk Parent Entity, kita harus menyebutkan strategy Inheritance nya menggunakan annotation @Inheritance
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/inheritance
     * ● Pada kasus Single Table Inheritance, kita wajib menggunakan Inheritance Type SINGLE_TABLE
     * <p>
     * Child Entity
     * ● Untuk Child Entity, kita cukup extends class Parent Entity
     * ● Pada kasus Single Table Inheritance, kita perlu memberi tahu JPA dari kolom dan value apa Child Entity tersebut dipilih
     * ● Oleh karena itu, kita perlu menambahkan annotation @DiscriminatorColumn pada Parent
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/discriminatorcolumn
     * ● Dan pada Child Entity, kita harus menambahkan annotation @DiscriminatorValue
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/discriminatorvalue
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
    void testInsertEmployeeSingleTableInheritance() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); // void begin()

        Employee employee = new Employee();
        employee.setId("rina");
        employee.setName("Rina Wati");
        entityManager.persist(employee); // void persist(Object var1)

        Manager manager = new Manager();
        manager.setId("joko");
        manager.setName("Joko Morro");
        manager.setTotalEmployee(10);
        entityManager.persist(manager);

        VicePresident vp = new VicePresident();
        vp.setId("budhi");
        vp.setName("Budhi Octaviansyah");
        vp.setTotalManager(5);
        entityManager.persist(vp);

        entityTransaction.commit(); // void commit()

        Assertions.assertEquals("rina", employee.getId()); // static void assertEquals(Object expected, Object actual)
        Assertions.assertEquals(10, manager.getTotalEmployee());
        Assertions.assertEquals(5, vp.getTotalManager());

        entityManager.close(); // void close()

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         employees
         *         (name,type,id)
         *     values
         *         (?,'EMPLOYEE',?)
         * Hibernate:
         *     insert
         *     into
         *         employees
         *         (name,total_employee,type,id)
         *     values
         *         (?,?,'MANAGER',?)
         * Hibernate:
         *     insert
         *     into
         *         employees
         *         (name,total_manager,type,id)
         *     values
         *         (?,?,'VP',?)
         */

    }

    @Test
    void testFindEntitySingleTableInheritance() {

        /**
         * Find Entity IS-A
         * ● Pada kasus relasi ISA, kita bisa find data langsung spesifik ke Child Entity, atau lewat Parent Entity
         * ● Selain itu, pada kasus jika kita melakukan Find menggunakan Parent Entity, dan ternyata data
         *   tersebut adalah Child Entity, kita bisa konversi secara manual
         *
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); // void begin()

        Employee budhi = entityManager.find(Employee.class, "budhi");
        Employee joko = entityManager.find(Employee.class, "joko");

        VicePresident vicePresident = (VicePresident) budhi; // Polymophism(terkait inheritance) konversi berubah bentuk dari parent ke child
        Manager manager = (Manager) joko;

        entityTransaction.commit(); // void commit()

        Assertions.assertEquals(5, vicePresident.getTotalManager()); // static void assertEquals(Object expected, Object actual)
        Assertions.assertEquals(10, manager.getTotalEmployee());

        entityManager.close(); // void close()

        /**
         * result query:
         * Hibernate:
         *     select
         *         e1_0.id,
         *         e1_0.type,
         *         e1_0.name,
         *         e1_0.total_employee,
         *         e1_0.total_manager
         *     from
         *         employees e1_0
         *     where
         *         e1_0.id=?
         * Hibernate:
         *     select
         *         e1_0.id,
         *         e1_0.type,
         *         e1_0.name,
         *         e1_0.total_employee,
         *         e1_0.total_manager
         *     from
         *         employees e1_0
         *     where
         *         e1_0.id=?
         */

    }


}
