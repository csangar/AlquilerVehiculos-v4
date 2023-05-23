package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LeerAlquiler extends Controlador {

	@FXML
	private TextField tfDni;

	@FXML
	private DatePicker dpFechaAlta;

	@FXML
	private TextField tfMatricula;

	boolean cancelado;

	@FXML
	void initialize() {
		tfDni.textProperty().addListener((ob, ov, mv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfDni));
		tfMatricula.textProperty()
				.addListener((ob, ov, mv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		dpFechaAlta.valueProperty().addListener((ob, ov, nv) -> {
			LocalDate fechaSeleccionada = dpFechaAlta.getValue();
			String fechaFormateada = fechaSeleccionada.format(Alquiler.FORMATO_FECHA);
			System.out.println(fechaFormateada);
		});
	}

	public Alquiler getAlquiler() {
		Cliente dni = Cliente.getClienteConDni(tfDni.getText());
		Vehiculo matricula = Vehiculo.getVehiculoConMatricula(tfMatricula.getText());
		LocalDate fechaAlta = dpFechaAlta.getValue();
		return cancelado ? null : new Alquiler(dni, matricula, fechaAlta);
	}

	public void limpiar() {
		tfDni.setText("");
		tfMatricula.setText("");
		dpFechaAlta.setValue(null);
		cancelado = true;
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
