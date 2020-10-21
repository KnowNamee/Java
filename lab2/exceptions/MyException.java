package exceptions;

public class MyException extends Exception{

    public MyException() {
        super();
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
