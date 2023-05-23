package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModificarCliente extends Controlador {

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfTelefono;
	
	private Cliente cliente;

	private boolean cancelado;

	@FXML
	void initialize() {
		tfDni.setDisable(true);
	}

	public void setCliente(Cliente cliente) {
		/*tfDni.setText(cliente.getDni());
		tfNombre.setText(cliente.getNombre());
		tfTelefono.setText(cliente.getTelefono());
		cancelado = false;*/
		 this.cliente = cliente;
	        actualizarDatosCliente();
	        cancelado = false;
	}

	public Cliente getCliente() {
		/*String dni = tfDni.getText();
		return cancelado ? null : Cliente.getClienteConDni(dni);*/
		return cancelado ? null : cliente;
	}

	public void limpiar() {
		tfDni.setText("");
		// tvCliente.setItems(null);
		cancelado = true;
	}

	public void actualizarDatosCliente() {
		tfDni.setText(cliente.getDni());
        tfNombre.setText(cliente.getNombre());
        tfTelefono.setText(cliente.getTelefono());
	}

	public void modificar() throws OperationNotSupportedException {
		//Cliente cliente = getCliente();
		if (cliente != null) {
			//cliente = VistaGrafica.getInstancia().getControlador().buscar(cliente);
			VistaGrafica.getInstancia().getControlador().modificar(cliente, cliente.getNombre(), cliente.getTelefono());
			Dialogos.mostrarDialogoAdvertencia("Modificar Cliente", "Cliente modificado correctamente", getEscenario());
			limpiar();
		}
	}

	@FXML
	void aceptar() throws OperationNotSupportedException {
		cancelado = false;
		modificar();
		getEscenario().close();
	}

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}
}
