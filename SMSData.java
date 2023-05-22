import java.time.format.DateTimeFormatter;

public class SMSData extends BaseData implements IPDUData {

    private PDUAddress SenderAddress;
    private PDUAddress DestinAddress;
    private PDUMessage Message;

    public SMSData(String senderAddress, String destinAddress, String message){
        
        this.SenderAddress = new PDUAddress(senderAddress);
        this.DestinAddress = new PDUAddress(destinAddress);
        this.Message = new PDUMessage(message);
        
    }
    @Override
    public boolean SupportEncoding(){
        return true;
    }
    @Override
    public IPDUData Encode(){
        PDUData result = new PDUData("asdasd");
        return result;
    }

    public String GetAddress(){
        return this.DestinAddress.Value();
    }
    
    public void ShowInfo(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");
        String formattedDateTime = this.CreatedOn.format(formatter); 
        
        System.out.println("SMS: " + this.Id + "Created: " + formattedDateTime);
    }
}
