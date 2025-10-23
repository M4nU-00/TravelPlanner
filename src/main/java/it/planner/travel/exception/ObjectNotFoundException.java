package it.planner.travel.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import it.planner.travel.exception.base.BaseException;

/**
 * Eccezione generica per oggetti non trovati nel sistema.
 * Può essere utilizzata per Book, Author, Genre o qualsiasi altra entità.
 */
public class ObjectNotFoundException extends BaseException {

    private static final Integer HTTP_STATUS_CODE = HttpStatus.NOT_FOUND.value(); // 404 corretto

    // Costruttore generico con ID
    public ObjectNotFoundException(String objectType, UUID uuid) {
        super(
                objectType + " not found",
                404, // code come Integer
                "No " + objectType.toLowerCase() + " found with UUID: " + uuid,
                HTTP_STATUS_CODE,
                null);
    }

    // Costruttore generico con campo personalizzato
    public ObjectNotFoundException(String objectType, String fieldName, String fieldValue) {
        super(
                objectType + " not found",
                404, // code come Integer
                "No " + objectType.toLowerCase() + " found with " + fieldName + ": " + fieldValue,
                HTTP_STATUS_CODE,
                null);
    }

    // Costruttore specifico per Author con nome e cognome
    public static ObjectNotFoundException forAuthor(String firstName, String lastName) {
        return new ObjectNotFoundException(
                "Author",
                "name",
                firstName + " " + lastName);
    }

    public static ObjectNotFoundException forAuthorLastName(String lastName) {
        return new ObjectNotFoundException(
                "Author",
                "lastName", lastName);
    }

    // Costruttore generico con messaggio personalizzato
    public ObjectNotFoundException(String customMessage) {
        super(
                "Object not found",
                404, // code come Integer
                customMessage,
                HTTP_STATUS_CODE,
                null);
    }

    // Costruttore con causa (per wrapping altre eccezioni)
    public ObjectNotFoundException(String objectType, UUID uuid, Throwable cause) {
        super(
                objectType + " not found",
                404, // code come Integer
                "No " + objectType.toLowerCase() + " found with UUID: " + uuid,
                HTTP_STATUS_CODE,
                cause);
    }
}
