package persistence;

import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

import domain.ConcreteFlight;
import domain.Flight;

public class Flight_db_Access {
	private EntityManager db;
	private EntityManagerFactory emf;
	String fileName = "flights.odb";

	public Flight_db_Access() {
		emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
		db = emf.createEntityManager();
		System.out.println("DataBase opened");
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public void storeFlight(String flightCode, String departingCity, String arrivingCity) {

		db.getTransaction().begin();
		Flight flight = new Flight(flightCode, departingCity, arrivingCity);
		db.persist(flight);
		db.getTransaction().commit();

		System.out.println("Gordeta " + flight);
	}

	public void addConcreteFlight(String flightCode, String concreteFlightCode, Date date, int businessNumber,
			int firstNumber, int touristNumber, String time) {
		db.getTransaction().begin();
		Flight flight = db.find(Flight.class, flightCode);
		flight.addConcreteFlight(concreteFlightCode, date, businessNumber, firstNumber, touristNumber, time);
		db.getTransaction().commit();

	}

	public Collection<ConcreteFlight> getCFByRouteDate(String departingCity, String arrivingCity, Date date) {
		db.getTransaction().begin();
		TypedQuery<ConcreteFlight> query = db.createQuery(
				"SELECT cf FROM ConcreteFlight cf WHERE cf.flight.arrivingCity= :arrC AND cf.flight.departingCity= :depC AND cf.date= :date",
				ConcreteFlight.class);
		Collection<ConcreteFlight> flights = (Collection<ConcreteFlight>) query.setParameter("depC", departingCity)
				.setParameter("arrC", arrivingCity).setParameter("date", date).getResultList();
		return flights;
	}

	public void updateBusinessNumber(int bN, String concreteFlightCode) {
		db.getTransaction().begin();
		Query query = db.createQuery("UPDATE ConcreteFlight SET businessNumber=?1 WHERE concreteFlightCode=?2");
		query.setParameter(1, bN);
		query.setParameter(2, concreteFlightCode);
		int n = query.executeUpdate();
		db.getTransaction().commit();
		System.out.println("Successfully purchased " + n + " Business ticket. The flight " + concreteFlightCode
				+ " has been updated in the DB!");
	}

	public void updateFirstNumber(int fN, String concreteFlightCode) {
		db.getTransaction().begin();
		Query query = db.createQuery("UPDATE ConcreteFlight SET firstNumber=?1 WHERE concreteFlightCode=?2");
		query.setParameter(1, fN);
		query.setParameter(2, concreteFlightCode);
		int n = query.executeUpdate();
		db.getTransaction().commit();
		System.out.println("Successfully purchased " + n + " First class ticket. The flight " + concreteFlightCode
				+ " has been updated in the DB!");
	}

	public void updateTouristNumber(int tN, String concreteFlightCode) {
		db.getTransaction().begin();
		Query query = db.createQuery("UPDATE ConcreteFlight SET touristNumber=?1 WHERE concreteFlightCode=?2");
		query.setParameter(1, tN);
		query.setParameter(2, concreteFlightCode);
		int n = query.executeUpdate();
		db.getTransaction().commit();
		System.out.println("Successfully purchased " + n + " Tourist ticket. The flight " + concreteFlightCode
				+ " has been updated in the DB!");
	}

//	public ConcreteFlight getCFlightByCFCode(String concreteFlightCode) {
//		ConcreteFlight cf;
//		db.getTransaction().begin();
//		TypedQuery<ConcreteFlight> query = db
//				.createQuery("SELECT cf FROM ConcreteFlight cf WHERE concreteFlightCode=?1", ConcreteFlight.class);
//		query.setParameter(1, concreteFlightCode);
//		cf = query.getSingleResult();
//
//		return cf;
//	}

//	public void getAllFlights() {
//		db.getTransaction().begin();
//		TypedQuery<Flight> query = db.createQuery("SELECT f FROM Flight f", Flight.class);
//		List<Flight> flights = query.getResultList();
//
//		System.out.println("Datu basearen edukia:");
//		for (Flight p : flights)
//			System.out.println(p.toString());
//		db.getTransaction().commit();
//
//	}

}