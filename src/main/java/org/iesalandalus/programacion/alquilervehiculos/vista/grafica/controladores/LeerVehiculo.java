package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LeerVehiculo extends Controlador {

	@FXML
	private ComboBox<TipoVehiculo> cbTipo;

	@FXML
	private TextField tfCilindrada;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	@FXML
	private TextField tfPlazas;

	@FXML
	private TextField tfPma;

	private boolean cancelado;

	@FXML
	void initialize() {
		tfMarca.textProperty().addListener((ob, ov, mv) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
		tfModelo.textProperty().addListener((ob, ov, mv) -> Controles.validarCampoTexto( null, tfModelo));
		tfMatricula.textProperty()
				.addListener((ob, ov, mv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		tfCilindrada.textProperty()
				.addListener((ob, ov, mv) -> Controles.validarCampoTexto(null, tfCilindrada));
		tfPlazas.textProperty().addListener((ob, ov, mv) -> Controles.validarCampoTexto(null, tfPlazas));
		tfPma.textProperty().addListener((ob, ov, mv) -> Controles.validarCampoTexto(null, tfPma));
		
	}

	public Vehiculo getVehiculo() {
		Vehiculo vehiculo = null;
		String marca = tfMarca.getText();
		String modelo = tfModelo.getText();
		String matricula = tfMatricula.getText();
		TipoVehiculo tipo = cbTipo.getValue();
		if (tipo.equals(TipoVehiculo.TURISMO)) {
			int cilindrada = Integer.parseInt(tfCilindrada.getText());
			vehiculo = new Turismo(marca, modelo, cilindrada, matricula);
		} else if (tipo.equals(TipoVehiculo.AUTOBUS)) {
			int plazas = Integer.parseInt(tfPlazas.getText());
			vehiculo = new Autobus(marca, modelo, plazas, matricula);
		} else if (tipo.equals(TipoVehiculo.FURGONETA)) {
			int plazas = Integer.parseInt(tfPlazas.getText());
			int pma = Integer.parseInt(tfPma.getText());
			vehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		return cancelado ? null : vehiculo;
	}

	public void limpiar() {
		tfMarca.setText("");
		tfModelo.setText("");
		tfMatricula.setText("");
		cbTipo.setValue(null);
		tfCilindrada.setText("");
		tfPlazas.setText("");
		tfPma.setText("");
		cancelado = true;
	}

	public void actualizar(TipoVehiculo[] tipoVehiculos) {
		cbTipo.setItems(FXCollections.observableArrayList(tipoVehiculos));
	}
	
	@FXML
	public void OpcionElegida() {
		if (cbTipo.getValue().equals(TipoVehiculo.TURISMO)) {
			tfCilindrada.setDisable(false);
			tfPlazas.setDisable(true);
			tfPma.setDisable(true);
		}else if(cbTipo.getValue().equals(TipoVehiculo.AUTOBUS)) {
			tfCilindrada.setDisable(true);
			tfPlazas.setDisable(false);
			tfPma.setDisable(true);
		}else if(cbTipo.getValue().equals(TipoVehiculo.FURGONETA)) {
			tfCilindrada.setDisable(true);
			tfPlazas.setDisable(false);
			tfPma.setDisable(false);
		}
	}

	@FXML
	void aceptar() {
		cancelado = false;
		getEscenario().close();
	}

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}
}
