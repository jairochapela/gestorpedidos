package cursojava.gestorpedidos;

import java.time.LocalDate;

import javax.sound.sampled.Line;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/**
 * Gestor de pedidos.
 */
public class App {
    private static SessionFactory factory;
    private static Session session;

    public static void main(String[] args) {
        System.out.println("Bienvenido al Gestor de Pedidos.");

        factory = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/gestorpedidos")
                .setProperty("hibernate.connection.username", "jairo")
                .setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.hbm2ddl.auto", "update") // Crea las tablas en la BD si no existen
                .setProperty("hibernate.show_sql", "true")    // Para poder ver qué hace Hibernate
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(LineaPedido.class)
                .addAnnotatedClass(Pedido.class)
                .buildSessionFactory();

        session = factory.openSession();


        // Generar un pedido manualmente

        // Transaction t = session.beginTransaction();

        // Cliente c = session.find(Cliente.class, 1L);
        // session.persist(c);

        // Pedido p = new Pedido();
        // p.setCliente(c);

        // p.setFecha(LocalDate.now());
        // session.persist(p);

        // Articulo a1 = session.find(Articulo.class, 1L);
        // LineaPedido lp1 = new LineaPedido(p, a1, 2);
        // session.persist(lp1);

        // Articulo a2 = session.find(Articulo.class, 2L);
        // LineaPedido lp2 = new LineaPedido(p, a2, 3);
        // session.persist(lp2);

        // Articulo a3 = session.find(Articulo.class, 3L);
        // LineaPedido lp3 = new LineaPedido(p, a3, 5);
        // session.persist(lp3);


        // t.commit();


        // Consultar un pedido
        Transaction t2 = session.beginTransaction();
        Pedido pedido = session.createQuery("from Pedido p where p.cliente.nombre = :nombre", Pedido.class)
            .setParameter("nombre", "Claudina Lutsch")
            .getSingleResult();
        System.out.println("Pedido encontrado: " + pedido);

        t2.commit();

        // Cerrar cosas
        session.close();
        factory.close();
    }
}
