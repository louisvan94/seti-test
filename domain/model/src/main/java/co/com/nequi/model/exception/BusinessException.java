package co.com.nequi.model.exception;

import co.com.nequi.model.exception.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends  RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getReason());
        this.errorMessage = errorMessage;
    }
}
