package cursojava.gestorpedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


/**
 * Gestor de pedidos.
 */
public class App {
    private static SessionFactory factory;
    private static Session session;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("Bienvenido al Gestor de Pedidos.");

        factory = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/gestorpedidos")
                .setProperty("hibernate.connection.username", "jairo")
                .setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.hbm2ddl.auto", "update") // Crea las tablas en la BD si no existen
                .setProperty("hibernate.show_sql", "false")    // Para poder ver qué hace Hibernate
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(LineaPedido.class)
                .addAnnotatedClass(Pedido.class)
                .buildSessionFactory();

        session = factory.openSession();

        int opcion = -1;
        
        scanner = new Scanner(System.in);

        while (opcion != 0) {
            // Menú de opciones
            System.out.println("Menú de opciones:");
            System.out.println("1. Generar un pedido");
            System.out.println("2. Consultar pedidos");
            System.out.println("3. Añadir cliente");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Añadir artículo");
            System.out.println("7. Modificar artículo");
            System.out.println("8. Eliminar artículo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción> ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            try {
                switch (opcion) {
                    case 1:
                        // Lógica para generar un pedido
                        System.out.println("Generando un pedido...");
                        generarPedido();
                        break;
                    case 2:
                        // Lógica para consultar pedidos
                        System.out.println("Consultando pedidos...");
                        consultarPedidos();
                        break;
                    case 3:
                        System.out.println("Añadir cliente...");
                        anadirCliente();
                        break;
                    case 4:
                        System.out.println("Modificar cliente...");
                        modificarCliente();
                        break;
                    case 5:
                        System.out.println("Eliminar cliente...");
                        eliminarCliente();
                        break;
                    case 6:
                        System.out.println("Añadir artículo...");
                        anadirArticulo();
                        break;
                    case 7:
                        System.out.println("Modificar artículo...");
                        modificarArticulo();
                        break;
                    case 8:
                        System.out.println("Eliminar artículo...");
                        eliminarArticulo();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }





        // Cerrar cosas
        scanner.close();
        session.close();
        factory.close();
    }

    /**
     * Eliminación de un artículo.
     * Primeramente muestra una lista de artículos y pide seleccionar uno indicando su ID.
     * A continuación, elimina el artículo seleccionado de la base de datos.
     */
    private static void eliminarArticulo() {
        System.out.println("Seleccione el artículo a eliminar:");
        Articulo articulo = seleccionarArticulo();

        Transaction t = session.beginTransaction();
        session.remove(articulo);
        t.commit();
    }

    /**
     * Método para modificar un artículo existente en la base de datos de forma interactiva.
     * Primero muestra una lista de artículos y permite seleccionar uno indicando su ID.
     * A continuación, solicita los nuevos datos del artículo y actualiza la información en la base
     * de datos.
     */
    private static void modificarArticulo() {
        System.out.println("Seleccione el artículo a modificar:");
        Articulo articulo = seleccionarArticulo();

        System.out.print("Nueva denominación del artículo (actual: " + articulo.getDenominacion() + ")> ");
        String nuevaDenominacion = scanner.nextLine();
        System.out.print("Nuevo precio unitario del artículo (actual: " + articulo.getPrecioUnitario() + ")> ");
        BigDecimal nuevoPrecioUnitario = scanner.nextBigDecimal();
        scanner.nextLine(); // Consumir el salto de línea
        
        articulo.setDenominacion(nuevaDenominacion);
        articulo.setPrecioUnitario(nuevoPrecioUnitario);

        Transaction t = session.beginTransaction();
        session.merge(articulo);
        t.commit();
    }


    /**
     * Método para añadir un nuevo artículo a la base de datos, de forma interactiva.
     * Solicita al usuario los datos del artículo y lo guarda en la base de datos.
     */
    private static void anadirArticulo() {
        System.out.print("Denominación del artículo> ");
        String denominacion = scanner.nextLine();
        System.out.print("Precio unitario del artículo> ");
        BigDecimal precioUnitario = scanner.nextBigDecimal();
        scanner.nextLine(); // Consumir el salto de línea

        Articulo articulo = new Articulo();
        articulo.setDenominacion(denominacion);
        articulo.setPrecioUnitario(precioUnitario);

        Transaction t = session.beginTransaction();
        session.persist(articulo);
        t.commit();

        System.out.println("Artículo añadido con ID: " + articulo.getId());
    }


    /**
     * Eliminación de un cliente existente en la base de datos de forma interactiva.
     * Primero muestra una lista de clientes y permite seleccionar uno indicando su ID.
     * A continuación, elimina el cliente seleccionado de la base de datos.
     */
    private static void eliminarCliente() {
        System.out.println("Seleccione el cliente a eliminar:");
        Cliente cliente = seleccionarCliente();

        Transaction t = session.beginTransaction();
        session.remove(cliente);
        t.commit();
    }

    /**
     * Método para modificar un cliente existente en la base de datos de forma interactiva.
     * Primero muestra una lista de clientes y permite seleccionar uno indicando su ID.
     * A continuación, solicita los nuevos datos del cliente y actualiza la información en la base
     * de datos.
     */
    private static void modificarCliente() {
        System.out.println("Seleccione el cliente a modificar:");
        Cliente cliente = seleccionarCliente();

        System.out.print("Nuevo nombre del cliente (actual: " + cliente.getNombre() + ")> ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nueva dirección del cliente (actual: " + cliente.getDireccion() + ")> ");
        String nuevaDireccion = scanner.nextLine();
        System.out.println("Observaciones actuales: " + cliente.getObservaciones());
        System.out.print("Nuevas observaciones del cliente> ");
        String nuevasObservaciones = scanner.nextLine();
        cliente.setNombre(nuevoNombre);
        cliente.setDireccion(nuevaDireccion);
        cliente.setObservaciones(nuevasObservaciones);

        Transaction t = session.beginTransaction();
        session.merge(cliente);
        t.commit();
    }

    /**
     * Método para añadir un nuevo cliente a la base de datos, de forma interactiva.
     * Solicita al usuario los datos del cliente y lo guarda en la base de datos.
     */
    private static void anadirCliente() {
        System.out.print("Nombre del cliente> ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección del cliente> ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);

        Transaction t = session.beginTransaction();
        session.persist(cliente);
        t.commit();

        System.out.println("Cliente añadido con ID: " + cliente.getId());
    }

    /**
     * Consulta los pedidos existentes en la base de datos, mostrándolos por pantalla.
     * La información que ha de mostrar incluye el ID del pedido, la fecha, el nombre del
     * cliente, las líneas del pedido y el importe total del pedido.
     */
    private static void consultarPedidos() {
        System.out.println("Pedidos existentes:");
        Query<Pedido> query = session.createQuery("select p from Pedido p join fetch p.cliente", Pedido.class);
        List<Pedido> pedidos = query.list();

        for (Pedido pedido : pedidos) {
            System.out.println("ID Pedido: " + pedido.getId());
            System.out.println("Fecha: " + pedido.getFecha());
            System.out.println("Cliente: " + pedido.getCliente().getNombre());
            System.out.println("Líneas del pedido:");
            
            Query<LineaPedido> query2 = session.createQuery("select lp from LineaPedido lp join fetch lp.articulo where lp.pedido.id = :pedidoId", LineaPedido.class);
            query2.setParameter("pedidoId", pedido.getId());
            List<LineaPedido> lineasPedido = query2.list();

            System.out.println("Artículo                                 | Cantidad | Precio Unitario");
            System.out.println("-----------------------------------------+----------+----------------");

            for (LineaPedido l : lineasPedido) {
                Articulo a = l.getArticulo();
                System.out.println(String.format("%-40s | %8d | %14.2f", a.getDenominacion(), l.getCantidad(), a.getPrecioUnitario()));
            }

            Query<BigDecimal> queryTotal = session.createQuery(
                "select sum(lp.cantidad * a.precioUnitario) from LineaPedido as lp join lp.articulo as a where lp.pedido = :pedidoId",
                BigDecimal.class
            );
            queryTotal.setParameter("pedidoId", pedido);
            BigDecimal total = queryTotal.getSingleResult();

            System.out.println("Importe total del pedido: " + total);

            System.out.println("--------------------------------------------------");
        }
    }

    /**
     * Genera un pedido de forma interactiva.
     * Se le solicita al usuario que seleccione un cliente, y a continuación
     * se le permite añadir artículos al pedido, indicando la cantidad de cada
     * uno de ellos.
     * Finalmente, se guarda el pedido y sus líneas en la base de datos.
     */
    private static void generarPedido() {
        // Seleccionar cliente, utilizando el método seleccionarCliente()
        System.out.println("Seleccione un cliente para el pedido:");
        Cliente cliente = seleccionarCliente();
        // Fecha del pedido (hoy)
        LocalDate fecha = LocalDate.now(); // fecha de hoy

        // Crear el pedido, con el cliente y la fecha seleccionados
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(fecha);
        
        Transaction t = session.beginTransaction();
        session.persist(pedido);

        // Bucle para añadir líneas al pedido.
        // Se interrumpirá cuando el usuario decida no añadir más artículos.
        while (true) {

            System.out.println("Seleccione un artículo para añadir al pedido:");
            Articulo articulo = seleccionarArticulo();
            System.out.print("Cantidad> ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            LineaPedido lp = new LineaPedido(pedido, articulo, cantidad);
            session.persist(lp);

            System.out.print("¿Desea añadir otro artículo? (s/n)> ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                break; // Salir del bucle; no se añaden más artículos
            }
        }

        t.commit();
    }

    /**
     * Permite seleccionar un artículo de la base de datos de forma interactiva.
     * Se le muestra al usuario una lista con los artículos disponibles y se le
     * solicita que introduzca el ID del artículo que desea seleccionar.
     * @return El artículo seleccionado.
     */
    private static Articulo seleccionarArticulo() {
        // Primera consulta para obtener una lista con todos los artículos
        Query<Articulo> query = session.createQuery("from Articulo", Articulo.class);
        List<Articulo> articulos = query.list();

        // Mostramos la lista de artículos, para que el usuario pueda elegir
        System.out.println("Artículos disponibles:");
        for (Articulo articulo : articulos) {
            System.out.println(articulo.getId() + "\t" + articulo.getDenominacion());
        }

        // Solciitamos ID de artículo al usuario
        System.out.print("ID del artículo> ");
        Long articuloId = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        // Segunda consulta que obtene el artículo seleccionado, identificado por su ID
        Articulo articuloSeleccionado = session.find(Articulo.class, articuloId);
        return articuloSeleccionado;
    }

    /**
     * Permite seleccionar un cliente de la base de datos de forma interactiva.
     * Se le muestra al usuario una lista con los clientes disponibles y se le
     * solicita que introduzca el ID del cliente que desea seleccionar.
     * @return El cliente seleccionado.
     */
    private static Cliente seleccionarCliente() {
        // Primera consulta para obtener una lista con todos los clientes
        Query<Cliente> query = session.createQuery("from Cliente c order by c.id", Cliente.class);
        List<Cliente> clientes = query.list();

        // Mostramos la lista de clientes, para que el usuario pueda elegir
        System.out.println("Clientes disponibles:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getId() + "\t" + cliente.getNombre());
        }

        // Solciitamos ID de cliente al usuario
        System.out.print("ID del cliente> ");
        Long clienteId = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        // Segunda consulta que obtene el cliente seleccionado, identificado por su ID
        Cliente clienteSeleccionado = session.find(Cliente.class, clienteId);
        return clienteSeleccionado;
    }
}
