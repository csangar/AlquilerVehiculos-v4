package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarAlquileresCliente extends Controlador{
	

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	private TableColumn<Alquiler, String> tcCliente;

	@FXML
	private TableColumn<Alquiler, String> tcVehiculo;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaAlquiler;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaDevolucion;
	
	private Cliente cliente;
	
	private Alquiler alquiler;

	private boolean cancelado;

	@FXML
	void initialize() {
		// getEscenario().setResizable(false); ---Es para que no pueda cambiarse el
		// tamaño de la ventana
		tcCliente.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCliente().getDni()));
		tcVehiculo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getVehiculo().getMatricula()));
		tcFechaAlquiler.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaAlquiler.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
		tcFechaDevolucion.setCellFactory(columna -> new FormateadorCeldaFecha());
	}
	

	public void setCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		this.cliente = cliente;
        cancelado = false;
	}
	
	public Cliente getCliente() {
		/*String dni = tfDni.getText();
		return cancelado ? null : Cliente.getClienteConDni(dni);*/
		return cancelado ? null : cliente;
	}

	public void setAlquiler(Alquiler alquiler) {
		/*tfDni.setText(cliente.getDni());
		tfNombre.setText(cliente.getNombre());
		tfTelefono.setText(cliente.getTelefono());
		cancelado = false;*/
		 this.alquiler = alquiler;
	        cancelado = false;
	}

	public Alquiler getAlquiler() {
		/*String dni = tfDni.getText();
		return cancelado ? null : Cliente.getClienteConDni(dni);*/
		return cancelado ? null : alquiler;
	}
	public void actualizar(List<Alquiler> alquileres) {
		//alquileres = VistaGrafica.getInstancia().getControlador().getAlquileres(cliente);
		tvAlquileres.setItems(FXCollections.observableArrayList(alquileres));
	}
	
	@FXML
	void pulsar() {
		getEscenario().close();
	}

}
