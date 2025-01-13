package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.service.BankAccountService;

@RestController
@RequestMapping("/api")
public class BankAccountRestController {

    @Autowired
    private BankAccountService bankAccountService;
    
    @GetMapping("/bankaccount/{accountId}")
    public ResponseEntity<Boolean> accountExists(@PathVariable String accountId){
        boolean isAccountExists = bankAccountService.accountExists(Integer.parseInt(accountId));
        return ResponseEntity.ok().body(isAccountExists);
    }

    @PostMapping("/transfer/from/{account-from}/to/{account-to}/{transfer-amount}")
    public ResponseEntity<Boolean> transferFund(@PathVariable("account-from") String accountFromId, @PathVariable("account-to") String accountToId,
    @PathVariable("transfer-amount") String amount){
        boolean isTransferred = bankAccountService.transferAmount(Integer.parseInt(accountFromId), 
        Integer.parseInt(accountToId), Float.parseFloat(amount));
        return new ResponseEntity<>(isTransferred, HttpStatusCode.valueOf(201));
    }
}
