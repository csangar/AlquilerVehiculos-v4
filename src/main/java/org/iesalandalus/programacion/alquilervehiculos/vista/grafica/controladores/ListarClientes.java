package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;


import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarClientes extends Controlador{

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, String> tcDNI;

    @FXML
    private TableColumn<Cliente, String> tcNombre;

    @FXML
    private TableColumn<Cliente, String> tcTelefono;
    
    @FXML
    private ComboBox<Cliente> cbElegido;
    
    private Cliente clienteElegido;
    
	@FXML
    void initialize() {
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDNI.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getDni()));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		cbElegido.setItems(FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getClientes()));
	}
	public void actualizar(List<Cliente> clientes) {
		tvClientes.setItems(FXCollections.observableArrayList(clientes));
	}

    @FXML
    void pulsar() {
    	getEscenario().close();
    }
    
    @FXML
    void elegirCliente() {
    	//ya tengo el cliente en el combobox
        clienteElegido = cbElegido.getValue();
    }
    
    @FXML
	void modificar() {
    	 ModificarCliente modificarCliente = (ModificarCliente) Controladores.get("vistas/ModificarCliente.fxml", "modificar Cliente",
                 getEscenario());
         modificarCliente.setCliente(clienteElegido);
         modificarCliente.getEscenario().showAndWait();
	}
	
	@FXML
	void listarAlquileres() {
		System.out.println("listarAlquileres");
		ListarAlquileresCliente listarAlquileres = (ListarAlquileresCliente) Controladores.get("vistas/ListarAlquileresCliente.fxml",
				"Listar Alquileres", getEscenario());
		listarAlquileres.setCliente(clienteElegido);
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres(clienteElegido));
		listarAlquileres.getEscenario().showAndWait();
	}
}
