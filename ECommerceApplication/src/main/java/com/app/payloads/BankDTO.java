package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class BankDTO { 

    private Long bankId;
    private String namaBank;
    private String nomorRekening; 
}