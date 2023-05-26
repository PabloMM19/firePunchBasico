package com.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idCliente")
	/* Almacena el id del cliente */
	private int idCliente;
	@Column(name = "nombre")
	/* Almacena el nombre del cliente */
	private String nombre;
	@Column(name = "apellidos")
	/* Almacena los apellidos del cliente */
	private String apellidos;
	@Column(name = "edad")
	/* Almacena la edad del cliente */
	private int edad;
	@Column(name = "altura")
	/* Almacena la altura del cliente */
	private int altura;
	@Column(name = "peso")
	/* Almacena el peso del cliente */
	private double peso;

	@ManyToMany
	@JoinTable(name = "realiza_ejercicio", joinColumns = @JoinColumn(name = "idCliente"), inverseJoinColumns = @JoinColumn(name = "idEjercicio"))
	/*
	 * Almacena la lista de ejercicios que hace un cliente y relaciona la tabla
	 * cliente con la tabla entrenador
	 */
	private List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

	public Cliente() {
		super();
	}

	/**
	 * @param nombre
	 * @param apellidos
	 * @param edad
	 * @param altura
	 * @param peso
	 * @param entrenador
	 * @param fotoPerfil
	 */
	public Cliente(String nombre, String apellidos, int edad, int altura, double peso) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.altura = altura;
		this.peso = peso;
	}

	/**
	 * @return the idCliente
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene el nombre del cliente.
	 * 
	 * @return el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece un nuevo nombre para el cliente.
	 * 
	 * @param nombre el nuevo nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los apellidos del cliente.
	 * 
	 * @return los apellidos del cliente
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece unos nuevos apellidos para el cliente.
	 * 
	 * @param apellidos los nuevos apellidos a establecer
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Obtiene la edad del cliente.
	 * 
	 * @return la edad del cliente
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Establece una nueva edad para el cliente.
	 * 
	 * @param edad la nueva edad a establecer
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Obtiene la altura del cliente.
	 * 
	 * @return la altura del cliente
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * Establece una nueva altura para el cliente.
	 * 
	 * @param altura la nueva altura a establecer
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * Obtiene el peso del cliente.
	 * 
	 * @return el peso del cliente
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Establece un nuevo peso para el cliente.
	 * 
	 * @param peso el nuevo peso a establecer
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * Obtiene la lista de ejercicios asignados al cliente.
	 * 
	 * @return la lista de ejercicios asignados al cliente
	 */
	public List<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	/**
	 * Establece una nueva lista de ejercicios para el cliente.
	 * 
	 * @param ejercicios la nueva lista de ejercicios a establecer
	 */
	public void setEjercicios(List<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	/**
	 * Asigna un nuevo ejercicio al cliente.
	 * 
	 * @param e el ejercicio a asignar
	 */
	public void asignarEjercicio(Ejercicio e) {
		this.ejercicios.add(e);
		e.getClientes().add(this);
	}

	public void quitarEjercicio(Ejercicio e) {
		this.ejercicios.remove(e);
		e.getClientes().remove(this);
	}

}