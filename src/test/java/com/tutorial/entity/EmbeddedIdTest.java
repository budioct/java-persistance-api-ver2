package com.tutorial.entity;

import com.tutorial.embeddedable.DepartmentId;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class EmbeddedIdTest {

    /**
     * Embedded ID
     * ● Id adalah representasi dari Primary Key di Table
     * ● Seperti yang sudah kita bahas di materi sebelumnya, Class Entity wajib memiliki Id
     * ● Namun bagaimana jika ternyata ada table dengan Primary Key lebih dari satu kolom?
     * ● Pada kasus seperti ini, kita tidak bisa membuat dua atribut dengan annotation Id, karena
     *   method-method yang terdapat di EntityManager hanya menggunakan 1 parameter sebagai ID
     * ● Oleh karena itu, kita perlu menggunakan Class Embedded
     * ● Dan untuk menandai bahwa atribut itu adalah Id, kita gunakan annotation EmbeddedId
     * ● Khusus untuk class Embedded untuk Primary Key, direkomendasikan implements Serializable
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embeddedid
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
    void testInsertEmbbeddeId2columnPrimaryKey() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("sb");
        departmentId.setDepartmentId("technologi");

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Teknologi");

        entityManager.persist(department);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         departments
         *         (name,company_id,department_id)
         *     values
         *         (?,?,?)
         */
    }



}
