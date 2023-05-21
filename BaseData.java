import java.util.UUID;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseData {

    protected UUID Id;
    protected LocalDateTime CreatedOn;
    protected long LifeSeconds;

    public BaseData(){
        this.Id = UUID.randomUUID();
        this.CreatedOn = LocalDateTime.now();
        this.LifeSeconds = 0;
    }

    public UUID GetID(){
        return this.Id;
    }

    public void Reset(long lifeSeconds){
        this.CreatedOn = LocalDateTime.now();
        this.LifeSeconds = lifeSeconds;
    }
    public boolean SupportEncoding(){
        return false;
    }
    public boolean SupportDecoding(){
        return false;
    }
    public IPDUData Encode(){
        return null;
    }
    public IPDUData Decode(){
        return null;
    }

    public boolean OutOfLifeTime(){
        var now = LocalDateTime.now();
        var spent = Duration.between(this.CreatedOn, now);
        return spent.getSeconds() > this.LifeSeconds;
    }
}