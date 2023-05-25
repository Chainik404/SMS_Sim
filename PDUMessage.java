import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class PDUMessage {

    private String Text;

    public PDUMessage(String message){

        this.Text = message;
        
    }

    public String Value(){
        return this.Text;
    }

    public String Encode(){
        byte[] bytes;
        try {
            bytes = this.Text.getBytes("UTF-16BE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder pdu = new StringBuilder();
        for (byte aByte : bytes) {
            pdu.append(String.format("%02X", aByte));
        }

        return pdu.toString();
    }

    public String Decode(){
        byte[] bytes = new byte[this.Text.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(this.Text.substring(i * 2, i * 2 + 2), 16);
        }

        try {
            return new String(bytes, "UTF-16BE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
