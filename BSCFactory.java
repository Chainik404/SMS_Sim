import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class BSCFactory implements IPDUDataFactory{
    private String Name;
    private int MaxCapacity;
    private int SMSLifeSeconds;    
    private List<BaseStationFactory> Factories;
    
    public BSCFactory(String name,int maxCapacity, int smsLifeSeconds, boolean supportEncoding, boolean supportDecoding){
        this.Name = name;
        this.MaxCapacity = maxCapacity;
        this.SMSLifeSeconds = smsLifeSeconds;        
        this.Factories = new ArrayList<>();
    }
    
    public BaseStationFactory DeleteByID(UUID id){
        BaseStationFactory factory = null;
        if(this.Factories.size()>1){            
            for(var temp : this.Factories){
                if(temp.GetID() == id){
                    factory = temp;
                    break;
                }
            }
        }
        // check if factory is not null - move all sms to available factory
        if(factory != null){
            var index = this.Factories.indexOf(factory);
            var listData = factory.ExtractData();
            // find factory to retrieve data
            ++index;
            if(index == this.Factories.size()){
                index = index-2;
            }
            if(index<0){
                index = 0;
            }
            var newFactory = this.Factories.get(index);
            newFactory.PutData(listData);
            this.Factories.remove(factory);
        }
        return factory;
    }

    public void Add(BaseStationFactory factory){
        this.Factories.add(factory);

    }
    //#region IPDUDataTransfer implementation

    public UUID GetID(){
        return UUID.randomUUID();
    }
    public boolean SupportEncoding(){
        return false;
    }

    public boolean SupportDecoding(){
        return false;
    }

    public List<IPDUData> GetData(){
        List<IPDUData> result = new ArrayList<>();
        for(var factory : this.Factories){
            var temp = factory.GetData();
            result.addAll(temp);
        }
        return result;
    }

    public void PutData(List<IPDUData> dataList){
        
        //
    }

    public long GetLifeTime(){
        return this.SMSLifeSeconds;
    }
    //#endregion

    public void ShowInfo(){

    }
}