package io.datajek.moneytransferrest.service.mapper;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toTransferDTO(TransactionEntity transactionEntity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transactionEntity.getId());
        transactionDTO.setDate(transactionEntity.getDate());
        transactionDTO.setSenderAccountNumber(transactionEntity.getSender().getAccountNumber());
        transactionDTO.setReceiverAccountNumber(transactionEntity.getReceiver().getAccountNumber());
        transactionDTO.setAmount(transactionEntity.getAmount());
        return transactionDTO;
    }

}