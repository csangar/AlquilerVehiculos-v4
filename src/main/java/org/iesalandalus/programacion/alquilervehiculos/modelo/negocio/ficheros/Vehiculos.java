package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {
	private List<Vehiculo> coleccionVehiculos;
	private static final File FICHERO_VEHICULOS = new File(
			String.format("%s%s%s", "datos", File.separator, "vehiculos.xml"));
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "vehiculo";
	private static final String MARCA = "marca";
	private static final String MODELO = "modelo";
	private static final String MATRICULA = "matricula";
	private static final String CILINDRADA = "cilindrada";
	private static final String PLAZAS = "plazas";
	private static final String PMA = "pma";
	private static final String TIPO = "tipo";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";
	private static Vehiculos instancia;

	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	static Vehiculos getInstancia() {
		if (instancia == null) {
			instancia = new Vehiculos();
		}
		return instancia;
	}

	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS);
		if (documento == null) {
			throw new NullPointerException("El documento no puede ser nulo");
		} else {
			leerDom(documento);
		}
	}

	private void leerDom(Document documentoXML) {
		NodeList vehiculos = documentoXML.getElementsByTagName(VEHICULO);
		for (int i = 0; i < vehiculos.getLength(); i++) {
			Node vehiculo = vehiculos.item(i);
			if (vehiculo.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getVehiculo((Element) vehiculo));
				} catch (Exception e) {
					System.out.println("Fallo al leer el vehiculo nº " + i);
				}
			}
		}
	}

	private Vehiculo getVehiculo(Element elemento) {
		Vehiculo vehiculo = null;
		String marca = elemento.getAttribute(MARCA);
		String modelo = elemento.getAttribute(MODELO);
		String matricula = elemento.getAttribute(MATRICULA);
		String tipo = elemento.getAttribute(TIPO);
		if (tipo.equals(TURISMO)) {
			String Scilindrada = elemento.getAttribute(CILINDRADA);
			int cilindrada = Integer.parseInt(Scilindrada);
			vehiculo = new Turismo(marca, modelo, cilindrada, matricula);
		}
		if (tipo.equals(AUTOBUS)) {
			String Splazas = elemento.getAttribute(PLAZAS);
			int plazas = Integer.parseInt(Splazas);
			vehiculo = new Autobus(marca, modelo, plazas, matricula);
		}
		if (tipo.equals(FURGONETA)) {
			String Spma = elemento.getAttribute(PMA);
			String Splazas = elemento.getAttribute(PLAZAS);
			int pma = Integer.parseInt(Spma);
			int plazas = Integer.parseInt(Splazas);
			vehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		return vehiculo;
	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_VEHICULOS);
		//System.out.println("¡FIN!");
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Vehiculo vehiculo : getInstancia().get()) {
				Element elementoDato = getElemento(documentoXml, vehiculo);
				documentoXml.getDocumentElement().appendChild(elementoDato);
			}
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
		Element elementoVehiculo = documentoXml.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MARCA, vehiculo.getMarca());
		elementoVehiculo.setAttribute(MATRICULA, vehiculo.getMatricula());
		elementoVehiculo.setAttribute(MODELO, vehiculo.getModelo());
		if (vehiculo instanceof Turismo turismo) {
			elementoVehiculo.setAttribute(CILINDRADA, String.valueOf(turismo.getCilindrada()));
			elementoVehiculo.setAttribute(TIPO, TURISMO);
		}
		if (vehiculo instanceof Autobus autobus) {
			elementoVehiculo.setAttribute(CILINDRADA, String.valueOf(autobus.getPlazas()));
			elementoVehiculo.setAttribute(CILINDRADA, AUTOBUS);
		}
		if (vehiculo instanceof Furgoneta furgoneta) {
			elementoVehiculo.setAttribute(PMA, String.valueOf(furgoneta.getPma()));
			elementoVehiculo.setAttribute(PLAZAS, String.valueOf(furgoneta.getPlazas()));
			elementoVehiculo.setAttribute(TIPO, FURGONETA);
		}
		return elementoVehiculo;
	}

	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (buscar(vehiculo) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		coleccionVehiculos.add(vehiculo);
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		int nuevoV = coleccionVehiculos.indexOf(vehiculo);
		if (nuevoV != -1) {
			return coleccionVehiculos.get(nuevoV);
		} else {
			return null;
		}
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (!coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		coleccionVehiculos.remove(vehiculo);
	}
}