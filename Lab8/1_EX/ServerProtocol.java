public class ServerProtocol {

    public String processRequest(String theInput) {
        String[] parts = theInput.split("-");
        if (parts.length != 3) {
            return "Invalid input format";
        }
        
        String operation = parts[0].trim();
        String message = parts[1].trim();
        int key;
        
        try {
            key = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            return "Invalid key format";
        }

        DataShared data = new DataShared(operation, message, key);
        return data.operation();
    }
}
