package model.operations;

import model.acts.performances.*;
import es.uam.eps.padsof.telecard.*;
import model.exceptions.CreationException;
import model.users.*;
import model.areas.positions.*;
import model.operations.payments.*;
import java.util.List;
import java.util.Set;

/**
 * Class that represents a purchase
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 */
public class Purchase extends Operation {
   /** Default UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructor
    * 
    * @param customer  user
    * @param perf      performance
    * @param positions positions requested
    * @param pms       payment methods to use
    * @throws CreationException      If any invalid parameter
    * @throws OrderRejectedException If purchase couldnt be made
    */
   public Purchase(Customer customer, Performance perf, List<Position> positions, Set<PaymentMethod> pms)
         throws CreationException, OrderRejectedException {
      super(customer, perf, positions);
      this.setPayments(pms);
      this.generateTickets();
   }
}
