package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

public enum Accion {

	
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			vista.terminar();
		}
	}, INSERTAR_CLIENTE("Insertar un cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.insertarCliente();
		}
	}, INSERTAR_VEHICULO("Insertar un vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.insertarVehiculo();
		}
	},
	INSERTAR_ALQUILER("Insertar un alquiler") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.insertarAlquiler();
		}
	}, BUSCAR_CLIENTE("Buscar un cliente") {
		@Override
		public void ejecutar() {
			vista.buscarCliente();
		}
	}, BUSCAR_VEHICULO("Buscar un vehiculo") {
		@Override
		public void ejecutar() {
			vista.buscarVehiculo();
		}
	},
	BUSCAR_ALQUILER("Buscar un alquiler") {
		@Override
		public void ejecutar() {
			vista.buscarAlquiler();
		}
	}, MODIFICAR_CLIENTE("Modificar un cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.modificarCliente();
		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver un alquiler de un cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.devoverAlquilerCliente();
		}
	}, DEVOLVER_ALQUILER_VEHICULO("Devolver un alquiler de un vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.devoverAlquilerVehiculo();
		}
	},
	BORRAR_CLIENTE("Borrar un cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.borrarCliente();
		}
	}, BORRAR_VEHICULO("Borrar un vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.borrarVehiculo();
		}
	}, BORRAR_ALQUILER("Borrar un alquiler") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			vista.borrarAlquiler();
		}
	},
	LISTAR_CLIENTES("Listar los clientes") {
		@Override
		public void ejecutar() {
			vista.listarClientes();
		}
	}, LISTAR_VEHICULOS("Listar los vehiculos") {
		@Override
		public void ejecutar() {
			vista.listarVehiculos();
		}
	},
	LISTAR_ALQUILERES("Listar los alquileres") {
		@Override
		public void ejecutar() {
			vista.listarAlquiler();
		}
	}, LISTAR_ALQUILERES_CLIENTE("Listar los alquileres de un cliente") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresCliente();
		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar los alquileres de un vehiculo") {
		@Override
		public void ejecutar() {
			vista.listarAlquileresVehiculo();
		}
	},
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar las estadisticas mensuales") {
		@Override
		public void ejecutar() {
			vista.mostrarEstadisticasMensualesTipoVehiculo();
		}
	};
	private static VistaTexto vista;
	private String texto;

	static void setVista(VistaTexto vista) {
		Accion.vista = vista;
	}

	public abstract void ejecutar() throws OperationNotSupportedException;

	private static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal < Accion.values().length);
	}

	public static Accion get(int ordinal) {
		if (!esOrdinalValido(ordinal)) {
			throw new IllegalArgumentException("El ordinal no es válido");
		}
		Accion accion = null;
		if (esOrdinalValido(ordinal)) {
			accion = Accion.values()[ordinal];
		}
		return accion;
	}

	private Accion(String cadena) {
		this.texto = cadena;
	}

	@Override
	public String toString() {
		return String.format("%d: %s ", ordinal(), texto);
	}
}
