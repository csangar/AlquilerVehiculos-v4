package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application{
	
	private static final String TITULO = "Vista Ventanas Alquiler de Vehiculos";
	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		try {
			Controlador ventanaPrincipal = Controladores.get("vistas/VentanaPrincipal.fxml", TITULO, null);
			//Dialogos.setHojaEstilos(VentanaPrincipal.getEscenario().getScene().getRoot().getStylesheets().get(0));
			ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal.getEscenario(), e));
			/*FXMLLoader cargadorVentanaPrincipal = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/VentanaPrincipal.fxml"));
			Parent raiz = cargadorVentanaPrincipal.load();
			
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setTitle("Vista Gráfica Alquiler de Vehiculos");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.show();*/
			ventanaPrincipal.getEscenario().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void comenzar() {
		launch(LanzadorVentanaPrincipal.class);
	}
	private void confirmarSalida(Stage escenario, WindowEvent e) {
		if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estas seguro de que quieres salir de la aplicacion?", escenario)) {
			escenario.close();
		}else {
			e.consume();
		}
	}
}
