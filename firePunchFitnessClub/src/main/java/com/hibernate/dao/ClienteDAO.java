package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Cliente;
import com.hibernate.util.HibernateUtil;

public class ClienteDAO {
	/**
	 * Inserta un nuevo cliente en la base de datos.
	 * 
	 * @param c el cliente a insertar
	 */
	public void insertCliente(Cliente c) {
	    Transaction transaction = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.persist(c);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Actualiza un cliente existente en la base de datos.
	 * 
	 * @param c el cliente a actualizar
	 */
	public void updateCliente(Cliente c) {
	    Transaction transaction = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.merge(c);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Elimina un cliente de la base de datos dado su código.
	 * 
	 * @param codigo el código del cliente a eliminar
	 */
	public void deleteCliente(int codigo) {
	    Transaction transaction = null;
	    Cliente c = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        c = session.get(Cliente.class, codigo);
	        session.remove(c);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }
	}

	/**
	 * Obtiene todos los clientes almacenados en la base de datos.
	 * 
	 * @return una lista de todos los clientes
	 */
	public List<Cliente> selectAllCliente() {
	    Transaction transaction = null;
	    List<Cliente> clientes = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        clientes = session.createQuery("from Cliente", Cliente.class).getResultList();
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }

	    return clientes;
	}

	/**
	 * Obtiene todos los clientes asociados a un entrenador dado su ID.
	 * 
	 * @param idEntrenador el ID del entrenador
	 * @return una lista de clientes asociados al entrenador
	 */
	public List<Cliente> selectClientesByIdEntrenador(int idEntrenador) {
	    Transaction transaction = null;
	    List<Cliente> clientesDeEntrenador = null;

	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        Query<Cliente> query = session.createQuery("FROM Cliente WHERE entrenador.idEntrenador = :idEntrenadorParam", Cliente.class);
	        query.setParameter("idEntrenadorParam", idEntrenador);
	        clientesDeEntrenador = query.getResultList();
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    }

	    return clientesDeEntrenador;
	}

	/**
	 * Obtiene todos los clientes asociados a un entrenador dado su ID, incluyendo los clientes sin asignar entrenador.
	 * 
	 * @param idEntrenador el ID del entrenador
	 * @return una lista de clientes asociados al entrenador y clientes sin asignar
	 */
	public List<Cliente> selectClientesPorEntrenadorYSinAsignar(int idEntrenador) {

		Transaction transaction = null;
		List<Cliente> clientesDeEntrenador = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<Cliente> query=session.createQuery("FROM Cliente WHERE entrenador.idEntrenador = :idEntrenadorParam OR entrenador.idEntrenador = 10000",Cliente.class);
			query.setParameter("idEntrenadorParam", idEntrenador);
			clientesDeEntrenador=query.getResultList();

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {

				transaction.rollback();
			}
		}
		return clientesDeEntrenador;
	}
	
	/**
	 * Obtiene todos los clientes asociados mediante su ID.
	 * 
	 * @param codigo el ID del cliente
	 * @return un cliente
	 */
	public Cliente selectClienteById(int codigo) {

		Transaction transaction = null;
		Cliente c = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			c = session.get(Cliente.class, codigo);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {

				transaction.rollback();
			}
		}
		return c;
	}
}