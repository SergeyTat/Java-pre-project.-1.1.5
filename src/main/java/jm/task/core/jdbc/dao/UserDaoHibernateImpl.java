package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS user(" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "name varchar(255)," +
                "lastname varchar(255)," +
                "age int)";
        Transaction tr = null;


        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            tr.commit();
            System.out.println("Таблица создана");

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;


        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USER").executeUpdate();
            tr.commit();
            System.out.println("Таблица Удалена");

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction tr = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory(); Session session = sessionFactory.getCurrentSession();) {
            tr = session.beginTransaction();
            session.save(user);
            tr.commit();

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory(); Session session = sessionFactory.getCurrentSession();) {
            tr = session.beginTransaction();
//            User user= session.get(User.class,id);
//            session.delete(user);
            session.createQuery("DELETE User where id=" + id).executeUpdate();
            tr.commit();

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> list = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory(); Session session = sessionFactory.getCurrentSession();) {
            tr = session.beginTransaction();
            list = session.createQuery("from User").list();
            tr.commit();

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        for (User y : list) {
            System.out.println(y);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory(); Session session = sessionFactory.getCurrentSession();) {
            tr = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            tr.commit();

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }

    }
}
