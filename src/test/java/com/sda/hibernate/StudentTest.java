package com.sda.hibernate;

import com.sda.hibernate.domain.Address;
import com.sda.hibernate.domain.Course;
import com.sda.hibernate.domain.Student;
import com.sda.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentTest {

//    pentru a nu crea studenti noi
//    @Ignore
    @Test
    public void createStudent(){
        Transaction tx=null;
        try(
                SessionFactory factory= HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
                ){
            tx=session.beginTransaction();

            Student student=new Student();
            student.setFirstname("Laurentiu");
            student.setLastname("Mihaiescu");
            student.setBirthDate(LocalDate.now());

//            Address address=new Address();
//            address.setStreet("Ion Mihalache, nr.59");
//            address.setCity("Bucuresti");
//            address=session.save(address);

            Address address=session.load(Address.class,1L);
            student.setAddress(address);

            Course course=session.load(Course.class,1L);
            //1. student.setCourses(new HashSet());
            //student.getCourses().add(course)
            //2. :
            Set<Course> courses=new HashSet<>();
            courses.add(course);
            student.setCourses(courses);

            session.save(student);
            tx.commit();
        }catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Test
    public void updateStudent(){
        Transaction tx=null;
        try(
                SessionFactory factory=HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
                ){
            tx=session.beginTransaction();

            Student student=session.load(Student.class,1L);
            student.setFirstname("Inocentiu");

            session.save(student);
//           OR session.persist(student);
//           OR session.saveOrUpdate(student);

            tx.commit();
        } catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Test
    public void testLoadAllStudents(){

        try(
                SessionFactory factory=HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
                ){

            TypedQuery<Student> query = session.createQuery("FROM Student",Student.class);
            List<Student> result = query.getResultList();
            result.stream().forEach(student -> {
                System.out.println("Prenume == "+student.getFirstname()+" nume == "+student.getLastname());
            });
        }
    }

    @Test
    public void testGetByLastName(){

        try(
                SessionFactory factory=HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
        ){

            TypedQuery<Student> query =
                    session.createQuery("SELECT s FROM Student s WHERE s.lastname= :numeFamilie",Student.class);
            query.setParameter("numeFamilie", "Mihaiescu");
            List<Student> result = query.getResultList();
            result.stream().forEach(student -> {
                System.out.println("Prenume == "+student.getFirstname()+" nume == "+student.getLastname());
            });
        }
    }

    @Test
    public void testNamedQuery(){

        try(
                SessionFactory factory=HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
        ){

            TypedQuery<Student> query =
                    session.createNamedQuery("Student.getByLastName",Student.class);
            query.setParameter("numeFamilie", "Mihaiescu");
            List<Student> result = query.getResultList();
            result.stream().forEach(student -> {
                System.out.println("Prenume == "+student.getFirstname()+" nume == "+student.getLastname());
            });
        }
    }

    @Test
    public void testNativeSQL(){

        try(
                SessionFactory factory=HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
        ){

            Query query =
                    session.createNativeQuery("SELECT s.first_name," +
                            "s.last_name," +
                            "a.street," +
                            "a.city FROM student s JOIN address a ON a.id=s.address_id");
            List<Object[]> result = query.getResultList();
            //forEach -> for each ROW
            result.stream().forEach(object -> {
                System.out.println("firstname == "+object[0]+", lastname == "+object[1]+", street == "+object[2]+
                        ", city == "+object[3]);
            });

        }
    }

}
