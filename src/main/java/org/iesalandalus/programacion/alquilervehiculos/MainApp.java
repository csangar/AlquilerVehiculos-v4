package org.iesalandalus.programacion.alquilervehiculos;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class MainApp {

	public static void main(String[] args) throws OperationNotSupportedException {
		// Ánimo!!!!
		Modelo modelo = new ModeloCascada(procesarFuenteDatos(args));
		//Vista vista =FactoriaVista.GRAFICA.crear();
		Vista vista = procesarArgumentosVista(args);
		Controlador controlador = new Controlador(vista, modelo);
		controlador.comenzar();
	}
	
	private static Vista procesarArgumentosVista(String[] args) {
		Vista vista =FactoriaVista.GRAFICA.crear();
		for(String argumento: args) {
			if(argumento.equals("-vtexto")){
				vista = FactoriaVista.TEXTO.crear();
			}
			else if(argumento.equals("-vgrafica")) {
				vista = FactoriaVista.GRAFICA.crear();
			}
		}
		return vista;
	}
	
	private static FactoriaFuenteDatos procesarFuenteDatos(String[] args) {
		FactoriaFuenteDatos factoria = FactoriaFuenteDatos.FICHEROS;
		for(String argumento: args) {
			if(argumento.equals("-fdficheros")){
				factoria = FactoriaFuenteDatos.FICHEROS;
			}
			else if(argumento.equals("-fdmariadb")) {
				factoria = FactoriaFuenteDatos.MARIADB;
			}else if (argumento.equals("-fdmongodb")) {
				factoria = FactoriaFuenteDatos.MONGODB;
			}
		}
		return factoria;
	}
}
