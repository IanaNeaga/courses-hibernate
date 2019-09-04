package com.sda.hibernate;

import com.sda.hibernate.domain.Teacher;
import com.sda.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;


public class TeacherTest {
    // CRUD - Create, Update, Read, Delete

    @Test
    public void createTeacher(){
        // 1. Get SessionFactory
        // 2. Get Session
        Transaction tx = null;
        try (
                SessionFactory factory = HibernateUtil.getSessionFactory();
                Session session = factory.openSession();
             ){

            // Pentru ca vreau sa fac un insert, am nevoie de tranzactie
            tx = session.beginTransaction();

            // creez un profesor
            Teacher teacher = new Teacher();
            teacher.setFirstname("Alexandru");
            teacher.setLastname("Popescu");
            teacher.setHireDate(new Date());
            teacher.setSalary(100D);

            // salvez profesor
            session.save(teacher);

            // commit tranzactia
            tx.commit();
        } catch (Exception ex){
            // rollback tranzactie
            if (null != tx) {
                tx.rollback();
            }

            ex.printStackTrace();
        }
    }
}
