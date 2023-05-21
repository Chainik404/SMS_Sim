public class PDUMessage {

    private String Text;

    public PDUMessage(String message){

        this.Text = message;
        
    }

    public boolean SupportEncoding(){
        return true;
    }
    public boolean SupportDecoding(){
        return true;
    }

    public Object Encode(){
        String result = this.Text;

        return result;

    }

    public Object Decode(){
        String result = this.Text;

        return result;
    }
}
