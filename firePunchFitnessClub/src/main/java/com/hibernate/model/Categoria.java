package com.hibernate.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoriaEjercicio")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idCategoria")
	/*Almacena el id de la categoría*/
	private int idCategoria;
	@Column(name = "nombreCategoria")
	/*Almacena el nombre de la categoría*/
	private String nombreCategoria;
	
	@OneToMany
	/*Almacena la lista de ejercicios que pertenecen a una categoría y relaciona la tabla categoriaEjercicio con ejercicio*/
	private List<Ejercicio> ejercicios;
	
	/*Constructor vacío necesario para Hibernate*/
	public Categoria() {
		super();
	}
	
	/**
	 * @param nombreCategoria almacena el objeto categoría
	 */
	public Categoria(String nombreCategoria) {
		super();
		this.nombreCategoria = nombreCategoria;
	}

	/**
	 * @return idCategoria que nos indica el id de la categoría almacenada en la bbdd
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria actualiza el id de la categoría en la bbdd
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the nombreCategoria que nos indica el nombre de la categoría almacenada en la bbdd
	 */
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	/**
	 * @param nombreCategoria actualiza el nombre de la categoría en la bbdd
	 */
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
}
