package co.com.nequi.api.exception;

import co.com.nequi.model.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

@Component
@Order(-2)
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    private static final int HTTP_STATUS_500 = 500;
    private static final String COMPONENT = "entry-point";

    public ExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
                            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::buildErrorResponse);
    }

    public Mono<ServerResponse> buildErrorResponse(final ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, this::errorBuilder)
                .onErrorResume(Exception.class,this::errorBuilder)
                .doOnNext(error -> loggerError(request, error))
                .cast(Tuple2.class)
                .flatMap(tuple -> this.buildResponse((ErrorDTO)tuple.getT1(), (HttpStatus)tuple.getT2()));

    }

    private Mono<Tuple2<ErrorDTO, HttpStatus>> errorBuilder(Throwable exception){
        return buildErrorResponse(exception.getMessage(), 500)
                .map(configurationErrorData ->
                        Tuples.of(configurationErrorData,HttpStatus.valueOf(HTTP_STATUS_500)));
    }

    private Mono<Tuple2<ErrorDTO, HttpStatus>> errorBuilder(BusinessException businessException){
        return buildErrorResponse(businessException.getReason(), businessException.getCode()).zipWith(Mono.just(
                HttpStatus.valueOf(businessException.getCode())));
    }

    private Mono<ErrorDTO> buildErrorResponse(String reason, int code) {
        return Mono.just(ErrorDTO.builder()
                .code(code)
                .reason(reason)
                .build());
    }

    private Mono<ServerResponse> buildResponse(ErrorDTO errorDTO, HttpStatus httpStatus){
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorDTO);
    }

    private void loggerError(ServerRequest request, Object error) {
        LOGGER.error("Error occurred in component {}: Request Path: {}, Method: {}, Error: {}",
                COMPONENT,
                request.path(),
                request.methodName(),
                error);
    }
}
