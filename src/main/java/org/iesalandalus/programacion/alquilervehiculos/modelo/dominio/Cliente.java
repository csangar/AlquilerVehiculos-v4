package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {

	public static final String ER_NOMBRE = "[A-Z][a-z]*( [A-Z][a-z]*)?";// "[A-Z][a-z]*\s*([A-Z][a-z]*)*\s*([A-Z][a-z]*)*"
	public static final String ER_DNI = "\\d{8}[A-Z]";
	public static final String ER_TELEFONO = "\\d{9}";
	private String nombre;
	private String dni;
	private String telefono;

	public Cliente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}

	public Cliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No es posible copiar un cliente nulo.");
		}
		this.nombre = cliente.getNombre();
		this.dni = cliente.getDni();
		this.telefono = cliente.getTelefono();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null)
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		if (dni == null)
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		if (!dni.matches(ER_DNI)) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		if (!comprobarLetraDni(dni)) {
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}
		this.dni = dni;
	}

	private static boolean comprobarLetraDni(String dni) {
		/* boolean comprobacion = false; */
		String caracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
		String numero = dni.substring(0, 8);
		Character caracter = dni.charAt(8);
		return caracter.equals(caracteres.charAt(Integer.parseInt(numero) % 23));
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null)
			throw new NullPointerException("ERROR: El teléfono no puede ser nulo.");
		if (!telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
		}
		this.telefono = telefono;
	}

	public static Cliente getClienteConDni(String dniValido) {
		return new Cliente("Pepe Lopez", dniValido, "911763247");
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return String.format("%s - %s (%s)", nombre, dni, telefono);
	}
}
