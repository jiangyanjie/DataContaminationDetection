package ru.arsenalpay.api.facade.impl;

import ru.arsenalpay.api.client.ApiClient;
import ru.arsenalpay.api.client.ApiResponse;
import ru.arsenalpay.api.client.impl.ApacheApiClientImpl;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.command.impl.InitPayMkProducer;
import ru.arsenalpay.api.command.impl.InitPayMkStatusProducer;
import ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.facade.ApiCommandsFacade;
import ru.arsenalpay.api.merchant.MerchantCredentials;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
import ru.arsenalpay.api.response.PaymentResponse;
import ru.arsenalpay.api.response.PaymentStatusResponse;
import ru.arsenalpay.api.util.Configuration;
import ru.arsenalpay.api.util.LoggerManager;
import ru.arsenalpay.api.util.MultiThreadedHttpClient;

import java.io.IOException;

/**
 * <p>The main and now a single implementation of ApiCommandsFacade.</p>
 *
 * <p>By default (default constructor) for communications with ArsenalPay Api Server use ApacheApiClientImpl
 * configured for concurrency environment. But if you want you can use your own implementation of ApiClient interface.
 * In special some cases it may be relevant for example if you want to create http connections using one tool throughout
 * the application or you for some reason are not satisfied with the default implementation.
 * We do so, because we understand that it is your application and you master it.
 * We appreciate this freedom and do not impose anything.</p>
 *
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 * @see ru.arsenalpay.api.client.ApiClient
 * @see ru.arsenalpay.api.client.impl.ApacheApiClientImpl
 *
 * @author adamether
 */
public class ApiCommandsFacadeImpl implements ApiCommandsFacade {

    /**
     * ApiClient concrete implementation
     */
    private final ApiClient apiClient;

    /**
     * Private secret merchant credentials
     */
    private final MerchantCredentials credentials;

    /**
     * This is default constructor with ApacheApiClientImpl as ApiClient implementation.
     * HttpClient is configured for work in concurrency environment.
     */
    public ApiCommandsFacadeImpl() {
        this.apiClient = new ApacheApiClientImpl(
                MultiThreadedHttpClient.getInstance().getHttpClient()
        );
        this.credentials = new MerchantCredentials(
                Configuration.getProp("merchant.id"),
                Configuration.getProp("merchant.secret")
        );
        LoggerManager.init();
    }

    /**
     * This constructor is for your freedom.
     * Simply implement ApiClient interface with your favorite http client with your configuration and
     * ApiCommandsFacade will use it for communication with ArsenalPay Server API.
     * @param apiClient -- implementation of ApiClient interface
     */
    public ApiCommandsFacadeImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.credentials = new MerchantCredentials(
                Configuration.getProp("merchant.id"),
                Configuration.getProp("merchant.secret")
        );
        LoggerManager.init();
    }

    /**
     * HttpClient is configured for work in concurrency environment.
     * MerchantCredentials are id and secret given to you at registration.
     * Take a note: merchantCredentials witch were set in conf/sdk.properties will be ignored!
     */
    public ApiCommandsFacadeImpl(MerchantCredentials merchantCredentials) {
        this.apiClient = new ApacheApiClientImpl(
                MultiThreadedHttpClient.getInstance().getHttpClient()
        );
        this.credentials = merchantCredentials;
        LoggerManager.init();
    }

    /**
     * This constructor is for your absolute freedom.
     * This is constructor with ApacheApiClientImpl as ApiClient implementation and merchantCredentials.
     * HttpClient is configured for work in concurrency environment.
     * MerchantCredentials are id and secret given to you at registration.
     * Take a note: merchantCredentials witch were set in conf/sdk.properties will be ignored!
     */
    public ApiCommandsFacadeImpl(ApiClient apiClient, MerchantCredentials merchantCredentials) {
        this.apiClient = apiClient;
        this.credentials = merchantCredentials;
        LoggerManager.init();
    }

    @Override
    public PaymentResponse requestPayment(PaymentRequest request) throws ArsenalPayApiException {
        ApiCommandProducer producer = new InitPayMkProducer(request, credentials);
        ApiCommand command = producer.getCommand();
        try {
            final ApiResponse apiResponse = apiClient.executeCommand(command);
            final String xml = apiResponse.getBody();
            return PaymentResponse.fromXml(xml);
        } catch (IOException e) {
            throw new InternalApiException(e);
        }
    }

    @Override
    public PaymentStatusResponse checkPaymentStatus(PaymentStatusRequest request) throws InternalApiException {
        ApiCommandProducer producer = new InitPayMkStatusProducer(request, credentials);
        ApiCommand command = producer.getCommand();
        try {
            final ApiResponse apiResponse = apiClient.executeCommand(command);
            final String xml = apiResponse.getBody();
            return PaymentStatusResponse.fromXml(xml);
        } catch (IOException e) {
            throw new InternalApiException(e);
        }
    }

}
