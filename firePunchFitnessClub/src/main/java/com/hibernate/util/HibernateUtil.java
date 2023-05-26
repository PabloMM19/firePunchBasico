package com.hibernate.util;

import java.util.Properties;

import org.hibernate.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;
import org.hibernate.service.*;

import com.hibernate.model.Categoria;
import com.hibernate.model.Cliente;
import com.hibernate.model.Ejercicio;


/**
 * Clase de utilidad para configurar y obtener la sesión de Hibernate.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    /**
     * Obtiene la sesión de Hibernate.
     * Si no se ha configurado previamente, se realiza la configuración y se crea la sesión.
     * 
     * @return la sesión de Hibernate
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                // Configuración de la conexión a la base de datos
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3307/firePunchFitnessClub?useSSL=false");
                settings.put(Environment.USER, "alumno");
                settings.put(Environment.PASS, "alumno");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                // Agregar las clases anotadas que se utilizarán con Hibernate
                configuration.addAnnotatedClass(Cliente.class);
                configuration.addAnnotatedClass(Ejercicio.class);
                configuration.addAnnotatedClass(Categoria.class);
                // Crear el registro de servicios y construir la sesión de fábrica
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}