import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class VBD implements IDataSerializer{
    private String Name;
    private int Frequency; // quantity SMS per one second
    private String SenderAddress;
    private String DestinAddress;
    private String Message;
    private boolean Active;
    private int SentSMS;

    private long LastSyncTimeMark;
    private Double SMSPerMillisecond;

    public VBD(String name,Integer frequency, String senderAddress, String destinAddress, String message){
        this.Name = name;
        this.Frequency = frequency;
        this.SenderAddress = senderAddress;
        this.DestinAddress = destinAddress;
        this.Message = message;
        this.SMSPerMillisecond = (Double)(frequency/1000.0);
        this.LastSyncTimeMark = 0;
        this.Active = true;
        this.SentSMS = 0;
    }

    public int GetFrequency(){
        return this.Frequency;
    }
    public String GetSenderAddress(){
        return this.SenderAddress;
    }
    public String GetDestinAddress(){
        return this.DestinAddress;
    }
    public String GetMessage(){
        return this.Message;
    }
    public boolean GetActive(){
        return this.Active;
    }

    public void SetFrequency(Integer frequency){
        this.Frequency = frequency;
        this.SMSPerMillisecond = (Double)(frequency/1000.0);
    }
    public void SetAddress(String address){
        this.DestinAddress = address;
    }
    public void SetMessage(String message){
        this.Message = message;
    }
    public void SetActive(boolean active){
        this.Active = active;
        if(this.Active){
            LastSyncTimeMark =  System.currentTimeMillis();
        }
    }

    public int GetSMSToRender(){
        int result = 0;
        if(this.Active){
            var curTime = System.currentTimeMillis();
            var diffMls = 0L;
            if(LastSyncTimeMark == 0){
                LastSyncTimeMark = curTime;
            }
            else{
                diffMls = curTime - LastSyncTimeMark;            
            }
            result = (int)(diffMls * SMSPerMillisecond);
            if(result > 0)
            {
                LastSyncTimeMark = LastSyncTimeMark + (int)(result/SMSPerMillisecond);
            }
        }
        return result;
    }

    public SMSData Create(){
        this.SentSMS++;
        return new SMSData(this.SenderAddress, this.DestinAddress, Message);
    }

    public void ShowInfo(){
        System.out.println(this.Name + "[" + this.Frequency + "/sec] => " + this.DestinAddress+". Outbox = " + this.SentSMS);
    }

    public void Save(DataOutputStream dos){        
        try {
            dos.writeUTF(this.Name);
            dos.writeInt(this.Frequency);
            dos.writeUTF(this.SenderAddress);
            dos.writeUTF(this.DestinAddress);
            dos.writeUTF(this.Message);
            dos.writeBoolean(this.Active);
            dos.writeInt(this.SentSMS);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            this.Name = dis.readUTF();
            this.Frequency = dis.readInt();
            this.SenderAddress = dis.readUTF();
            this.DestinAddress = dis.readUTF();
            this.Message = dis.readUTF();
            this.Active = dis.readBoolean();
            this.SentSMS = dis.readInt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}