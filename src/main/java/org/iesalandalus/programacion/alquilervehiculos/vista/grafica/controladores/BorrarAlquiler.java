package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.awt.TextField;
import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class BorrarAlquiler extends Controlador{
	

    @FXML
    private DatePicker dpFechaAlta;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfMatricula;
    
    private boolean cancelado;
    
    @FXML
    void initialize() { 	
    	
    }
    
    public Alquiler getAlquiler() {
    	Cliente dni = Cliente.getClienteConDni(tfDni.getText());
		Vehiculo matricula = Vehiculo.getVehiculoConMatricula(tfMatricula.getText());
		LocalDate fechaAlquiler = dpFechaAlta.getValue();
    	return cancelado ? null : new Alquiler(dni, matricula, fechaAlquiler);
    }

    public void limpiar() {
    	tfDni.setText("");
    	tfMatricula.setText("");
    	dpFechaAlta.setValue(null);
    	cancelado = true;
    }
    
    public void borrar() throws OperationNotSupportedException {
        Alquiler alquiler = getAlquiler();
        if (alquiler != null) {
        	alquiler = VistaGrafica.getInstancia().getControlador().buscar(alquiler);
    		VistaGrafica.getInstancia().getControlador().borrar(alquiler);
    		Dialogos.mostrarDialogoAdvertencia("Borrar alquiler", "alquiler eliminado correctamente",
					getEscenario());
            limpiar();
        }
    }
    
    @FXML
    void aceptar() throws OperationNotSupportedException {
    	cancelado = false;
    	borrar();
    	getEscenario().close();
    }

    @FXML
    void cancelar() {
    	cancelado = true;
    	getEscenario().close();
    }


}
