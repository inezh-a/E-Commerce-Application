package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.entites.Bank;
import com.app.payloads.BankDTO;
import com.app.services.BankService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;

@RestController 
@RequestMapping("/api") 
@SecurityRequirement(name = "E-Commerce Application") 
public class BankController {
    @Autowired
    private BankService bankService;

    // ADMIN endpoint to create a new bank record
    @PostMapping("/admin/bank")
    public ResponseEntity<BankDTO> createBank(@Valid @RequestBody Bank bank) {
        BankDTO savedBank = bankService.createBank(bank);
        return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
    }

    // ADMIN endpoint to update an existing bank record
    @PutMapping("/admin/bank/{bankId}")
    public ResponseEntity<BankDTO> updateBank(@Valid @RequestBody Bank bank, @PathVariable Long bankId) {
        BankDTO updatedBank = bankService.updateBank(bank, bankId);
        return new ResponseEntity<>(updatedBank, HttpStatus.OK);
    }

    // ADMIN endpoint to delete a bank record
    @DeleteMapping("/admin/bank/{bankId}")
    public ResponseEntity<String> deleteBank(@PathVariable Long bankId) {
        String response = bankService.deleteBank(bankId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // PUBLIC endpoint to get a list of supported banks (useful for payment selection)
    @GetMapping("/public/banks")
    public ResponseEntity<List<BankDTO>> getBanks() {
        List<BankDTO> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }
}