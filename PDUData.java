import java.time.format.DateTimeFormatter;

public class PDUData extends BaseData implements IPDUData {

    private String DecodedSMS;

    public PDUData(String decodedSMS){
        this.DecodedSMS = decodedSMS;        
    }

    @Override
    public boolean SupportDecoding(){
        return true;
    }
    @Override
    public IPDUData Decode(){
        SMSData result = new SMSData("asdasd","asdasdasd","asdasdasd");
        return result;
    }
    
    public void ShowInfo(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");
        String formattedDateTime = this.CreatedOn.format(formatter); 
        
        System.out.println("SMS: " + this.Id + "Created: " + formattedDateTime);
    }
}