package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import domain.ConcreteFlight;
import domain.Flight;
import persistence.Flight_db_Access;

@WebService(endpointInterface = "service.WebServiceLogicInterface")
public class WebServiceLogic implements WebServiceLogicInterface {
	ArrayList<Flight> flights;

	public WebServiceLogic() {
		Flight_db_Access flightsDB = new Flight_db_Access();

		Flight f1 = new Flight("F1", "Donostia", "Bilbo");
		String fc1 = f1.getFlightCode();

		Flight f2 = new Flight("F2", "Bilbo", "Donostia");
		String fc2 = f2.getFlightCode();

		flightsDB.storeFlight(fc1, f1.getDepartingCity(), f1.getArrivingCity());

		flightsDB.addConcreteFlight(fc1, fc1 + "CF1", newDate(2022, 1, 22), 1, 2, 3, "12:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF2", newDate(2022, 1, 23), 3, 0, 3, "12:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF3", newDate(2022, 1, 23), 1, 2, 2, "13:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF4", newDate(2022, 1, 23), 3, 3, 0, "14:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF5", newDate(2022, 1, 23), 3, 3, 0, "15:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF6", newDate(2022, 1, 23), 3, 3, 0, "16:00");
		flightsDB.addConcreteFlight(fc1, fc1 + "CF7", newDate(2022, 1, 23), 3, 3, 0, "17:00");

		flightsDB.storeFlight(fc2, f2.getDepartingCity(), f2.getArrivingCity());

		flightsDB.addConcreteFlight(fc2, fc2 + "CF1", newDate(2022, 1, 21), 3, 3, 0, "12:00");
		flightsDB.addConcreteFlight(fc2, fc2 + "CF2", newDate(2022, 1, 22), 3, 3, 0, "12:00");
		flightsDB.addConcreteFlight(fc2, fc2 + "CF3", newDate(2022, 1, 23), 3, 3, 0, "12:00");

		flightsDB.close();
	}

	public List<ConcreteFlight> getConcreteFlights(String departingCity, String arrivingCity, Date date) {
		Flight_db_Access flightsDB = new Flight_db_Access();
		Collection<ConcreteFlight> res = flightsDB.getCFByRouteDate(departingCity, arrivingCity, date);
		flightsDB.close();
		List<ConcreteFlight> cfList = new ArrayList<ConcreteFlight>(res);
		return cfList;

	}

	public void updateBusinessNumber(ConcreteFlight cf) {
		Flight_db_Access flightsDB = new Flight_db_Access();
		flightsDB.updateBusinessNumber(cf.getBusinessNumber(), cf.getConcreteFlightCode());
		flightsDB.close();
	}

	public void updateFirstNumber(ConcreteFlight cf) {
		Flight_db_Access flightsDB = new Flight_db_Access();
		flightsDB.updateFirstNumber(cf.getFirstNumber(), cf.getConcreteFlightCode());
		flightsDB.close();
	}

	public void updateTouristNumber(ConcreteFlight cf) {
		Flight_db_Access flightsDB = new Flight_db_Access();
		flightsDB.updateTouristNumber(cf.getTouristNumber(), cf.getConcreteFlightCode());
		flightsDB.close();
	}

	private Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
}
