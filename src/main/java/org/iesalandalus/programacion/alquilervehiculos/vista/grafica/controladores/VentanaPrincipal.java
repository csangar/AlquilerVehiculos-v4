package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class VentanaPrincipal extends Controlador {

	@FXML
	private Button btPulsame;

	@FXML
	void pulsado() {
		System.out.println("Boton pulsado");
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"ListarClientes", getEscenario());
		listarClientes.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		listarClientes.getEscenario().showAndWait();
	}

	@FXML
	void initialize() {
		System.out.println("Metodo que inicializa la ventana principal");
	}

	@FXML
	void confirmarSalida() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estas seguro que deseas salir de la apliacion?",
				getEscenario())) {
			getEscenario().close();
		}
	}

	@FXML
	void leerCliente() {
		LeerCliente leerCliente = (LeerCliente) Controladores.get("vistas/LeerCliente.fxml", "Leer Cliente",
				getEscenario());
		leerCliente.limpiar();
		leerCliente.getEscenario().showAndWait();
		try {
			Cliente cliente = leerCliente.getCliente();
			if (cliente != null) {
				VistaGrafica.getInstancia().getControlador().insertar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Insertar Cliente", "Cliente insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar Cliente", e.getMessage(), getEscenario());
		}
		// System.out.println(leerCliente.getCliente());
	}

	@FXML
	void buscarCliente() {
		BuscarCliente buscado = (BuscarCliente) Controladores.get("vistas/BuscarCliente.fxml", "Buscar Cliente",
				getEscenario());
		// buscarCliente.limpiar();
		buscado.getEscenario().showAndWait();

	}
	
	@FXML
	void borrarCliente() {
		BorrarCliente borrado = (BorrarCliente) Controladores.get("vistas/BorrarCliente.fxml", "Borrar Cliente",
				getEscenario());
		// buscarCliente.limpiar();
		borrado.getEscenario().showAndWait();
		try {
			Cliente cliente = borrado.getCliente();
			if (cliente != null) {
				VistaGrafica.getInstancia().getControlador().borrar(cliente);
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listarVehiculos() {
		System.out.println("listarVehiculos");
		ListarVehiculos listarVehiculos = (ListarVehiculos) Controladores.get("vistas/ListarVehiculos.fxml",
				"Listar Vehiculos", getEscenario());
		listarVehiculos.actualizar(VistaGrafica.getInstancia().getControlador().getVehiculos());
		listarVehiculos.getEscenario().showAndWait();
	}

	@FXML
	void leerVehiculo() {
		LeerVehiculo leerVehiculo = (LeerVehiculo) Controladores.get("vistas/LeerVehiculo.fxml", "Leer Vehiculo",
				getEscenario());
		leerVehiculo.limpiar();
		leerVehiculo.actualizar(TipoVehiculo.values());
		leerVehiculo.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = leerVehiculo.getVehiculo();
			if (vehiculo != null) {
				VistaGrafica.getInstancia().getControlador().insertar(vehiculo);
				Dialogos.mostrarDialogoAdvertencia("Insertar vehiculo", "Vehiculo insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar Vehiculo", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void buscarVehiculo() {
		BuscarVehiculo buscado = (BuscarVehiculo) Controladores.get("vistas/BuscarVehiculo.fxml", "Buscar vehiculo",
				getEscenario());
		buscado.getEscenario().showAndWait();
	}

	@FXML
	void borrarVehiculo() {
		BorrarVehiculo borrado = (BorrarVehiculo) Controladores.get("vistas/BorrarVehiculo.fxml", "Borrar Vehiculo",
				getEscenario());
		// buscarCliente.limpiar();
		borrado.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = borrado.getVehiculo();
			if (vehiculo != null) {
				VistaGrafica.getInstancia().getControlador().borrar(vehiculo);
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar vehiculo", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void listarAlquileres() {
		System.out.println("listarAlquileres");
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar Alquileres", getEscenario());
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres());
		listarAlquileres.getEscenario().showAndWait();
	}

	@FXML
	void leerAlquiler() {
		LeerAlquiler leerAlquiler = (LeerAlquiler) Controladores.get("vistas/LeerAlquiler.fxml", "Leer Alquiler",
				getEscenario());
		leerAlquiler.limpiar();
		leerAlquiler.getEscenario().showAndWait();
		try {
			Alquiler alquiler = leerAlquiler.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().insertar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Insertar alquiler", "alquiler insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar alquiler", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void buscarAlquiler() {
		BuscarAlquiler buscado = (BuscarAlquiler) Controladores.get("vistas/BuscarAlquiler.fxml", "Buscar alquiler",
				getEscenario());
		buscado.getEscenario().showAndWait();
	}
	
	@FXML
	void borrarAlquiler() {
		BorrarAlquiler borrado = (BorrarAlquiler) Controladores.get("vistas/BorrarAlquiler.fxml", "Borrar alquiler",
				getEscenario());
		// buscarCliente.limpiar();
		borrado.getEscenario().showAndWait();
		try {
			Alquiler alquiler = borrado.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().borrar(alquiler);
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar alquiler", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void devolverAlquilerCliente() {
		DevolverAlquilerCliente devuelto = (DevolverAlquilerCliente) Controladores.get("vistas/DevolverAlquilerCliente.fxml", "devolver alquiler",
				getEscenario());
		devuelto.getEscenario().showAndWait();
		try {
			Alquiler alquiler = devuelto.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().devolver(alquiler.getCliente(), alquiler.getFechaAlquiler());
				Dialogos.mostrarDialogoAdvertencia("Insertar alquiler", "alquiler insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar alquiler", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void devolverAlquilerVehiculo() {
		DevolverAlquilerVehiculo devuelto = (DevolverAlquilerVehiculo) Controladores.get("vistas/DevolverAlquilerVehiculo.fxml", "devolver alquiler",
				getEscenario());
		devuelto.getEscenario().showAndWait();
		try {
			Alquiler alquiler = devuelto.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().devolver(alquiler.getVehiculo(), alquiler.getFechaAlquiler());;
				Dialogos.mostrarDialogoAdvertencia("Insertar alquiler", "alquiler insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar alquiler", e.getMessage(), getEscenario());
		}
	}
	
	@FXML
	void mostrarEstadisticasMensuales() {
		EstadisticasMensuales mostrarEstadisticas = (EstadisticasMensuales) Controladores.get(
	            "vistas/EstadisticasMensuales.fxml", "Estadísticas Mensuales", getEscenario());
	    mostrarEstadisticas.getEscenario().showAndWait();
	}
}
