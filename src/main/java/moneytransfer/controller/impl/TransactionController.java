package moneytransfer.controller.impl;

import moneytransfer.controller.api.TransactionControllerAPI;
import moneytransfer.dto.TransactionDTO;
import moneytransfer.repository.entity.TransactionEntity;
import moneytransfer.service.TransactionService;
import moneytransfer.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionControllerAPI {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionMapper.toTransactionDTO(transactionService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        List<TransactionEntity> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactionMapper.toTransactionDTOList(transactions));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

