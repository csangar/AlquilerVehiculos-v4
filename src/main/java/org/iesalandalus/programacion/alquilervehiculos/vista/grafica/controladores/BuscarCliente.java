package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuscarCliente extends Controlador {

	@FXML
    private TextField tfDni;
	
	@FXML
	private TableColumn<Cliente, String> tcDNI;

	@FXML
	private TableColumn<Cliente, String> tcNombre;

	@FXML
	private TableColumn<Cliente, String> tcTelefono;

	@FXML
	private TableView<Cliente> tvCliente;
	
	private boolean cancelado;

	@FXML
	void initialize() {
    	//tfDni.textProperty().addListener((ob,ov,mv)-> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
    	/*tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));*/
	}
	
	public void actualizar(List<Cliente> cliente) {
		tvCliente.setItems(FXCollections.observableArrayList(cliente));
	}

	public Cliente getCliente() {
		String dni = tfDni.getText();
    	return cancelado ? null : Cliente.getClienteConDni(dni);
    }

	public void limpiar() {
		tfDni.setText("");
		//tvCliente.setItems(null);
		cancelado = true;
	}
	
	@FXML
	void buscar() {
		Cliente buscado = getCliente();
		tvCliente.setItems(null);
		List<Cliente> miCliente = new ArrayList<>();
		
		miCliente.add(VistaGrafica.getInstancia().getControlador().buscar(buscado));
		System.out.println(VistaGrafica.getInstancia().getControlador().buscar(buscado));
		tvCliente.setItems(FXCollections.observableArrayList(miCliente));
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		//tvCliente.refresh();
		ModificarCliente modificarCliente = (ModificarCliente) Controladores.get("vistas/ModificarCliente.fxml", "modificar Cliente", getEscenario());
	    modificarCliente.setCliente(buscado);

	    modificarCliente.getEscenario().showAndWait();
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
