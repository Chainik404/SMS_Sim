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
        var temp = this.DecodedSMS;
        var senderAddress = new PDUAddress(temp);
        temp = temp.substring(senderAddress.Value().length());
        var destinAddress = new PDUAddress(temp);
        temp = temp.substring(destinAddress.Value().length());
        var message = new PDUMessage(temp);
        SMSData result = new SMSData(senderAddress.Value(), destinAddress.Value(), message.Value());
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