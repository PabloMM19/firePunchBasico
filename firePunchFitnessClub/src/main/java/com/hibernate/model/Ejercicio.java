package com.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEjercicio")
	private int idEjercicio;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "series")
	private int series;
	@Column(name = "repeticiones")
	private int repeticiones;
	@Column(name = "cargaKg")
	private double cargaKg;

	@ManyToOne
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;

	@ManyToMany
	@JoinTable(name = "realiza_ejercicio", joinColumns = @JoinColumn(name = "idEjercicio"), inverseJoinColumns = @JoinColumn(name = "idCliente"))
	private List<Cliente> clientes = new ArrayList<Cliente>();

	/**
	 * 
	 */
	public Ejercicio() {
		super();
	}

	/**
	 * @param nombre
	 * @param series
	 * @param repeticiones
	 * @param cargaKg
	 * @param idCategoria
	 */
	public Ejercicio(String nombre, int series, int repeticiones, double cargaKg, Categoria categoria) {
		super();
		this.nombre = nombre;
		this.series = series;
		this.repeticiones = repeticiones;
		this.cargaKg = cargaKg;
		this.categoria = categoria;
	}

	/**
	 * Obtiene el ID del ejercicio.
	 * 
	 * @return el ID del ejercicio
	 */
	public int getIdEjercicio() {
		return idEjercicio;
	}

	/**
	 * Establece un nuevo ID para el ejercicio.
	 * 
	 * @param idEjercicio el nuevo ID a establecer
	 */
	public void setIdEjercicio(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	/**
	 * Obtiene el nombre del ejercicio.
	 * 
	 * @return el nombre del ejercicio
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece un nuevo nombre para el ejercicio.
	 * 
	 * @param nombre el nuevo nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el número de series del ejercicio.
	 * 
	 * @return el número de series del ejercicio
	 */
	public int getSeries() {
		return series;
	}

	/**
	 * Establece un nuevo número de series para el ejercicio.
	 * 
	 * @param series el nuevo número de series a establecer
	 */
	public void setSeries(int series) {
		this.series = series;
	}

	/**
	 * Obtiene el número de repeticiones del ejercicio.
	 * 
	 * @return el número de repeticiones del ejercicio
	 */
	public int getRepeticiones() {
		return repeticiones;
	}

	/**
	 * Establece un nuevo número de repeticiones para el ejercicio.
	 * 
	 * @param repeticiones el nuevo número de repeticiones a establecer
	 */
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	/**
	 * Obtiene la carga en kilogramos del ejercicio.
	 * 
	 * @return la carga en kilogramos del ejercicio
	 */
	public double getCargaKg() {
		return cargaKg;
	}

	/**
	 * Establece una nueva carga en kilogramos para el ejercicio.
	 * 
	 * @param cargaKg la nueva carga en kilogramos a establecer
	 */
	public void setCargaKg(double cargaKg) {
		this.cargaKg = cargaKg;
	}

	/**
	 * Obtiene la categoría del ejercicio.
	 * 
	 * @return la categoría del ejercicio
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Establece una nueva categoría para el ejercicio.
	 * 
	 * @param categoria la nueva categoría a establecer
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * Obtiene la lista de clientes asignados al ejercicio.
	 * 
	 * @return la lista de clientes asignados al ejercicio
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * Establece una nueva lista de clientes para el ejercicio.
	 * 
	 * @param clientes la nueva lista de clientes a establecer
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * Asigna un nuevo cliente al ejercicio.
	 * 
	 * @param c el cliente a asignar
	 */
	public void asignarCliente(Cliente c) {
		this.clientes.add(c);
		c.getEjercicios().add(this);
	}

	/**
	 * Quita un nuevo cliente al ejercicio.
	 * 
	 * @param c el cliente a quitar
	 */
	public void quitarCliente(Cliente c) {
		this.clientes.remove(c);
		c.getEjercicios().remove(this);
	}

}