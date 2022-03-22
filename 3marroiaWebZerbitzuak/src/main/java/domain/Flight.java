package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlAccessType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight implements Serializable{
//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//

	@Id
	@XmlID
	public String flightCode;
	String departingCity;
	String arrivingCity;
	
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	Collection<ConcreteFlight> concreteFlights;

//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//__//
	
	public Flight() {
		super();
	}
	
	public Flight(String flightCode, String departingCity, String arrivingCity) {
		super();

		this.flightCode = flightCode;
		this.departingCity = departingCity;
		this.arrivingCity = arrivingCity;
		concreteFlights = new ArrayList<ConcreteFlight>();
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getDepartingCity() {
		return departingCity;
	}

	public void setDepartingCity(String departingCity) {
		this.departingCity = departingCity;
	}

	public String getArrivingCity() {
		return arrivingCity;
	}

	public void setArrivingCity(String arrivingCity) {
		this.arrivingCity = arrivingCity;
	}

	public void addConcreteFlight(String concreteFlighCode, Date date, int businessNumber, int firstNumber,
			int touristNumber, String time) {
		ConcreteFlight cf = new ConcreteFlight(concreteFlighCode, date, businessNumber, firstNumber, touristNumber,
				time, this);
		concreteFlights.add(cf);
	}

	public Collection<ConcreteFlight> getConcreteFlights() {
		return concreteFlights;
	}

	public void setConcreteFlights(Collection<ConcreteFlight> concreteFlights) {
		this.concreteFlights = concreteFlights;
	}

	public String toString() {
		return flightCode + "/" + departingCity + "/" + arrivingCity;
	}
}
