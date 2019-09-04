package com.sda.hibernate;

import com.sda.hibernate.domain.Course;
import com.sda.hibernate.domain.Teacher;
import com.sda.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class CourseTest {

    @Test
    public void createTest(){
        Transaction tx=null;
        try(
                SessionFactory factory= HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
                ) {
            tx=session.beginTransaction();

            Course course=new Course();
            course.setCode("JAV_123");
            course.setDescription("Java Fundamentals");

            Teacher teacher=session.load(Teacher.class,2L);
            course.setTeacher(teacher);

            session.save(course);
            tx.commit();
        } catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
        }
    }
}
