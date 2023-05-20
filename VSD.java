

public class VSD {
    private Integer Frequency; // quantity SMS per one second
    private String SenderAddress;
    private String DestinAddress;
    private String Message;

    private long LastSyncTimeMark;
    private Double SMSPerMillisecond;

    public VSD(Integer frequency, String senderAddress, String destinAddress, String message){
        this.Frequency = frequency;
        this.SenderAddress = senderAddress;
        this.DestinAddress = destinAddress;
        this.Message = message;
        this.SMSPerMillisecond = (Double)(frequency/1000.0);
        this.LastSyncTimeMark = 0;
    }

    public void SetFrequency(Integer frequency){
        this.Frequency = frequency;
        this.SMSPerMillisecond = (Double)(frequency/1000.0);
    }

    public int GetSMSToRender(){
        var curTime = System.currentTimeMillis();
        var diffMls = 0L;
        if(LastSyncTimeMark == 0){
            LastSyncTimeMark = curTime;
        }
        else{
            diffMls = curTime - LastSyncTimeMark;            
        }
        var result = (int)(diffMls * SMSPerMillisecond);
        if(result > 0)
        {
            LastSyncTimeMark = LastSyncTimeMark + (int)(result/SMSPerMillisecond);
        }
        return result;
    }

    public SMSData Create(){
        return new SMSData(this.SenderAddress, this.DestinAddress, Message);
    }

    public void ShowInfo(){
        System.out.println("VSD: " + this.Frequency + "/sec");
    }
}