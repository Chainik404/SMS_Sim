
public class VBD {
    private String Name;
    private Integer Frequency; // quantity SMS per one second
    private String SenderAddress;
    private String DestinAddress;
    private String Message;
    private boolean Active;

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
        return new SMSData(this.SenderAddress, this.DestinAddress, Message);
    }

    public void ShowInfo(){
        System.out.print(this.Name + "[" + this.Frequency + "/sec] => " + this.DestinAddress);
    }
}