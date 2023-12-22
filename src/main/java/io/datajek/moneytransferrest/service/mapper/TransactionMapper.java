package io.datajek.moneytransferrest.service.mapper;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionDTO toTransactionDTO(TransactionEntity transactionEntity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transactionEntity.getId());
        transactionDTO.setDate(transactionEntity.getDate());
        transactionDTO.setSenderAccountNumber(transactionEntity.getSender().getAccountNumber());
        transactionDTO.setReceiverAccountNumber(transactionEntity.getReceiver().getAccountNumber());
        transactionDTO.setAmount(transactionEntity.getAmount());
        return transactionDTO;
    }

    public List<TransactionDTO> toTransactionDTOList(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .map(this::toTransactionDTO)
                .collect(Collectors.toList());
    }



}