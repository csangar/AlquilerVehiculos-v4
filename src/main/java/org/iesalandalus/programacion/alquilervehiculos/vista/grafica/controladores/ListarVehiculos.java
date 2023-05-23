package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarVehiculos extends Controlador {

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;
	
	@FXML
    private ComboBox<Vehiculo> cbElegido;
    
    private Vehiculo vehiculoElegido;
    
	@FXML
	void initialize() {
		// getEscenario().setResizable(false); ---Es para que no pueda cambiarse el
		// tamaño de la ventana
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcCilindrada.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Turismo turismo ? Integer.toString(turismo.getCilindrada()) : ""));
		tcPlazas.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue() instanceof Autobus autobus
				? Integer.toString(autobus.getPlazas())
				: fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPlazas()) : ""));
		tcPma.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPlazas()) : ""));
		cbElegido.setItems(FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getVehiculos()));
	}

	public void actualizar(List<Vehiculo> vehiculos) {
		tvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
	}
	 @FXML
	    void elegirVehiculo() {
	    	//ya tengo el cliente en el combobox
	        vehiculoElegido = cbElegido.getValue();
	    }

	 @FXML
		void listarAlquileres() {
			System.out.println("listarAlquileres");
			ListarAlquileresVehiculo listarAlquileres = (ListarAlquileresVehiculo) Controladores.get("vistas/ListarAlquileresVehiculo.fxml",
					"Listar Alquileres", getEscenario());
			listarAlquileres.setVehiculo(vehiculoElegido);
			listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(vehiculoElegido));
			listarAlquileres.getEscenario().showAndWait();
		}
	@FXML
	void pulsar() {
		getEscenario().close();
	}
}
