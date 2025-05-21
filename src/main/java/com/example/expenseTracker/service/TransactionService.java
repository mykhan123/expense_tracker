package com.example.expenseTracker.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expenseTracker.entity.Transaction;
import com.example.expenseTracker.entity.TransactionType;
import com.example.expenseTracker.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
    private TransactionRepository repository;
	
	public Transaction addTransaction(Transaction transaction) {
		Transaction saved = repository.save(transaction);
		System.out.println("************SAVED TRANSACTION*************:"+saved);
        return saved;
    }
	
	public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }
	
	public List<Transaction> getMonthlySummary(int month) {
	        LocalDate start = LocalDate.of(LocalDate.now().getYear(), month, 1);
	        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
	        return repository.findByDateBetween(start, end);
	}
	 
	 
	  public void loadFromFile(File file) throws IOException {
		  System.out.println("Service....................................");
		  try (BufferedReader reader = new BufferedReader(new FileReader(file))){
		      String line;
		      while ((line =reader.readLine()) != null) {
		    	 String[] parts = line.split(",");
		    	 if(parts.length<4) {
		    		 throw new IllegalArgumentException("Invalid csv column Expected 4 columns.");
		    	 }
		    	 
		    	 Transaction transaction = Transaction.builder()
		    	 .type(Enum.valueOf(TransactionType.class, parts[0]))
                 .date(LocalDate.parse(parts[1]))
                 .category(parts[2])
                 .amount(Double.parseDouble(parts[3]))
                 .build();
		    	 repository.save(transaction);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		  
	  }
}
