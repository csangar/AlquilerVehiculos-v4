package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BorrarVehiculo extends Controlador{


    @FXML
    private TextField tfMatricula;
    
    private boolean cancelado;
    
    @FXML
    void initialize() { 	
    	
    }
    
    public Vehiculo getVehiculo() {
		String matricula = tfMatricula.getText();
    	return cancelado ? null : Vehiculo.getVehiculoConMatricula(matricula);
    }
    
    public void limpiar() {
    	tfMatricula.setText("");
    	cancelado = true;
    }
    
    public void borrar() throws OperationNotSupportedException {
        Vehiculo vehiculo = getVehiculo();
        if (vehiculo != null) {
        	vehiculo = VistaGrafica.getInstancia().getControlador().buscar(vehiculo);
    		VistaGrafica.getInstancia().getControlador().borrar(vehiculo);
    		Dialogos.mostrarDialogoAdvertencia("Borrar vehiculo", "Vehiculo eliminado correctamente",
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
