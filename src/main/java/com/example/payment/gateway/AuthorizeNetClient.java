package com.example.payment.gateway;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.*;
import net.authorize.api.controller.base.ApiOperationBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AuthorizeNetClient {

    @Value("${API_LOGIN_ID:dummy}")
    private String apiLoginId;

    @Value("${TRANSACTION_KEY:dummy}")
    private String transactionKey;

    private void initAuth() {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
    }

    public CreateTransactionResponse authorize(String cardNumber, String expDate, String cvv, long amountCents) {
        initAuth();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(cardNumber);
        creditCard.setExpirationDate(expDate);
        creditCard.setCardCode(cvv);

        PaymentType paymentType = new PaymentType();
        paymentType.setCreditCard(creditCard);

        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        txnRequest.setAmount(BigDecimal.valueOf(amountCents).movePointLeft(2));
        txnRequest.setPayment(paymentType);

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(request);
        controller.execute();
        return controller.getApiResponse();
    }

    public CreateTransactionResponse capture(String transactionId, long amountCents) {
        initAuth();
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.PRIOR_AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setRefTransId(transactionId);
        txnRequest.setAmount(BigDecimal.valueOf(amountCents).movePointLeft(2));

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(request);
        controller.execute();
        return controller.getApiResponse();
    }

    public CreateTransactionResponse purchase(String cardNumber, String expDate, String cvv, long amountCents) {
        initAuth();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(cardNumber);
        creditCard.setExpirationDate(expDate);
        creditCard.setCardCode(cvv);

        PaymentType paymentType = new PaymentType();
        paymentType.setCreditCard(creditCard);

        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setAmount(BigDecimal.valueOf(amountCents).movePointLeft(2));
        txnRequest.setPayment(paymentType);

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(request);
        controller.execute();
        return controller.getApiResponse();
    }

    public CreateTransactionResponse voidTransaction(String transactionId) {
        initAuth();
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.VOID_TRANSACTION.value());
        txnRequest.setRefTransId(transactionId);

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(request);
        controller.execute();
        return controller.getApiResponse();
    }

    public CreateTransactionResponse refund(String transactionId, String last4, long amountCents) {
        initAuth();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(last4);
        creditCard.setExpirationDate("XXXX");

        PaymentType paymentType = new PaymentType();
        paymentType.setCreditCard(creditCard);

        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
        txnRequest.setRefTransId(transactionId);
        txnRequest.setAmount(BigDecimal.valueOf(amountCents).movePointLeft(2));
        txnRequest.setPayment(paymentType);

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(txnRequest);
        CreateTransactionController controller = new CreateTransactionController(request);
        controller.execute();
        return controller.getApiResponse();
    }
}

