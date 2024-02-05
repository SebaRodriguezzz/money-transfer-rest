package io.datajek.moneytransferrest.service.mapper;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toTransactionDTO(TransactionEntity transactionEntity);
    List<TransactionDTO> toTransactionDTOList(List<TransactionEntity> transactionEntities);
}