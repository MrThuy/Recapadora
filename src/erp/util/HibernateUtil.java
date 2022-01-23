/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import erp.controles.CConfiguracoes;
import java.net.ConnectException;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Cliente
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
    private static boolean conectou;

    static {
        abreConexao();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        try {
            sessions.set(sessionFactory.openSession());
            return sessions.get();
        } catch (Exception err) {
            return null;
        }
    }

    public static void closeSession() {
        sessions.get().close();
        sessions.set(null);
    }

    public static void abreConexao() {
        try {
            conectou = false;
            Configuration config = new Configuration();

            config.configure();

            String ip = CConfiguracoes.carregaIP_Banco();
            String porta = CConfiguracoes.carregaPorta_Banco();
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            config.setProperty("hibernate.connection.url", "jdbc:mysql://" + ip + ":" + porta + "/uniao");
            config.setProperty("hibernate.connection.username", "uniao");
            config.setProperty("hibernate.connection.password", "cargapesada");

            sessionFactory = config.buildSessionFactory();

            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();            
        } catch (Throwable ex) {
            // Log the exception. 
            JOptionPane.showMessageDialog(null, ex.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static boolean isConnected() {
        try {

            Session session = openSession();
            session.connection().getTransactionIsolation();
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getCause().getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
