package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount);
}
