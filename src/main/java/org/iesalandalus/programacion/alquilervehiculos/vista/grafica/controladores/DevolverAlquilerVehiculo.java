package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DevolverAlquilerVehiculo extends Controlador{
	

    @FXML
    private DatePicker dpFechaDev;

    @FXML
    private TextField tfMatricula;
    
    private Alquiler alquiler;

    private boolean cancelado;
    
    @FXML
	void initialize() {
    	tfMatricula.textProperty()
		.addListener((ob, ov, mv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		dpFechaDev.valueProperty().addListener((ob, ov, nv) -> {
			LocalDate fechaSeleccionada = dpFechaDev.getValue();
			String fechaFormateada = fechaSeleccionada.format(Alquiler.FORMATO_FECHA);
			System.out.println(fechaFormateada);
		});
	}
    
    public void setAlquiler(Alquiler alquiler) {
		 this.alquiler = alquiler;
	        cancelado = false;
	}
    
    public Alquiler getAlquiler() {
		return cancelado ? null : alquiler;
	}
    
    void devolver() {
		String matricula = tfMatricula.getText();
		LocalDate fechaDevolucion = dpFechaDev.getValue();

		try {
			VistaGrafica.getInstancia().getControlador().devolver(Vehiculo.getVehiculoConMatricula(matricula), fechaDevolucion);
			Dialogos.mostrarDialogoAdvertencia("devolver alquiler", "alquiler devuelto correctamente",
					getEscenario());
		} catch (OperationNotSupportedException e) {
			e.getMessage();
		}
	}
    
    @FXML
	void aceptar() throws OperationNotSupportedException {
		cancelado = false;
		devolver();
		getEscenario().close();
	}

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}
}
