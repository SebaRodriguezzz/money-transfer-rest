package moneytransfer.web.mapper;

import moneytransfer.web.dto.TransactionDTO;
import moneytransfer.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toTransactionDTO(TransactionEntity transactionEntity);
    List<TransactionDTO> toTransactionDTOList(List<TransactionEntity> transactionEntities);
}