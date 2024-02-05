package io.datajek.moneytransferrest.web.mapper;

import io.datajek.moneytransferrest.web.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toTransactionDTO(TransactionEntity transactionEntity);
    List<TransactionDTO> toTransactionDTOList(List<TransactionEntity> transactionEntities);
}