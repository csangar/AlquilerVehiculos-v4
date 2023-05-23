package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Vehiculos;

public class ModeloCascada extends Modelo {

	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos) {
		super(factoriaFuenteDatos);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		clientes.insertar(new Cliente(cliente));
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		vehiculos.insertar(vehiculo);
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		Cliente cliente = buscar(alquiler.getCliente());
		if (cliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		Vehiculo vehiculo = buscar(alquiler.getVehiculo());
		if (vehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
		}
		alquileres.insertar(new Alquiler(cliente, vehiculo, alquiler.getFechaAlquiler()));
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		return new Cliente(clientes.buscar(cliente));
	}

	@Override
	public Vehiculo buscar(Vehiculo turismo) {
		return new Turismo((Turismo) vehiculos.buscar(turismo));
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		return new Alquiler(alquileres.buscar(alquiler));
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		clientes.modificar(cliente, nombre, telefono);
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(vehiculo, fechaDevolucion);
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for (Alquiler alquiler : alquileres.get(cliente)) {
			alquileres.borrar(alquiler);
		}
		clientes.borrar(cliente);
	}

	@Override
	public void borrar(Vehiculo turismo) throws OperationNotSupportedException {
		for (Alquiler alquiler : alquileres.get(turismo)) {
			alquileres.borrar(alquiler);
		}
		vehiculos.borrar(turismo);
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		alquileres.borrar(alquiler);
	}

	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> lista = new ArrayList<>();
		for (Cliente cliente : clientes.get()) {
			lista.add(new Cliente(cliente));
		}
		return lista;
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> lista = new ArrayList<>();
		for (Vehiculo vehiculo : vehiculos.get()) {
			lista.add(Vehiculo.copiar(vehiculo));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get()) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get(cliente)) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo turismo) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get(turismo)) {
			lista.add(new Alquiler(alquiler));
		}
		return lista;
	}
}
