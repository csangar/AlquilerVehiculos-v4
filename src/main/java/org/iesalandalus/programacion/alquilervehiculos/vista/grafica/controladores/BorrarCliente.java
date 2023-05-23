package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BorrarCliente extends Controlador{

    @FXML
    private TextField tfDni;
    
    private boolean cancelado;
    
    @FXML
    void initialize() { 	
    	//tfDni.textProperty().addListener((ob,ov,mv)-> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
    }
    
    public Cliente getCliente() {
		String dni = tfDni.getText();
    	return cancelado ? null : Cliente.getClienteConDni(dni);
    }
    
    public void limpiar() {
    	tfDni.setText("");
    	cancelado = true;
    }

    public void borrar() throws OperationNotSupportedException {
        Cliente cliente = getCliente();
        if (cliente != null) {
    		/*List<Cliente> miCliente = new ArrayList<>();
    		miCliente.remove(VistaGrafica.getInstancia().getControlador().buscar(cliente));*/
        	cliente = VistaGrafica.getInstancia().getControlador().buscar(cliente);
    		VistaGrafica.getInstancia().getControlador().borrar(cliente);
    		Dialogos.mostrarDialogoAdvertencia("Borrar Cliente", "Cliente eliminado correctamente",
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
