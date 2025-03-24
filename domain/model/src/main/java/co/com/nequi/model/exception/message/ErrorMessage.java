package co.com.nequi.model.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    FRANCHISE_EXISTS(400, "La Franquicia ya existe"),
    BRANCH_OFFICE_EXISTS(400, "La Sucursal ya existe"),
    FRANCHISE_NOT_EXISTS(404, "La Franquicia no existe"),
    BRANCH_OFFICE_NOT_EXISTS(404, "La Sucursal no existe"),
    PRODUCT_EXISTS(400, "El Producto existe"),
    FIELD_IS_REQUIRED(400, "Campo es requerido"),
    BAD_REQUEST_ID(400, "Id is requerido"),
    UNEXPECTED_EXCEPTION(500, "Unexpected error");

    private final Integer code;
    private final String reason;
}
