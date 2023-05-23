package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {
	private List<Alquiler> coleccionAlquileres;
	private static final File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";	
	private static Alquileres instancia;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if(instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;
	}
	
	public void comenzar() {
			Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES);
			if (documento == null) {
				throw new NullPointerException("El documento no puede ser nulo");
			} else {
				leerDom(documento);
			}
	}

	private void leerDom(Document documentoXML) {
		NodeList alquileres = documentoXML.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node alquiler = alquileres.item(i);
			if (alquiler.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element) alquiler));
				} catch (Exception e) {
					System.out.println("Fallo al leer el alquiler nº "+ i);
				}
			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String cliente = elemento.getAttribute(CLIENTE);
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
		String vehiculo = elemento.getAttribute(VEHICULO);
		Cliente cli = Cliente.getClienteConDni(cliente);
		Vehiculo vehi = Vehiculo.getVehiculoConMatricula(vehiculo);
		LocalDate fechaAl = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		Alquiler alquiler = null;
		alquiler =  new Alquiler(cli, vehi, fechaAl);
		if(elemento.hasAttribute(FECHA_DEVOLUCION)) {
			LocalDate fechaDev = LocalDate.parse(fechaDevolucion, FORMATO_FECHA);
		alquiler.devolver(fechaDev);
		}
		return alquiler;
	}
	
	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_ALQUILERES);
		//System.out.println("¡FIN!");
	}
	
	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler Alquiler : getInstancia().get()) {
				Element elementoDato = getElemento(documentoXml, Alquiler);
				documentoXml.getDocumentElement().appendChild(elementoDato);
			}
		}
		return documentoXml;
	}
	
	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		 elementoAlquiler.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		 elementoAlquiler.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		 elementoAlquiler.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		 if (alquiler.getFechaDevolucion() != null) {
				elementoAlquiler.setAttribute(FECHA_DEVOLUCION, alquiler.getFechaDevolucion().format(FORMATO_FECHA));
			}
		return elementoAlquiler;
	}
	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (cliente.equals(alquiler.getCliente())) {
				lista.add(alquiler);
			}
		}
		return lista;
	}

	@Override
	public List<Alquiler> get(Vehiculo turismo) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (turismo.equals(alquiler.getVehiculo())) {
				lista.add(alquiler);
			}
		}
		return lista;
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
			}
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() != null
					&& alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() != null
					&& alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
			}
		}
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquilerAbierto = getAlquilerAbierto(cliente);
		if (alquilerAbierto == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquilerAbierto.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		Alquiler alquilerAbierto = null;
		while (iterador.hasNext() && alquilerAbierto == null) {
			Alquiler alquilerTemporal = iterador.next();
			if (alquilerTemporal.getCliente().equals(cliente) && alquilerTemporal.getFechaDevolucion() == null) {
				alquilerAbierto = alquilerTemporal;
			}
		}
		return alquilerAbierto;
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquilerAbierto = getAlquilerAbierto(vehiculo);
		if (alquilerAbierto == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquilerAbierto.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();
		Alquiler alquilerAbierto = null;
		while (iterador.hasNext() && alquilerAbierto == null) {
			Alquiler alquilerTemporal = iterador.next();
			if (alquilerTemporal.getVehiculo().equals(vehiculo) && alquilerTemporal.getFechaDevolucion() == null) {
				alquilerAbierto = alquilerTemporal;
			}
		}
		return alquilerAbierto;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int nuevoA = coleccionAlquileres.indexOf(alquiler);
		if (nuevoA != -1) {
			return coleccionAlquileres.get(nuevoA);
		}
		return null;
	}
}
