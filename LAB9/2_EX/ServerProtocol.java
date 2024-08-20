public class ServerProtocol {

    public DataReply processRequest(DataRequest req) {
        DataReply reply = new DataReply();
        String result = operation(req.getOperation(), req.getMessage(), req.getKey());
        reply.setMessage(result);
        return reply;
    }

    private String decode(String message, int key) {
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

    private String encode(String message, int key) {
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

    private String operation(String operation, String message, int key) {
        switch (operation.toUpperCase()) {
            case "LOWERCASE":
                return message.toLowerCase();
            case "UPPERCASE":
                return message.toUpperCase();
            case "ENCODE":
                return encode(message, key);
            case "DECODE":
                return decode(message, key);
            default:
                return "Unknown operation";
        }
    }
}
