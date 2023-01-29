package model.users;

import es.uam.eps.padsof.telecard.*;
import model.exceptions.*;
import model.acts.performances.*;
import model.app.TheatreTickets;
import model.areas.*;
import model.enums.Heuristic;
import model.enums.ReservationStatus;
import model.users.notifications.*;
import model.operations.*;

import java.time.LocalDateTime;
import java.util.*;
import model.operations.payments.*;
import model.areas.positions.*;
import model.acts.cycles.Cycle;

/**
 * This class is for Customers
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Customer extends RegisteredUser {
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/** List of all the purchases this user has performed */
	private List<Purchase> opsPurchases = new LinkedList<>();
	/** List of all the reservations this user has performed */
	private List<Reservation> opsReservations = new LinkedList<>();
	/** Set of the payment methods this user has, like passes and credit cards */
	private Set<AnnualPass> payMethAnnual = new HashSet<>();
	/** Set of the payment methods this user has, like passes and credit cards */
	private Set<CyclePass> payMethCycle = new HashSet<>();
	/** List of pending notification */
	private List<Notification> notis = new LinkedList<>();
	/** List of all performances a customer is waiting for */
	private List<Performance> waiting = new LinkedList<>();

	/**
	 * Customer class constructor, saves a Registered user as Customer
	 * 
	 * @param name     same as the username of the registered user
	 * @param password same as the password of the registered user
	 */
	public Customer(String name, String password) {
		super(name, password);
	}

	/**
	 * Getter for the payment methods
	 * 
	 * @return a set with the payment methods
	 */
	public Set<PaymentMethod> getPayments() {
		Set<PaymentMethod> pays = new HashSet<>();
		pays.addAll(payMethAnnual);
		pays.addAll(payMethCycle);
		return pays;
	}

	/**
	 * This method verifies that purchases can be made with the payment methods
	 * indicated
	 * 
	 * @param p  Performance to buy
	 * @param pm Payment methods available
	 * @param n  Number of tickets requested
	 * @return True if a purchase can be made with these payment methods
	 */
	private boolean verifyPaymentMethods(Performance p, Set<PaymentMethod> pm, int n) {
		boolean isThereCreditCard = false;
		/** Verifying payment methods */
		for (PaymentMethod pay : pm) {
			if (pay instanceof Pass) {
				if (!((Pass) pay).isValidToUse(p)) {
					return false;
				}
			} else if (pay instanceof CreditCard) {
				isThereCreditCard = true;
			}
		}

		if (pm.size() < n && !isThereCreditCard) {
			/**
			 * If there is no credit card to pay the rest of the tickets, we cannot continue
			 * with the purchase
			 */
			return false;
		}
		return true;
	}

	/**
	 * method for making a purchase in a standing area
	 * 
	 * @param p  performance in which the ticket is to be bought
	 * @param a  the area of the ticket in that performance
	 * @param n  number of tickets
	 * @param pm list of payment methods for each ticket. If various credit cards
	 *           are passed, only one will be used
	 * @return true if tickets are bought succesfully
	 */
	public boolean buyTickets(Performance p, Standing a, int n, Set<PaymentMethod> pm) {
		if (n > TheatreTickets.getInstance().getMaxTicketsPerPurchase()) {
			return false;
		}
		/**
		 * We are creating a list of positions with PositionIdentifier. They are the
		 * same object as a PositionIdentifier merely generalizes the design
		 */
		List<Position> toGet = Collections.nCopies(n, a.getAPosition());
		if (p.availableCapacity(a, n) == false) {
			return false;
		}

		if (verifyPaymentMethods(p, pm, n) == false)
			return false;

		try {
			opsPurchases.add(new Purchase(this, p, toGet, pm));
		} catch (OrderRejectedException | CreationException ex) {
			System.out.println(ex);
			return false;
		}

		return true;
	}

	/**
	 * Method for making a purchase in a sitting area, using manual selection by
	 * customer
	 * 
	 * @param p     performance in which the ticket is to be bought
	 * @param s     the area of the ticket in that performance
	 * @param pm    list of payment methods for each ticket
	 * @param seats set of seats the customer wants to buy tickets for
	 * @return true if tickets are bought succesfully
	 */
	public boolean buyTickets(Performance p, Sitting s, Set<PaymentMethod> pm, Set<Seat> seats) {
		if (seats.size() > TheatreTickets.getInstance().getMaxTicketsPerPurchase())
			return false;
		if (p.availableSeats(s, seats) == false) {
			return false;
		}

		if (verifyPaymentMethods(p, pm, seats.size()) == false)
			return false;

		try {
			opsPurchases.add(new Purchase(this, p, List.copyOf(seats), pm));
		} catch (OrderRejectedException | CreationException ex) {
			System.out.println(ex);
			return false;
		}
		return true;

	}

	/**
	 * Method for making a purchase in a sitting area, using automatic selection by
	 * heuristics
	 * 
	 * @param p  performance in which the ticket is to be bought
	 * @param s  the area of the ticket in that performance
	 * @param n  number of tickets
	 * @param pm list of payment methods for each ticket
	 * @param h  the heuristic the customer chose for automatic seat selection
	 * @return true if tickets are bought succesfully
	 */
	public boolean buyTickets(Performance p, Sitting s, int n, Set<PaymentMethod> pm, Heuristic h) {
		if (n > TheatreTickets.getInstance().getMaxTicketsPerPurchase())
			return false;
		Set<Seat> seats = p.getSeats(s, n, h);
		if (seats == null) {
			return false;
		}
		/** p.availableSeats will be true always */
		return buyTickets(p, s, pm, seats);
	}

	/**
	 * method for reserving positions in a standing area
	 * 
	 * @param p performance in which the ticket is to be reserved
	 * @param a the area of the ticket in that performance
	 * @param n number of tickets
	 * @return true if reservation is performed successfully
	 */
	public boolean reserveTickets(Performance p, Standing a, int n) {
		List<Position> toGet = Collections.nCopies(n, a.getAPosition());
		if (n > TheatreTickets.getInstance().getMaxTicketsPerReservation())
			return false;
		if (p.availableCapacity(a, n) == false) {
			return false;
		}

		try {
			opsReservations.add(new Reservation(this, p, toGet));
		} catch (CreationException ce) {
			System.out.println(ce);
			return false;
		}

		return true;
	}

	/**
	 * Method for reserving seats in a sitting area, using manual selection by
	 * customer
	 * 
	 * @param p     performance in which the ticket is to be reserved
	 * @param s     the area of the ticket in that performance
	 * @param seats set of seats the customer wants to reserve tickets for
	 * @return true if reservation is performed successfully
	 */
	public boolean reserveTickets(Performance p, Sitting s, Set<Seat> seats) {
		if (seats.size() > TheatreTickets.getInstance().getMaxTicketsPerReservation())
			return false;
		if (LocalDateTime.now().isAfter(p.getDate().minusHours(TheatreTickets.getInstance().getHoursForReservation())))
			return false; // Cannot make reservation now.
		if (p.availableSeats(s, seats) == false) {
			return false;
		}

		try {
			opsReservations.add(new Reservation(this, p, List.copyOf(seats)));
		} catch (CreationException ce) {
			System.out.println(ce);
			return false;
		}

		return true;
	}

	/**
	 * Method for reserving seats in a sitting area, using automatic selection by
	 * heuristics
	 * 
	 * @param p performance in which the ticket is to be reserved
	 * @param s the area of the ticket in that performance
	 * @param n number of tickets
	 * @param h the heuristic the customer chose for automatic seat selection
	 * @return true if reservation is performed successfully
	 */
	public boolean reserveTickets(Performance p, Sitting s, int n, Heuristic h) {
		if (n > TheatreTickets.getInstance().getMaxTicketsPerReservation())
			return false;
		Set<Seat> seats = p.getSeats(s, n, h);
		if (seats == null) {
			return false;
		}

		return reserveTickets(p, s, seats);
	}

	/**
	 * Looks for purchase for certain ID.
	 *
	 * @param pID performance id
	 * @return The performance with id pID. null if not found
	 */
	public Purchase getPurchase(int pID) {
		for (Purchase p : opsPurchases) {
			if (p.getId() == pID) {
				return p;
			}
		}
		return null;
	}

	/**
	 * @return List of all purchases made by this customer
	 */
	public List<Purchase> getPurchases() {
		return opsPurchases;
	}

	/**
	 * Looks for reservation with a specified ID
	 * 
	 * @param rID ID to compare to
	 * @return Null if no reservations were found with that id. The reservation
	 *         otherwise
	 */
	public Reservation getReservation(int rID) {
		for (Reservation o : opsReservations) {
			if (o.getId() == rID) {
				return o;
			}
		}
		return null;
	}

	/**
	 * @return A List containing only active reservations
	 */
	public List<Reservation> getActiveReservations() {
		List<Reservation> ret = new LinkedList<>();

		for (Reservation o : opsReservations) {
			if (o.getStatus() == ReservationStatus.ACTIVE)
				ret.add(o);
		}
		return ret;
	}

	/**
	 * @return List of all reservations
	 */
	public List<Reservation> getReservations() {
		return opsReservations;
	}

	/**
	 * Confirms a reservation previously made if possible
	 *
	 * @param pm Set of payment method to use
	 * @param op Reservation to confirm
	 * @return True if confirmation was succesful
	 */
	public boolean confirmReservation(Set<PaymentMethod> pm, Reservation op) {
		if (verifyPaymentMethods(op.getPerformance(), pm, op.getNumberOfTickets()) == false)
			return false;

		try {
			op.confirm(pm);
		} catch (OrderRejectedException ex) {
			System.out.println(ex);
			return false;
		}
		opsReservations.remove(op);
		return true;
	}

	/**
	 * Cancels a reservation of this user and deletes from list of reservations
	 *
	 * @param op Reservation to cancel
	 * @return True if reservation was removed succesfully
	 */
	public boolean cancelReservation(Reservation op) {
		if (opsReservations.contains(op)) {
			opsReservations.remove(op);
			op.cancel();
			return true;
		}
		return false;
	}

	/**
	 * Adds a notification to the customer notification entry
	 * 
	 * @param n Notification to add
	 */
	public void addNotification(Notification n) {
		this.notis.add(n);
	}

	/**
	 * @return Returns a string showing all notifications
	 */
	public List<Notification> getNotifications() {
		return notis;
	}

	/**
	 * Removes all notifications of the customer
	 */
	public void deleteNotification() {
		notis = new LinkedList<>();
	}

	/**
	 * @return Number of notifications pending
	 */
	public int getNumberOfNotifications() {
		return notis.size();
	}

	/**
	 * Purchases annual pass
	 *
	 * @param a  Area where annual pass is solicited
	 * @param cc CreditCard to pay annual pass with
	 * @return True if pass was purchased succesfuly
	 */
	public boolean purchaseAnnualPass(NonComposite a, CreditCard cc) {

		try {
			cc.pay(TheatreTickets.getInstance().getPassPrice(a), "Purchasing annual pass", true);
			this.payMethAnnual.add(new AnnualPass(a));
		} catch (OrderRejectedException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	/**
	 * Purchases a cylce pass
	 * 
	 * @param c  Cycle for which the pass is solicied
	 * @param a  Area to buy the cycle pass for
	 * @param cc Credit card to pay with
	 * @return True i pass was purchased succesfuly
	 */
	public boolean purchaseCyclePass(Cycle c, NonComposite a, CreditCard cc) {

		try {
			CyclePass cp = new CyclePass(a, c);
			cc.pay(cp.getPrice(), "Purchasing cycle pass", true);
			this.payMethCycle.add(cp);
		} catch (OrderRejectedException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	/**
	 * Removes a performance from the waiting list
	 * 
	 * @param p Performance to remove
	 */
	public void removeFromWaitingList(Performance p) {
		waiting.remove(p);
	}

	/**
	 * Enters a customer to a performance's waiting list if possible
	 * 
	 * @param p Performance to consider
	 * @return True in success
	 */
	public boolean enterWaitingList(Performance p) {
		if (p.enterWaitingList(this)) {
			waiting.add(p);
			return true;
		}
		return false;
	}

	/**
	 * @return List of performances a user is waiting for
	 */
	public List<Performance> getWaitingPerformances() {
		return waiting;
	}

	/**
	 * Getter for annual passes
	 * 
	 * @return All annual passes belonging to customer
	 */
	public Set<AnnualPass> getAnnualPasses() {
		return this.payMethAnnual;
	}

	/**
	 * Getter for cycle passes
	 * 
	 * @return All cycle passes belonging to customer
	 */
	public Set<CyclePass> getCyclePasses() {
		return this.payMethCycle;
	}

	/**
	 * Calculates which annual passes have not been used with certain area and
	 * performance
	 * 
	 * @param p Performance to consider
	 * @param a Area to consider
	 * @return The set of annual passes
	 */
	public Set<AnnualPass> getAnnualPasses(Performance p, NonComposite a) {
		Set<AnnualPass> ret = new HashSet<>();
		for (AnnualPass ap : payMethAnnual) {
			if (ap.getArea().equals(a) && ap.isValidToUse(p)) {
				ret.add(ap);
			}
		}
		return ret;
	}

	/**
	 * Calculates which cycle passes have not been used with certain area and
	 * performance
	 * 
	 * @param p Performance to consider
	 * @param a Area to consider
	 * @return The set of cycle passes
	 */
	public Set<CyclePass> getCyclePasses(Performance p, NonComposite a) {
		Set<CyclePass> ret = new HashSet<>();
		for (CyclePass cp : payMethCycle) {
			if (cp.getArea().equals(a) && cp.isValidToUse(p)) {
				ret.add(cp);
			}
		}
		return ret;
	}
}
