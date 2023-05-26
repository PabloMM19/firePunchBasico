package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Ejercicio;
import com.hibernate.util.HibernateUtil;

public class EjercicioDAO {
	/**
	 * Inserta un nuevo ejercicio en la base de datos.
	 * 
	 * @param e el ejercicio a insertar
	 */
	public void insertEjercicio(Ejercicio e) {
	    Transaction transaction = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.persist(e);
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Actualiza un ejercicio existente en la base de datos.
	 * 
	 * @param e el ejercicio a actualizar
	 */
	public void updateEjercicio(Ejercicio e) {
	    Transaction transaction = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.merge(e);
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Elimina un ejercicio de la base de datos dado su código.
	 * 
	 * @param codigo el código del ejercicio a eliminar
	 */
	public void deleteEjercicio(int codigo) {
	    Transaction transaction = null;
	    Ejercicio e = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        e = session.get(Ejercicio.class, codigo);
	        session.remove(e);
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Obtiene todos los ejercicios almacenados en la base de datos.
	 * 
	 * @return una lista de todos los ejercicios
	 */
	public List<Ejercicio> selectAllEjercicio() {
	    Transaction transaction = null;
	    List<Ejercicio> ejercicios = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        ejercicios = session.createQuery("from Ejercicio", Ejercicio.class).getResultList();
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }

	    return ejercicios;
	}

	/**
	 * Obtiene un ejercicio de la base de datos dado su código.
	 * 
	 * @param codigo el código del ejercicio
	 * @return el ejercicio encontrado o null si no existe
	 */
	public Ejercicio selectEjercicioById(int codigo) {
	    Transaction transaction = null;
	    Ejercicio e = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        e = session.get(Ejercicio.class, codigo);
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }

	    return e;
	}

	/**
	 * Obtiene un ejercicio de la base de datos dado su nombre.
	 * 
	 * @param nombre el nombre del ejercicio
	 * @return el ejercicio encontrado o null si no existe
	 */
	public Ejercicio selectEjercicioByNombre(String nombre) {
	    Transaction transaction = null;
	    Ejercicio e = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        Query<Ejercicio> query = session.createQuery("FROM Ejercicio WHERE nombre = :nombreParam", Ejercicio.class);
	        query.setParameter("nombreParam", nombre);
	        e = query.uniqueResult();
	        transaction.commit();
	    } catch (Exception ex) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }

	    return e;
	}
}