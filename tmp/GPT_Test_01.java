
public class GPT_Test_01{
    public static void main(String[] args) {
        String phoneNumber = "1234567890";
        String message = "Hello, World!";
        String pdu = encodeSmsPdu(phoneNumber, message);
        System.out.println("Encoded PDU: " + pdu);
    }
    public static String encodeSmsPdu(String phoneNumber, String message) {
            String encodedMessage = encodeMessage(message);
            int messageLength = encodedMessage.length() / 2;
            String header = createPduHeader(phoneNumber, messageLength);
            String encodedHeader = hexEncode(header);
            String encodedText = hexEncode(encodedMessage);
            String pdu = encodedHeader + encodedText;
            pdu = compressPdu(pdu);
            return pdu;
    }
    
    private static String encodeMessage(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        int shift = 0;
        int previousSeptet = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int septet = Character.toUpperCase(c) & 0x7F;
            if (shift == 0) {
                encodedMessage.append(String.format("%02X", septet));
            } else {
                encodedMessage.append(String.format("%02X", ((previousSeptet << (7 - shift)) | (septet >> shift))));
                encodedMessage.append(String.format("%02X", (septet << (8 - shift))));
            }
            shift++;
            if (shift == 7) {
                encodedMessage.append(String.format("%02X", septet >> 1));
                shift = 0;
            }
            previousSeptet = septet;
        }
        if (shift != 0) {
            encodedMessage.append(String.format("%02X", previousSeptet << (7 - shift)));
        }
        return encodedMessage.toString();
    }

    private static String createPduHeader(String phoneNumber, int messageLength) {
        StringBuilder header = new StringBuilder();

        // Address type and length
        header.append("91"); // International format (Type-of-Address)
        int addressLength = phoneNumber.length();
        if (addressLength % 2 != 0) {
            phoneNumber += "F"; // Pad with 'F' if the phone number length is odd
            addressLength++;
        }
        header.append(String.format("%02X", addressLength / 2));

        // Recipient address
        header.append(phoneNumber);

        // Protocol Identifier
        header.append("00"); // Normal SMS

        // Data Coding Scheme
        header.append("00"); // 7-bit GSM default alphabet

        // Message Reference
        header.append("00"); // Set to 0 for automatic assignment

        // Message Length
        header.append(String.format("%02X", messageLength));

        return header.toString();
    }

    private static String hexEncode(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            String hex = input.substring(i, i + 2);
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String compressPdu(String pdu) {
        int index = pdu.lastIndexOf("00");
        if (index != -1) {
            pdu = pdu.substring(0, index);
        }
        return pdu;
    }

    
}
        
    
 
