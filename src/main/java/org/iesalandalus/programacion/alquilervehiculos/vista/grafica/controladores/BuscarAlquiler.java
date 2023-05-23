package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuscarAlquiler extends Controlador{

		@FXML
	    private DatePicker dpFechaAlta;

	    @FXML
	    private TableColumn<Alquiler, String> tcDni;

	    @FXML
	    private TableColumn<Alquiler, LocalDate> tcFecAlta;

	    @FXML
	    private TableColumn<Alquiler, LocalDate> tcFecDev;

	    @FXML
	    private TableColumn<Alquiler, String> tcMatricula;

	    @FXML
	    private TextField tfDni;

	    @FXML
	    private TextField tfMatricula;

	    @FXML
	    private TableView<Alquiler> tvAlquiler;
	    
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

		public void actualizar(List<Alquiler> alquiler) {
			tvAlquiler.setItems(FXCollections.observableArrayList(alquiler));
		}

		public Alquiler getAlquiler() {
			Cliente dni = Cliente.getClienteConDni(tfDni.getText());
			Vehiculo matricula = Vehiculo.getVehiculoConMatricula(tfMatricula.getText());
			LocalDate fechaAlta = dpFechaAlta.getValue();
			return cancelado ? null : new Alquiler(dni, matricula, fechaAlta);
		}

		public void limpiar() {
			tfMatricula.setText("");
			tfMatricula.setText("");
			tvAlquiler.setItems(null);
			cancelado = true;
		}

		@FXML
		void buscar() {
			Alquiler buscado = getAlquiler();
			tvAlquiler.setItems(null);
			List<Alquiler> miAlquiler = new ArrayList<>();
			miAlquiler.add(VistaGrafica.getInstancia().getControlador().buscar(buscado));
			tvAlquiler.setItems(FXCollections.observableArrayList(miAlquiler));
			tcDni.setCellValueFactory(new PropertyValueFactory<>("cliente"));
			tcMatricula.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
			tcFecAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
			tcFecAlta.setCellFactory(columna -> new FormateadorCeldaFecha());
			tcFecDev.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
			tcFecDev.setCellFactory(columna -> new FormateadorCeldaFecha());
		}
		
		 public void borrar() throws OperationNotSupportedException {
		        Alquiler alquiler = getAlquiler();
		        if (alquiler != null) {
		        	alquiler = VistaGrafica.getInstancia().getControlador().buscar(alquiler);
		    		VistaGrafica.getInstancia().getControlador().borrar(alquiler);
		    		Dialogos.mostrarDialogoAdvertencia("Borrar alquiler", "Alquiler eliminado correctamente",
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
