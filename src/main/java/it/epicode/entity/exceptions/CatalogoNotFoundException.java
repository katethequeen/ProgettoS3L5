package it.epicode.entity.exceptions;

public class CatalogoNotFoundException extends RuntimeException {
    public CatalogoNotFoundException(String message) {
        super(message);
    }
}
