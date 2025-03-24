package co.com.nequi.model.exception;

import co.com.nequi.model.exception.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends  RuntimeException {

    private final String reason;
    private final int code;


    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getReason());
        this.reason = errorMessage.getReason();
        this.code = errorMessage.getCode();
    }
}
