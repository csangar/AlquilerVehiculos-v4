package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EstadisticasMensuales extends Controlador{
	
	@FXML
	private PieChart pcGrafico;
	
	@FXML
	private DatePicker dpFechaGrafico;
	
	@FXML
	void initialize() {
		dpFechaGrafico.valueProperty().addListener((observable, oldValue, newValue) -> {
			LocalDate fechaSeleccionada = dpFechaGrafico.getValue();
			generarGraficoMensual(fechaSeleccionada);
		});
	}
	
	private void generarGraficoMensual(LocalDate fecha) {
		Map<TipoVehiculo, Integer> mapa = inicializarEstadisticas();
		for (Alquiler alquiler : VistaGrafica.getInstancia().getControlador().getAlquileres()) {
			if (alquiler.getFechaAlquiler().getMonth().equals(fecha.getMonth())
					&& alquiler.getFechaAlquiler().getYear() == fecha.getYear()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.get(alquiler.getVehiculo());
				mapa.put(tipoVehiculo, mapa.get(tipoVehiculo) + 1);
			}
		}
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (TipoVehiculo tipoVehiculo : mapa.keySet()) {
			int cantidad = mapa.get(tipoVehiculo);
			PieChart.Data data = new PieChart.Data(tipoVehiculo.toString(), cantidad);
			pieChartData.add(data);
		}
		
		pcGrafico.setData(pieChartData);
	}
	
	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		TreeMap<TipoVehiculo, Integer> map = new TreeMap<>();
		for (TipoVehiculo vehiculo : TipoVehiculo.values()) {
			map.put(vehiculo, 0);
		}
		return map;
	}
}