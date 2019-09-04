package com.sda.hibernate;

import com.sda.hibernate.domain.Address;
import com.sda.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class AddressTest {

    @Test
    public void createAddress(){
        Transaction tx=null;
        try(
            SessionFactory factory= HibernateUtil.getSessionFactory();
            Session session=factory.openSession()
        ){
            tx=session.beginTransaction();

            Address address=new Address();
            address.setStreet("Iuliu Maniu, nr.15");
            address.setCity("Iasi");

            session.save(address);

            tx.commit();
        }catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
        }
    }

    @Test
    public void testExistentaInContext(){

        Transaction tx=null;
        try(
                SessionFactory factory= HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
        ){
            tx=session.beginTransaction();

           Address address=session.load(Address.class,2L);
           System.out.println("1.Exista entitate in context? "+session.contains(address));

           session.evict(address);
            System.out.println("2.Exista entitate in context? "+session.contains(address));

            tx.commit();
        }catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
        }
    }

    @Test
    public void testDetachedEntity(){

        Transaction tx=null;
        try(
                SessionFactory factory= HibernateUtil.getSessionFactory();
                Session session=factory.openSession()
        ){
            tx=session.beginTransaction();

            Address address=session.load(Address.class,2L);
            System.out.println("1.Exista entitate in context? "+session.contains(address));

            session.evict(address);
//            sau
//            session.clear();

            //incerc sa salvez adresa
            session.save(address);

            tx.commit();
        }catch (Exception ex){
            if(tx!=null){
                tx.rollback();
            }
        }
    }

}
