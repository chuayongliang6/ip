public class SWEException extends Exception {
    public SWEException(String message) {
        super(message);
    }
    //having that class allows for exceptions not belonging to any known exception classes built-in in Java to be caught by SWEException although there is nothing in this class
}