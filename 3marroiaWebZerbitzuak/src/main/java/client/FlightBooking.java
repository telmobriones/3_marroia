package client;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import domain.ConcreteFlight;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import service.WebServiceLogicInterface;
import service.WebServiceLogic;


public class FlightBooking extends JFrame {

private static final long serialVersionUID = 1L;
private JPanel contentPane = null;
private JLabel lblDepartCity = new JLabel("Departing city:");
private JLabel lblArrivalCity = new JLabel("Arrival City:");
private JLabel lblYear = new JLabel("Year:");
private JLabel lblRoomType = new JLabel("Room Type:");
private JLabel lblMonth = new JLabel("Month:");
private JLabel lblDay = new JLabel("Day:");;
private JLabel jLabelResult = new JLabel();
private JLabel searchResult = new JLabel();

private JTextField arrivalCity;
private JTextField departCity;
private JTextField day = null;
private JComboBox<String> months = null;
private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();

private JTextField year = null;

private JRadioButton bussinesTicket = null;
private JRadioButton firstTicket = null;
private JRadioButton touristTicket = null;

private ButtonGroup fareButtonGroup = new ButtonGroup();

private JButton lookforFlights = null;

private DefaultComboBoxModel<ConcreteFlight> flightInfo = new DefaultComboBoxModel<ConcreteFlight>();
private JComboBox<ConcreteFlight> flightCombo = null;

private JButton bookFlight = null;

private List<ConcreteFlight> concreteFlightList;

//private FlightManager businessLogic; // @jve:decl-index=0:
private static WebServiceLogicInterface wsl;

private ConcreteFlight selectedConcreteFlight;

private String dayFormat = "([0]?[1-9]|[1-2][0-9]|[3][0-1])";
private String yearFormat = "([2][0-9][0-9][0-9])";

/**
 * Launch the application.
 */

public static void main(String[] args) throws Exception {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				URL url = new URL("http://localhost:9999/ws?wsdl");
				QName qname = new QName("http://service/","WebServiceLogicService");
				Service service = Service. create (url, qname);
				wsl = service.getPort(WebServiceLogicInterface.class);
				FlightBooking frame = new FlightBooking();
				frame.setBusinessLogic(new WebServiceLogic());
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}

// Custom operations
public void setBusinessLogic(WebServiceLogicInterface g) {
	wsl = g;
}

private Date newDate(int year, int month, int day) {

	Calendar calendar = Calendar.getInstance();
	calendar.set(year, month, day, 0, 0, 0);
	calendar.set(Calendar.MILLISECOND, 0);

	return calendar.getTime();
}

/**
 * Create the frame
 * 
 * @return void
 */
@SuppressWarnings("unchecked")
private FlightBooking() {

	setTitle("Book Flight");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 440, 375);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	lblDepartCity = new JLabel("Depart City:");
	lblDepartCity.setBounds(21, 11, 103, 16);
	contentPane.add(lblDepartCity);

	arrivalCity = new JTextField();
	arrivalCity.setText("Bilbo");
	arrivalCity.setBounds(110, 34, 243, 26);
	contentPane.add(arrivalCity);
	arrivalCity.setColumns(10);

	departCity = new JTextField();
	departCity.setText("Donostia");
	departCity.setBounds(110, 6, 243, 26);
	contentPane.add(departCity);
	departCity.setColumns(10);

	year = new JTextField();
	year.setText("2022");
	year.setBounds(63, 70, 40, 26);
	contentPane.add(year);
	year.setColumns(10);

	lblYear = new JLabel("Year:");
	lblYear.setBounds(21, 75, 37, 16);
	contentPane.add(lblYear);

	lblMonth = new JLabel("Month:");
	lblMonth.setBounds(123, 75, 50, 16);
	contentPane.add(lblMonth);

	months = new JComboBox<String>();
	months.setBounds(178, 70, 116, 27);
	contentPane.add(months);
	months.setModel(monthNames);

	monthNames.addElement("January");
	monthNames.addElement("February");
	monthNames.addElement("March");
	monthNames.addElement("April");
	monthNames.addElement("May");
	monthNames.addElement("June");
	monthNames.addElement("July");
	monthNames.addElement("August");
	monthNames.addElement("September");
	monthNames.addElement("October");
	monthNames.addElement("November");
	monthNames.addElement("December");
	months.setSelectedIndex(1);

	lblDay = new JLabel("Day:");
	lblDay.setBounds(310, 75, 38, 16);
	contentPane.add(lblDay);

	day = new JTextField();
	day.setText("23");
	day.setBounds(345, 70, 50, 26);
	contentPane.add(day);
	day.setColumns(10);

	lblRoomType = new JLabel("Seat Type:");
	lblRoomType.setBounds(21, 105, 84, 16);
	contentPane.add(lblRoomType);

	bussinesTicket = new JRadioButton("Business");
	bussinesTicket.setSelected(true);
	fareButtonGroup.add(bussinesTicket);
	bussinesTicket.setBounds(99, 103, 99, 23);
	contentPane.add(bussinesTicket);

	firstTicket = new JRadioButton("First");
	fareButtonGroup.add(firstTicket);
	firstTicket.setBounds(200, 103, 75, 23);
	contentPane.add(firstTicket);

	touristTicket = new JRadioButton("Tourist");
	fareButtonGroup.add(touristTicket);
	touristTicket.setBounds(271, 103, 77, 23);
	contentPane.add(touristTicket);

	lookforFlights = new JButton("Look for Concrete Flights");
	lookforFlights.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			bookFlight.setEnabled(true);
			flightInfo.removeAllElements();
			bookFlight.setText("");

			if (!year.getText().matches(yearFormat)) {
				searchResult.setText("ERROR: Year is not valid!");
			} else if (!day.getText().matches(dayFormat)) {
				searchResult.setText("ERROR: Day is not valid!");
			} else {
				java.util.Date date = newDate(Integer.parseInt(year.getText()), months.getSelectedIndex(),
						Integer.parseInt(day.getText()));

				concreteFlightList = wsl.getConcreteFlights(departCity.getText(),
						arrivalCity.getText(), date);
				for (ConcreteFlight f : concreteFlightList) {
					System.out.println(f.toString());
					flightInfo.addElement(f);
				}
				if (concreteFlightList.isEmpty())
					searchResult.setText("No flights in that city in that date");
				else
					searchResult.setText("Choose an available flight in this list:");
			}
		}
	});

	lookforFlights.setBounds(86, 135, 261, 40);
	contentPane.add(lookforFlights);

	jLabelResult = new JLabel("");
	jLabelResult.setBounds(109, 220, 243, 16);
	contentPane.add(jLabelResult);

	flightCombo = new JComboBox<ConcreteFlight>();
	flightCombo.setModel(flightInfo);
	flightCombo.setBounds(86, 210, 261, 16);
	contentPane.add(flightCombo);

	flightCombo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (flightCombo.getSelectedItem() != null) {
				selectedConcreteFlight = (ConcreteFlight) flightCombo.getSelectedItem();
				if (bussinesTicket.isSelected() & selectedConcreteFlight.getBusinessNumber() == 0) {
					bookFlight.setEnabled(false);
					bookFlight.setText("No available tickets for that fare");
				} else if (firstTicket.isSelected() & selectedConcreteFlight.getFirstNumber() == 0) {
					bookFlight.setEnabled(false);
					bookFlight.setText("No available tickets for that fare");
				} else if (touristTicket.isSelected() & selectedConcreteFlight.getTouristNumber() == 0) {
					bookFlight.setEnabled(false);
					bookFlight.setText("No available tickets for that fare");
				} else {
					bookFlight.setEnabled(true);
					bookFlight.setText("Book this flight: " + selectedConcreteFlight); // TODO Auto-generated Event
																						// stub valueChanged()
				}

			}
		}
	});

	bookFlight = new JButton("");
	bookFlight.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int num = 0;
			boolean error = false;
			if (bussinesTicket.isSelected()) {
				num = selectedConcreteFlight.getBusinessNumber();
				if (num > 0) {
					selectedConcreteFlight.setBusinessNumber(num - 1);
					wsl.updateBusinessNumber(selectedConcreteFlight);
				}
				else
					error = true;
			} else if (firstTicket.isSelected()) {
				num = selectedConcreteFlight.getFirstNumber();
				if (num > 0) {
					selectedConcreteFlight.setFirstNumber(num - 1);
					wsl.updateFirstNumber(selectedConcreteFlight);
				}
				else
					error = true;
			} else if (touristTicket.isSelected()) {
				num = selectedConcreteFlight.getTouristNumber();
				if (num > 0) {
					selectedConcreteFlight.setTouristNumber(num - 1);
					wsl.updateTouristNumber(selectedConcreteFlight);
				}
				else
					error = true;
			}
			if (error)
				bookFlight.setText("Error: There were no seats available!");
			else
				bookFlight.setText("Booked. #seat left: " + (num - 1));
			bookFlight.setEnabled(false);
		}
	});
	bookFlight.setBounds(21, 263, 399, 35);
	contentPane.add(bookFlight);

	lblArrivalCity.setBounds(21, 39, 84, 16);
	contentPane.add(lblArrivalCity);

	searchResult.setBounds(86, 185, 314, 16);
	contentPane.add(searchResult);
}
} // @jve:decl-index=0:visual-constraint="18,9"
