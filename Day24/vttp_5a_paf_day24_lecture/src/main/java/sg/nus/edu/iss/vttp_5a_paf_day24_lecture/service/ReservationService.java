package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Book;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Reservation;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.ReservationDetail;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo.BookRepo;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo.ReservationRepo;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private BookRepo bookRepo;

    @Transactional
    public boolean createReservationRecord(ReservationDetail reservationDetail){
        int iReservationId = reservationRepo.createReservation(reservationDetail.getReservation());
        int iBookId = bookRepo.insertBook(reservationDetail.getBook());

        reservationDetail.getReservation().setId(iReservationId);
        reservationDetail.getBook().setId(iBookId);


        reservationRepo.createReservationDetails(reservationDetail);
        /*
         * uncomment to simulate error
         * throw new IllegalArgumentException("Simulate error after creating reservation...")
         */
        return true;
    }
}
