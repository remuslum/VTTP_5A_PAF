package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static vttp2023.batch3.assessment.paf.bookings.util.AccOccupancySQLQueries.COLUMN_VACANCY;
import static vttp2023.batch3.assessment.paf.bookings.util.AccOccupancySQLQueries.GET_VACANCY;
import static vttp2023.batch3.assessment.paf.bookings.util.AccOccupancySQLQueries.UPDATE_VACANCY;
import static vttp2023.batch3.assessment.paf.bookings.util.ReservationSQLQueries.INSERT_RESERVATION;

@Repository
public class ReservationRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean makeReservation(String reservationId, String accountId, String name, String email, LocalDate arrivalDate, int duration){
        int added = jdbcTemplate.update(INSERT_RESERVATION,reservationId,name,email,accountId,arrivalDate.toString(),duration);
        return added > 0;
    }

    public boolean updateVacancy(String accountId, int duration, int vacancy){
        int added = jdbcTemplate.update(UPDATE_VACANCY,vacancy - duration,accountId);
        return added > 0;
    }

    public int getVacancy(String accountId){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(GET_VACANCY,accountId);
        while(rowSet.next()){
            return rowSet.getInt(COLUMN_VACANCY);
        }
        return 0;
    }
}
