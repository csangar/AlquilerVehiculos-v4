package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
		super();
	}

	public void comenzar() throws OperationNotSupportedException {
		Accion.setVista(this);
		Accion accion;
		do {
			Consola.mostrarMenuAcciones();
			accion = Consola.elegirAccion();
			accion.ejecutar();
		} while (accion != Accion.SALIR);
	}

	public void terminar() {
		getControlador().terminar();
		System.out.println("¡FIN!");
	}

	public void insertarCliente() throws OperationNotSupportedException {
		try {
			getControlador().insertar(Consola.leerCliente());
			System.out.println("El cliente se ha insertado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() throws OperationNotSupportedException {
		try {
			super.getControlador().insertar(Consola.leerVehiculo());
			System.out.println("El turismo se ha insertado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() throws OperationNotSupportedException {
		try {
			Alquiler alquiler = Consola.leerAlquiler();
			Cliente cliente = getControlador().buscar(alquiler.getCliente());
			Vehiculo turismo = getControlador().buscar(alquiler.getVehiculo());
			if (cliente == null) {
				throw new OperationNotSupportedException("El cliente no existe");
			}
			if (turismo == null) {
				throw new OperationNotSupportedException("El turismo no puede ser nulo");
			}
			super.getControlador().insertar(new Alquiler(cliente, turismo, alquiler.getFechaAlquiler()));
			System.out.println("El alquiler se ha insertado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			System.out.println(getControlador().buscar(Consola.leerClienteDni()));
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			System.out.println(getControlador().buscar(Consola.leerVehiculoMatricula()));
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		try {
			System.out.println(getControlador().buscar(Consola.leerAlquiler()));
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void modificarCliente() throws OperationNotSupportedException {
		try {
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("El cliente se ha modificado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devoverAlquilerCliente() throws OperationNotSupportedException {
		try {
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
			System.out.println("El alquiler se ha devuelto de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devoverAlquilerVehiculo() throws OperationNotSupportedException {
		try {
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
			System.out.println("El alquiler se ha devuelto de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() throws OperationNotSupportedException {
		try {
			Cliente cliente = Consola.leerClienteDni();
			getControlador().borrar(cliente);
			System.out.println("El cliente se ha borrado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() throws OperationNotSupportedException {
		try {
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.println("El turismo se ha borrado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() throws OperationNotSupportedException {
		try {
			getControlador().borrar(Consola.leerAlquiler());
			System.out.println("El alquiler se ha borrado de forma correcta");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			List<Cliente> listaClientes = getControlador().getClientes();
			listaClientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			System.out.println(listaClientes);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarVehiculos() {
		try {
			List<Vehiculo> listaVehiculos = getControlador().getVehiculos();
			listaVehiculos.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
					.thenComparing(Vehiculo::getMatricula));
			System.out.println(listaVehiculos);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquiler() {
		try {
			List<Alquiler> listaAlquileres = getControlador().getAlquileres();
			Comparator<Cliente> listaClientes = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			listaAlquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					listaClientes));
			System.out.println(getControlador().getAlquileres());
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresCliente() {
		try {
			List<Alquiler> listaAlquileres = getControlador().getAlquileres(Consola.leerClienteDni());
			listaAlquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler));
			System.out.println(listaAlquileres);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {
		try {
			List<Alquiler> listaAlquileres = getControlador().getAlquileres(Consola.leerVehiculoMatricula());
			Comparator<Cliente> listaClientes = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			listaAlquileres.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente,
					listaClientes));
			System.out.println(listaAlquileres);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		LocalDate fecha = Consola.leerMes();
		Map<TipoVehiculo, Integer> mapa = inicializarEstadisticas();
		for (Alquiler alquiler : getControlador().getAlquileres()) {
			if (alquiler.getFechaAlquiler().getMonth().equals(fecha.getMonth())
					&& alquiler.getFechaAlquiler().getYear() == (fecha.getYear())) {
				if (TipoVehiculo.get(alquiler.getVehiculo()).equals(TipoVehiculo.TURISMO)) {
					mapa.put(TipoVehiculo.TURISMO, mapa.get(TipoVehiculo.TURISMO) + 1);
				}
				if (TipoVehiculo.get(alquiler.getVehiculo()).equals(TipoVehiculo.AUTOBUS)) {
					mapa.put(TipoVehiculo.AUTOBUS, mapa.get(TipoVehiculo.AUTOBUS) + 1);
				}
				if (TipoVehiculo.get(alquiler.getVehiculo()).equals(TipoVehiculo.FURGONETA)) {
					mapa.put(TipoVehiculo.FURGONETA, mapa.get(TipoVehiculo.FURGONETA) + 1);
				}
			}
		}
		System.out.println(mapa);
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		TreeMap<TipoVehiculo, Integer> map = new TreeMap<>();
		for (TipoVehiculo vehiculo : TipoVehiculo.values()) {
			map.put(vehiculo, 0);
		}
		return map;
	}
}
