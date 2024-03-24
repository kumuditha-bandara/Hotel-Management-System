package Controller;

import Dao.ReservationDAO;
import Model.Reservation;
import Model.Room;

import java.util.List;

public class ReservationController {
    private ReservationDAO reservationDAO;

    public ReservationController() {
        this.reservationDAO = new ReservationDAO();
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public void addReservation(Reservation reservation) {
        reservationDAO.addReservation(reservation);
    }

    public void updateReservation(Reservation reservation) {
        reservationDAO.updateReservation(reservation);
    }

    

    public void cancelReservation(int reservationId) {
        reservationDAO.cancelReservation(reservationId);
    }
}
