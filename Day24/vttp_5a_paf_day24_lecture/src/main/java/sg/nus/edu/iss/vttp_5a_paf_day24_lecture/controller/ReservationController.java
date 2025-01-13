package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Book;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Reservation;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.ReservationDetail;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createReservation(){
        Reservation reservation = new Reservation();
        reservation.setFullName("Dinner for 2");
        reservation.setReservationDate(Date.valueOf("2025-03-03"));

        Book book = new Book();
        book.setTitle("Avatar");
        book.setQuantity(5);

        ReservationDetail reservationDetail = new ReservationDetail();
        reservationDetail.setBook(book);
        reservationDetail.setReservation(reservation);

        return new ResponseEntity<>(reservationService.createReservationRecord(reservationDetail),
        HttpStatusCode.valueOf(201));
    }
}
