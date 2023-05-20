import java.util.UUID;
import java.time.Duration;
import java.time.LocalDateTime;

public class SMSData {

    private UUID Id; 
    private PDUAddress Address;
    private PDUMessage Message;
    private LocalDateTime CreatedOn;
    private int LifeSeconds;

    public SMSData(String address, String message, int lifeSeconds){
        this.Id = UUID.randomUUID();
        this.Address = new PDUAddress(address);
        this.Message = new PDUMessage(message);
        this.CreatedOn = LocalDateTime.now();
        this.LifeSeconds = lifeSeconds;
    }
    
    public Boolean GoAway(){
        var now = LocalDateTime.now();
        var spent = Duration.between(this.CreatedOn, now);
        return spent.getSeconds() > this.LifeSeconds;
    }
}
