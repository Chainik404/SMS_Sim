import java.util.UUID;

public interface IPDUData {
    UUID GetID();

    boolean SupportEncoding();
    boolean SupportDecoding();

    IPDUData Encode();
    IPDUData Decode();
    
    void Reset(long lifeSeconds);
    boolean OutOfLifeTime();

    String GetAddress();
}