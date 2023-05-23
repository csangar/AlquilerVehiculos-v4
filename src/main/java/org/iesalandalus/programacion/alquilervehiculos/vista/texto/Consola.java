package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.processing.SupportedSourceVersion;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {

	}

	public static void mostrarCabecera(String mensaje) {
		String subrayado = "";
		System.out.println(mensaje);
		for (int i = 0; i < mensaje.length(); i++) {
			subrayado = subrayado + "-";
		}
		System.out.println(subrayado);
	}

	public static void mostrarMenuAcciones() {
		mostrarCabecera("Menu de opciones disponibles:");
		for (Accion accion : Accion.values()) {
			System.out.println(accion);
		}
	}

	public static Accion elegirAccion() {
		int accion;
		Accion o = null;
		try {
			do {
				accion = leerEntero("Elige la opcion que desear realizar: ");
				o = Accion.get(accion);
			} while (o == null);
		} catch (Exception e) {
			System.out.print("Esa opcion no es correcta");
		}
		return o;
	}

	private static String leerCadena(String mensaje) {
		System.out.print(mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.print(mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		LocalDate fecha = null;
		try {
			String cadena = leerCadena(mensaje);
			if (patron.equals(PATRON_FECHA)) {
				fecha = LocalDate.parse(cadena, FORMATO_FECHA);
			}
			if (patron.equals(PATRON_MES)) {
				fecha = LocalDate.parse("01/" + cadena, FORMATO_FECHA);
			}
		} catch (Exception e) {
			System.out.print(mensaje);
		}
		return fecha;
	}

	public static Cliente leerCliente() {
		String nombre = leerNombre();
		String dni = leerCadena("Introduce el dni del cliente: ");
		String telefono = leerTelefono();
		return new Cliente(nombre, dni, telefono);
	}

	public static Cliente leerClienteDni() {
		String dni = leerCadena("Introduce un dni: ");
		return Cliente.getClienteConDni(dni);
	}

	public static String leerNombre() {
		return leerCadena("Introduce el nombre del cliente: ");
	}

	public static String leerTelefono() {
		return leerCadena("Introduce el telefono del cliente: ");
	}

	public static Vehiculo leerVehiculo() {
		mostrarMenuTiposVehiculos();
		return leerVehiculo(elegirTipoVehiculo());
	}

	private static void mostrarMenuTiposVehiculos() {
		mostrarCabecera("Menu de tipod de vehiculos disponibles:");
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			System.out.println(tipoVehiculo);
		}
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		int tipoVehiculo;
		TipoVehiculo tp = null;
		try {
			do {
				tipoVehiculo = leerEntero("Elige el tipo de vehiculo: ");
				tp = TipoVehiculo.get(tipoVehiculo);
			} while (tp == null);
		} catch (Exception e) {
			System.out.print("Ese tipo de vehiculo no existe");
		}
		return tp;
	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		String marca = leerCadena("Introduce la marca del vehiculo: ");
		String modelo = leerCadena("Introduce el modelo del vehiculo: ");
		String matricula = leerCadena("Introduce la matricula del vehiculo: ");
		Vehiculo vehiculoLeido = null;
		if (tipoVehiculo.equals(TipoVehiculo.TURISMO)) {
			int cilindrada = leerEntero("Introduce la cilidrada del vehiculo: ");
			vehiculoLeido = new Turismo(marca, modelo, cilindrada, matricula);
		}
		if (tipoVehiculo.equals(TipoVehiculo.AUTOBUS)) {
			int plazas = leerEntero("Introduce el nº de plazas del vehiculo: ");
			vehiculoLeido = new Autobus(marca, modelo, plazas, matricula);
		}
		if (tipoVehiculo.equals(TipoVehiculo.FURGONETA)) {
			int pma = leerEntero("Introduce el pma del vehiculo: ");
			int plazas = leerEntero("Introduce el nº de plazas del vehiculo: ");
			vehiculoLeido = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		return vehiculoLeido;
	}

	public static Vehiculo leerVehiculoMatricula() {
		String matricula = leerCadena("Introduce una matricula: ");
		return Vehiculo.getVehiculoConMatricula(matricula);
	}

	public static Alquiler leerAlquiler() {
		Cliente cliente = leerClienteDni();
		Vehiculo vehiculo = leerVehiculoMatricula();
		LocalDate fechaAlquiler = leerFecha("Introduce la fecha de alquiler: ", PATRON_FECHA);
		return new Alquiler(cliente, vehiculo, fechaAlquiler);
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Introduce la fecha de devolucion: ", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Introduce un mes y un año: ", PATRON_MES);
	}
}
