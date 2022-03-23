package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.ConcreteFlight;
import domain.Flight;

@WebService
public interface WebServiceLogicInterface {
	@WebMethod
	List<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, Date date);
	@WebMethod
	void updateBusinessNumber(ConcreteFlight cf);
	@WebMethod
	void updateFirstNumber(ConcreteFlight cf);
	@WebMethod
	void updateTouristNumber(ConcreteFlight cf);
	
}
