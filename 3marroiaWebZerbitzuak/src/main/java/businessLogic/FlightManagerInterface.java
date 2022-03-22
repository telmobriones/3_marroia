package businessLogic;

import java.util.Collection;
import java.util.Date;

import domain.ConcreteFlight;

public interface FlightManagerInterface{
	Collection<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, Date date);
	void updateBusinessNumber(ConcreteFlight cf);
	void updateFirstNumber(ConcreteFlight cf);
	void updateTouristNumber(ConcreteFlight cf);
}