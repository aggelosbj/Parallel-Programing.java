import java.io.Serializable;

public class DataReply implements Serializable {
    private String message;

    public DataReply() {
        this.message = "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
