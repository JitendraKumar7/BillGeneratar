package angira.bhinfotech.library.gmail.exceptions;

public class NotNetworkException extends RuntimeException {


    public NotNetworkException() {
        super("NotNetworkException, you need internet connection to send the email");
    }

    public NotNetworkException(String detailMessage) {
        super(detailMessage);
    }
}
