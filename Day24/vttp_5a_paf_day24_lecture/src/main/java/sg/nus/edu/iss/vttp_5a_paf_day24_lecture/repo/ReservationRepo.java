package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Reservation;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.ReservationDetail;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.util.Queries;

@Repository
public class ReservationRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int createReservation(Reservation reservation){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement ps = con.prepareStatement(Queries.QUERY_TO_INSERT_RESERVATION, new String[]{"id"});
            ps.setString(1, reservation.getFullName());
            ps.setDate(2, reservation.getReservationDate());
            return ps;
        };

        jdbcTemplate.update(psc, keyHolder);
        int reservationId = keyHolder.getKey().intValue();
        return reservationId;
    }

    public boolean createReservationDetails(ReservationDetail reservationDetail){
        int iUpdated = jdbcTemplate.update(Queries.QUERY_TO_INSERT_RESERVATION_DETAIL, reservationDetail.getBook().getId(),
        reservationDetail.getReservation().getId());
        return iUpdated > 0;
    }

}
