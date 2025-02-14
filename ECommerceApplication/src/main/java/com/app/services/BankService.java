package com.app.services;

import com.app.entites.Bank; 
import com.app.payloads.BankDTO; 
import java.util.List;

public interface BankService {
    BankDTO createBank(Bank bank);

    BankDTO updateBank(Bank bank, Long bankId);

    String deleteBank(Long bankId);

    List<BankDTO> getAllBanks();
}