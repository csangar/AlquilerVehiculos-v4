package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {
	TURISMO("Turismo"),
	AUTOBUS("Autobus"),
	FURGONETA("Furgoneta");
	
    private String nombre;

	private TipoVehiculo(String nombre) {
		this.nombre = nombre;
	}
    
	private static boolean esOrdinalValido(int ordinal) {
	        return (ordinal >= 0 && ordinal < Accion.values().length);
	    }
    public static TipoVehiculo get(int ordinal) {
    	if(!esOrdinalValido(ordinal)) {
    		throw new IllegalArgumentException("El ordinal no es válido");
    	}
        TipoVehiculo opcion = null;
        if(esOrdinalValido(ordinal)) {
            opcion = TipoVehiculo.values()[ordinal];
        }
        return opcion;
    }
    
    public static TipoVehiculo get(Vehiculo vehiculo) {
    	TipoVehiculo vehiculoDevuelto = null;
    	if (vehiculo instanceof Turismo) {
    		vehiculoDevuelto = TipoVehiculo.TURISMO;
    	}
    	if(vehiculo instanceof Autobus) {
    		vehiculoDevuelto = TipoVehiculo.AUTOBUS;
    	}
    	if(vehiculo instanceof Furgoneta) {
    		vehiculoDevuelto = TipoVehiculo.FURGONETA;
    	}
        return vehiculoDevuelto;
    }
    @Override
    public String toString() {
        return String.format("%d: %s ", ordinal(),nombre);
    }
}
