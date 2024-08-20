import java.io.Serializable;

public class DataRequest implements Serializable {
    private String operation;
    private String message;
    private int key;

    public DataRequest(String operation, String message, int key) {
        this.operation = operation.trim();
        this.message = message.trim();
        this.key = key;
    }

    public void setOperation(String operation) {
        this.operation = operation.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setMessage(String message) {
        this.message = message.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
