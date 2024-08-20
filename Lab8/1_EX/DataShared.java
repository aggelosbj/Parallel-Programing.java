public class DataShared {
    private String operation;
    private String message;
    private int key;

    public DataShared(String operation, String message, int key) {
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

    public String ceaserCipherEncode() {
        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character >= 'a' && character <= 'z') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + key) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else if (character >= 'A' && character <= 'Z') {
                int originalAlphabetPosition = character - 'A';
                int newAlphabetPosition = (originalAlphabetPosition + key) % 26;
                char newCharacter = (char) ('A' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String ceaserCipherDecode() {
        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character >= 'a' && character <= 'z') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition - key + 26) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else if (character >= 'A' && character <= 'Z') {
                int originalAlphabetPosition = character - 'A';
                int newAlphabetPosition = (originalAlphabetPosition - key + 26) % 26;
                char newCharacter = (char) ('A' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String operation() {
        switch (operation.toUpperCase()) {
            case "LOWERCASE":
                return message.toLowerCase();
            case "UPPERCASE":
                return message.toUpperCase();
            case "ENCODE":
                return ceaserCipherEncode();
            case "DECODE":
                return ceaserCipherDecode();
            default:
                return "Unknown operation";
        }
    }
}
