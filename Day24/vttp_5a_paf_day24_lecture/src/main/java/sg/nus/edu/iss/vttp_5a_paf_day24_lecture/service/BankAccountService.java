package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.BankAccount;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.AccountInactiveException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.AccountNotFoundException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.InsufficientBalanceException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo.BankAccountRepo;

@Service
public class BankAccountService {
    
    @Autowired
    private BankAccountRepo bankAccountRepo;

    public boolean accountExists(int accountId){
        return bankAccountRepo.accountExists(accountId);
    }

    public BankAccount getBankAccount(int accountId){
        if(accountExists(accountId)){
            BankAccount bankAccount  = bankAccountRepo.getAccountById(accountId);
            if(!bankAccount.isActive()){
                throw new AccountInactiveException("Account is no longer active.");
            }
            return bankAccount;
        } else {
            throw new AccountNotFoundException("Account is not found in our system");
        }
    }

    @Transactional
    public boolean transferAmount(int accountIdFrom, int accountIdTo, float amount){
        BankAccount bankAccountFrom = bankAccountRepo.getAccountById(accountIdFrom);
        BankAccount bankAccountTo = bankAccountRepo.getAccountById(accountIdTo);

        float balanceForFromAccount = bankAccountFrom.getBalance() - amount;
        float balanceForToAccount = bankAccountTo.getBalance() + amount;

        if(balanceForFromAccount < 0){
            throw new InsufficientBalanceException("Account has insufficient funds.");
        } else {
            return bankAccountRepo.updateAccountBalance(accountIdFrom, balanceForFromAccount) 
            && bankAccountRepo.updateAccountBalance(accountIdTo, balanceForToAccount);
        }

    }
}
