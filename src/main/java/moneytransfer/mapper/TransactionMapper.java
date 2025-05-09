package moneytransfer.mapper;

import moneytransfer.dto.TransactionDTO;
import moneytransfer.repository.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toTransactionDTO(TransactionEntity transactionEntity);
    List<TransactionDTO> toTransactionDTOList(List<TransactionEntity> transactionEntities);
}