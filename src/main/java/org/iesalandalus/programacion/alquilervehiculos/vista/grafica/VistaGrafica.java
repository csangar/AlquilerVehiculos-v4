package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaGrafica extends Vista {

	private static VistaGrafica instancia;
	
	private VistaGrafica() {
		
	}

	public static VistaGrafica getInstancia() {
		if (instancia == null) {
			instancia = new VistaGrafica();
		}
		return instancia;
	}

	@Override
	public void comenzar() throws OperationNotSupportedException {
		LanzadorVentanaPrincipal.comenzar();
		getControlador().terminar();
	}

	@Override
	public void terminar() {
		System.out.println("FIN!");
	}

}
