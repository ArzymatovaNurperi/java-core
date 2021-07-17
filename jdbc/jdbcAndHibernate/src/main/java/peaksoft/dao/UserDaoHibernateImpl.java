package peaksoft.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {


    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id serial PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT NULL, " +
                    "age int2 NOT NULL)";

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
            session.close();
            System.out.println("Table succesfully created");

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE if exists users").executeUpdate();

            session.getTransaction().commit();
            session.close();
            System.out.println(" Table droped succesfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("User cuccesfully saved");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction=null;
      try{  Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        User u = session.get(User.class, id);
        session.delete(u);
        session.getTransaction().commit();
        session.close();
        System.out.println("User with id-" + id + " was delete" + u);
    }catch (HibernateException e){
          if(transaction!=null){
              transaction.rollback();
          }
          System.out.println(e.getMessage());
      }
      }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User").list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("truncate users").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("All users were delete");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
