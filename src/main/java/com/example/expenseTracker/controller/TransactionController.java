package com.example.expenseTracker.controller;

import java.io.File;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.expenseTracker.entity.Transaction;
import com.example.expenseTracker.service.TransactionService;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<Transaction>   addtransaction(@RequestBody Transaction transaction) {
		Transaction saveTransaction = transactionService.addTransaction(transaction);
		return ResponseEntity.ok(saveTransaction);
	}
	
	@GetMapping
	public ResponseEntity<List<Transaction>> getAll(){
		return  ResponseEntity.ok(transactionService.getAllTransactions());
	}
	
	@GetMapping("/summary")
	public ResponseEntity<List<Transaction>> getMonthlySummary(@RequestParam int month) {
		return ResponseEntity.ok(transactionService.getMonthlySummary(month));
	}
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws Exception {
		System.out.println("File upload Starting..............");
		File tempFile = File.createTempFile("upload", ".csv");
		file.transferTo(tempFile);
		transactionService.loadFromFile(tempFile);
		return ResponseEntity.ok("File uploaded and transactions saved to DB.");
		
	}
}
