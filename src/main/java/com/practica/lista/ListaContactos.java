package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;

	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas.
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada
	 * en un instante
	 */
	public void insertarNodoTemporal (PosicionPersona p) {
		NodoTemporal aux = lista, ant=null;
		boolean salir=false,  encontrado = false;
		/**
		 * Busco la posición adecuada donde meter el nodo de la lista, excepto
		 * que esté en la lista. Entonces solo añadimos una coordenada.
		 */
		while (aux!=null && !salir) {
			if(aux.getFecha().compareTo(p.getFechaPosicion())==0) {
				encontrado = true;
				salir = true;
				insertarNodoListaCoordenadas(p, aux);
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())<0) {
				ant = aux;
				aux=aux.getSiguiente();
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())>0) {
				salir=true;
			}
		}
		if(!encontrado) {
			insertarNuevoNodo(p, ant, aux);
		}
	}

	private void insertarNuevoNodo(PosicionPersona p, NodoTemporal ant, NodoTemporal aux){
		NodoTemporal nuevo = new NodoTemporal();
		nuevo.setFecha(p.getFechaPosicion());
		insertarNodoListaCoordenadas(p, nuevo);

		if(ant!=null) {
			nuevo.setSiguiente(aux);
			ant.setSiguiente(nuevo);
		}else {
			nuevo.setSiguiente(lista);
			lista = nuevo;
		}
		this.size++;
	}

	private void insertarNodoListaCoordenadas(PosicionPersona p, NodoTemporal nuevo) {
		NodoPosicion npActual = nuevo.getListaCoordenadas();
		NodoPosicion npAnt=null;
		boolean npEncontrado = false;
		while (npActual!=null && !npEncontrado) {
			if(npActual.getCoordenada().equals(p.getCoordenada())) {
				npEncontrado=true;
				npActual.setNumPersonas(npActual.getNumPersonas()+1);
			}else {
				npAnt = npActual;
				npActual = npActual.getSiguiente();
			}
		}
		if(!npEncontrado) {
			NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(),  1, null);
			if(nuevo.getListaCoordenadas()==null)
				nuevo.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
	}

	public int tamanioLista () {
		return this.size;
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista;
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	// 1. El método genérico privado que encapsula TODA la lógica repetida

	private int procesarInstantes(FechaHora inicio, FechaHora fin, boolean contarPersonas) {
		if (this.size == 0) {
			return 0;
		}
		NodoTemporal aux = lista;
		int cont = 0;
		while (aux != null) {
			if (aux.getFecha().compareTo(inicio) >= 0 && aux.getFecha().compareTo(fin) <= 0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while (nodo != null) {
					if (contarPersonas) {
						cont = cont + nodo.getNumPersonas(); // Caso del método 1
					} else {
						cont = cont + 1;                     // Caso del método 2
					}

					nodo = nodo.getSiguiente();
				}
			}
			aux = aux.getSiguiente(); // Avanzar el nodo limpiamente fuera del if-else
		}
		return cont;
	}


	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return procesarInstantes(inicio, fin, true);
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return procesarInstantes(inicio, fin, false);
	}



	@Override
	public String toString() {
		String cadena="";
		int cont;
		NodoTemporal aux = lista;
		for(cont=1; cont<size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
			aux=aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}
}
