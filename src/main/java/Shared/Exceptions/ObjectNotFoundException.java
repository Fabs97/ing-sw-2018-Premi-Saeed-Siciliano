package Shared.Exceptions;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(){
        super();
    }
    public ObjectNotFoundException(String message){
        super(message);
    }
}
