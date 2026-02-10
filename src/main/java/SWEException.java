public class SWEException extends Exception {
    public SWEException(String message) {
        super(message);
    }
    //having that class allows for exceptions not belonging to any known exception classes bult in in javas to be caught by SWEexception although there is nothing in this class
}