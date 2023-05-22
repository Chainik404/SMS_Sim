import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.time.format.DateTimeFormatter;

public class PDUData extends BaseData implements IPDUData, IDataSerializer {

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
    public String GetAddress(){
        return "";
    }
    public void ShowInfo(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");
        String formattedDateTime = this.CreatedOn.format(formatter); 
        
        System.out.println("SMS: " + this.Id + "Created: " + formattedDateTime);
    }

    public void Save(DataOutputStream dos){        
        try {
            dos.writeUTF(this.DecodedSMS);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            this.DecodedSMS = dis.readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}