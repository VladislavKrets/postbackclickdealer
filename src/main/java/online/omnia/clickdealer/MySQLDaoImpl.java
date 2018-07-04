package online.omnia.clickdealer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MySQLDaoImpl {
    private static Configuration masterDbConfiguration;
    private static SessionFactory masterDbSessionFactory;

    private static MySQLDaoImpl instance;

    static {
        masterDbConfiguration = new Configuration()
                .addAnnotatedClass(PostBackEntity.class)
                .addAnnotatedClass(CurrencyEntity.class)
                .addAnnotatedClass(ExchangeEntity.class)
                .configure("/hibernate.cfg.xml");

        Map<String, String> properties = FileWorkingUtils.iniFileReader();

        masterDbConfiguration.setProperty("hibernate.connection.password", properties.get("master_db_password"));
        masterDbConfiguration.setProperty("hibernate.connection.username", properties.get("master_db_username"));
        masterDbConfiguration.setProperty("hibernate.connection.url", properties.get("master_db_url"));

        while (true) {
            try {
                masterDbSessionFactory = masterDbConfiguration.buildSessionFactory();
                break;
            } catch (PersistenceException e) {
                try {
                    e.printStackTrace();
                    System.out.println("Can't connect to master db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
    public CurrencyEntity getCurrency(String currency) {
        Session session = null;
        CurrencyEntity currencyEntity = null;
        while (true) {
            try {
                session = masterDbSessionFactory.openSession();
                currencyEntity = session.createQuery("from CurrencyEntity where code=:currency", CurrencyEntity.class)
                        .setParameter("currency", currency)
                        .getSingleResult();
                break;
            } catch (NoResultException e) {
                currencyEntity = null;
                break;
            } catch (PersistenceException e) {
                e.printStackTrace();
                try {
                    System.out.println("Can't connect to db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.close();
        return currencyEntity;
    }

    public ExchangeEntity getExchange(int id) {
        Session session = null;
        ExchangeEntity exchangeEntity = null;
        while (true) {
            try {
                session = masterDbSessionFactory.openSession();
                exchangeEntity = session.createQuery("from ExchangeEntity where currency_id=:currencyId and time=(select max(time) from ExchangeEntity where currency_id=:currencyId)", ExchangeEntity.class)
                        .setParameter("currencyId", id)
                        .getSingleResult();

                break;
            } catch (PersistenceException e) {
                try {
                    System.out.println("Can't connect to db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        session.close();
        return exchangeEntity;
    }
    public void removePostback(PostBackEntity postBackEntity) {
        Session session = masterDbSessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete PostBackEntity where clickid=:clickid and prefix=:prefix")
                .setParameter("clickid", postBackEntity.getClickId())
                .setParameter("prefix", postBackEntity.getPrefix())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    public List<PostBackEntity> getPostbacksByClickidPrefix(String clickid, String prefix) {
        Session session = masterDbSessionFactory.openSession();
        List<PostBackEntity> postBackEntities = session.createQuery("from PostBackEntity where clickid=:clickid and prefix=:prefix", PostBackEntity.class)
                .setParameter("clickid", clickid)
                .setParameter("prefix", prefix)
                .getResultList();
        session.close();
        return postBackEntities;
    }

    public void updatePostback(PostBackEntity postBackEntity) {
        Session session = masterDbSessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update PostBackEntity p set p.sum=:psum where clickid=:clickid and prefix=:prefix")
                .setParameter("psum", postBackEntity.getSum())
                .setParameter("clickid", postBackEntity.getClickId())
                .setParameter("prefix", postBackEntity.getPrefix())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void addPostback(PostBackEntity postBackEntity) {
        Session session = masterDbSessionFactory.openSession();
        session.beginTransaction();
        session.save(postBackEntity);
        session.getTransaction().commit();
        session.close();
    }
    private MySQLDaoImpl() {
    }

    public static SessionFactory getMasterDbSessionFactory() {
        return masterDbSessionFactory;
    }

    public static synchronized MySQLDaoImpl getInstance() {
        if (instance == null) instance = new MySQLDaoImpl();
        return instance;
    }
}
