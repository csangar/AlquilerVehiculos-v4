package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {

	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int PRECIO_DIA = 20;
	private Cliente cliente;
	private Vehiculo vehiculo;
	private LocalDate fechaAlquiler;
	private LocalDate fechaDevolucion;

	public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {
		setCliente(cliente);
		setVehiculo(vehiculo);
		setFechaAlquiler(fechaAlquiler);
	}

	public Alquiler(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
		}
		this.cliente = new Cliente(alquiler.cliente.getNombre(), alquiler.cliente.getDni(), alquiler.cliente.getTelefono());
		this.vehiculo = Vehiculo.copiar(alquiler.getVehiculo());
		this.fechaAlquiler = alquiler.getFechaAlquiler();
		this.fechaDevolucion = alquiler.getFechaDevolucion();
	}

	public Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
		}
		this.vehiculo = vehiculo;
	}

	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	private void setFechaAlquiler(LocalDate fechaAlquiler) {
		if (fechaAlquiler == null) {
			throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
		}
		if (fechaAlquiler.compareTo(LocalDate.now()) == 1) {
			throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
		}
		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.compareTo(LocalDate.now()) == 1) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (fechaDevolucion.compareTo(getFechaAlquiler()) <= 0) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
		}

		this.fechaDevolucion = fechaDevolucion;
	}

	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (getFechaDevolucion() != null) {
			throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);
	}

	@Override
	public String toString() {
		String cadFinal;
		if (getFechaDevolucion() != null) {
			cadFinal = cliente + " <---> " + vehiculo + ", " + getFechaAlquiler().format(FORMATO_FECHA) + " - "
					+ getFechaDevolucion().format(FORMATO_FECHA) + " (" + getPrecio() + "€)";
		} else {
			cadFinal = cliente + " <---> " + vehiculo + ", " + getFechaAlquiler().format(FORMATO_FECHA)
					+ " - Aún no devuelto (" + 0 + "€)";
		}
		return cadFinal;
	}

	public int getPrecio() {
		long nDias;
		if (getFechaDevolucion() == null) {
			nDias = 0;
		} else {
			nDias = ChronoUnit.DAYS.between(getFechaAlquiler(), getFechaDevolucion());
		}
		return (PRECIO_DIA + (vehiculo.getFactorPrecio())) * (int) nDias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, fechaAlquiler, fechaDevolucion, vehiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alquiler other = (Alquiler) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(fechaAlquiler, other.fechaAlquiler)
				&& Objects.equals(fechaDevolucion, other.fechaDevolucion) && Objects.equals(vehiculo, other.vehiculo);
	}

}
