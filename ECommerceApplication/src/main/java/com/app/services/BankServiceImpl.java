package com.app.services;

import java.util.List; 
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.app.entites.Bank; 
import com.app.exceptions.APIException; 
import com.app.exceptions.ResourceNotFoundException; 
import com.app.payloads.BankDTO; 
import com.app.repositories.BankRepo;

import jakarta.transaction.Transactional;

@Transactional 
@Service public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BankDTO createBank(Bank bank) {
        // Check if a bank with the same name already exists
        Bank savedBank = bankRepo.findByNamaBank(bank.getNamaBank());
        if (savedBank != null) {
            throw new APIException("Bank with the name '" + bank.getNamaBank() + "' already exists.");
        }
        savedBank = bankRepo.save(bank);
        return modelMapper.map(savedBank, BankDTO.class);
    }

    @Override
    public BankDTO updateBank(Bank bank, Long bankId) {
        Bank savedBank = bankRepo.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank", "bankId", bankId));
        bank.setBankId(bankId);
        savedBank = bankRepo.save(bank);
        return modelMapper.map(savedBank, BankDTO.class);
    }

    @Override
    public String deleteBank(Long bankId) {
        Bank bank = bankRepo.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank", "bankId", bankId));
        bankRepo.delete(bank);
        return "Bank with id: " + bankId + " deleted successfully!";
    }

    @Override
    public List<BankDTO> getAllBanks() {
        List<Bank> banks = bankRepo.findAll();
        return banks.stream().map(bank -> modelMapper.map(bank, BankDTO.class))
                .collect(Collectors.toList());
    }
}