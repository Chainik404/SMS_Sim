import java.util.List;
import java.util.UUID;

public interface IPDUDataFactory {
    UUID GetID();
    boolean SupportEncoding();
    boolean SupportDecoding();   
    List<IPDUData> GetData();
    void PutData(List<IPDUData> dataList);
    long GetLifeTime();
}