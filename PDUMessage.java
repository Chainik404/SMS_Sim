public class PDUMessage {

    private String Text;

    public PDUMessage(String message){

        this.Text = message;
        
    }
    public String Encode(){
        String result = this.Text;

        return result;

    }

    public String Decode(){
        String result = this.Text;

        return result;
    }
}
