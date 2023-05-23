package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuscarVehiculo extends Controlador{

	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TableView<Vehiculo> tvVehiculo;

	private boolean cancelado;

	void initialize() {
		/*tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		tcCilindrada.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Turismo turismo ? Integer.toString(turismo.getCilindrada()) : ""));
		tcPlazas.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue() instanceof Autobus autobus
				? Integer.toString(autobus.getPlazas())
				: fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPlazas()) : ""));
		tcPma.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPlazas()) : ""));*/
	}

	public void actualizar(List<Vehiculo> vehiculo) {
		tvVehiculo.setItems(FXCollections.observableArrayList(vehiculo));
	}

	public Vehiculo getVehiculo() {
		String matricula = tfMatricula.getText();
		return cancelado ? null : Vehiculo.getVehiculoConMatricula(matricula);
	}

	public void limpiar() {
		tfMatricula.setText("");
		tvVehiculo.setItems(null);
		cancelado = true;
	}

	@FXML
	void buscar() {
		Vehiculo buscado = getVehiculo();
		tvVehiculo.setItems(null);
		List<Vehiculo> miVehiculo = new ArrayList<>();
		miVehiculo.add(VistaGrafica.getInstancia().getControlador().buscar(buscado));
		tvVehiculo.setItems(FXCollections.observableArrayList(miVehiculo));
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
	}
}
