package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.BankAccount;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.AccountNotFoundException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.util.Queries;

@Repository
public class BankAccountRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean accountExists(int accountId){
        try {
            // Auto creates a bankaccount object
            BankAccount bankAccount = jdbcTemplate.queryForObject(Queries.QUERY_TO_SELECT_BANKACCOUNT_BY_ID
            , BeanPropertyRowMapper.newInstance(BankAccount.class), accountId);
            return true;
        } catch (DataAccessException e){
            throw new AccountNotFoundException("The account you are looking for does not exist in the system.");
        }
    }

    public BankAccount getAccountById(int accountId){
        BankAccount bankAccount = jdbcTemplate.queryForObject(Queries.QUERY_TO_SELECT_BANKACCOUNT_BY_ID
        , BeanPropertyRowMapper.newInstance(BankAccount.class), accountId);
        return bankAccount;
    }

    public boolean updateAccountBalance(int accountId, float amount){
        return jdbcTemplate.update(Queries.QUERY_TO_UPDATE_BANK_ACCOUNT, amount, accountId) > 0;
    }
}
