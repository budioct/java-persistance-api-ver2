package com.tutorial.entity;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentTest {

    private static final Logger log = LoggerFactory.getLogger(DepartmentTest.class);

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
    void testInsertEmbbeddeId2columnPrimaryKey(){

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
