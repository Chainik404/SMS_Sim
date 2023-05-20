import java.util.UUID;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SMSData {

    private UUID Id;
    private PDUAddress SenderAddress;
    private PDUAddress DestinAddress;
    private PDUMessage Message;
    private LocalDateTime CreatedOn;
    private int LifeSeconds;

    public SMSData(String senderAddress, String destinAddress, String message){
        this.Id = UUID.randomUUID();
        this.SenderAddress = new PDUAddress(senderAddress);
        this.DestinAddress = new PDUAddress(destinAddress);
        this.Message = new PDUMessage(message);
        this.CreatedOn = LocalDateTime.now();
        this.LifeSeconds = 0;
    }

    public void Init(int lifeSeconds){
        this.CreatedOn = LocalDateTime.now();
        this.LifeSeconds = lifeSeconds;
    }
    
    public Boolean GoAway(){
        var now = LocalDateTime.now();
        var spent = Duration.between(this.CreatedOn, now);
        return spent.getSeconds() > this.LifeSeconds;
    }

    public void ShowInfo(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");
        String formattedDateTime = this.CreatedOn.format(formatter); 
        
        System.out.println("SMS: " + this.Id + "Created: " + formattedDateTime);
    }
}
