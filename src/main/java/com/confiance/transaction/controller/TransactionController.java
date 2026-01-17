package com.confiance.transaction.controller;

import com.confiance.common.dto.ApiResponse;
import com.confiance.transaction.entity.Transaction;
import com.confiance.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository repository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<Transaction>>> getUserTransactions(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        Page<Transaction> transactions = repository.findByUserId(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        Transaction saved = repository.save(transaction);
        return ResponseEntity.ok(ApiResponse.success("Transaction created", saved));
    }
}