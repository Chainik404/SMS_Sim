import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BaseStationFactory implements IPDUDataFactory, IDataSerializer{
    private UUID Id;
    private String Name;
    private int MaxCapacity;
    private int SMSLifeSeconds;    
    private List<BaseStation> Stations;
    
    
    public BaseStationFactory(String name,int maxCapacity, int smsLifeSeconds){
        this.Id = UUID.randomUUID();
        this.Name = name;
        this.MaxCapacity = maxCapacity;
        this.SMSLifeSeconds = smsLifeSeconds;        
        this.Stations = new ArrayList<>();
    }
    
    public boolean Add(IPDUData pduData){
        BaseStation station = FindAvailableStation();
        var result =  station != null;
        if(result){
            pduData.Reset(this.SMSLifeSeconds);
            station.Add(pduData);
        }
        return result;
    }

    private BaseStation FindAvailableStation(){
        BaseStation result = null;
        for(var station : this.Stations){
            if(station.HasCapacity()){
                result = station;
                break;
            }
        }
        if(result == null){
            var index = this.Stations.size()+1;
            String name = this.Name + " - " + index ;
            result = new BaseStation(name, this.MaxCapacity);
            this.Stations.add(result);
        }
        return result;
    }

    //#region IPDUDataFactory implementation

    public UUID GetID(){
        return this.Id;
    }

    public boolean SupportEncoding(){
        return false;
    }

    public boolean SupportDecoding(){
        return false;
    }

    public List<IPDUData> GetData(){
        List<IPDUData> result = new ArrayList<>();
        
        List<BaseStation> removeList = new ArrayList<>();
        for(var station : this.Stations){
            var temp = station.GetMany();
            result.addAll(temp);
            if(station.Empty()){
                removeList.add(station);
            }       
        }
        // remove empty stations
        for(var station : removeList){
            this.Stations.remove(station);       
        }
        removeList.clear();
        return result;
    }

    public void PutData(List<IPDUData> dataList){
        for(var pduData : dataList){
            this.Add(pduData);
        }
    }

    public long GetLifeTime(){
        return this.SMSLifeSeconds;
    }
    //#endregion

    public List<IPDUData> ExtractData(){
        List<IPDUData> result = new ArrayList<>();        
        for(var station : this.Stations){
            var temp = station.Extract();
            result.addAll(temp);       
        }
        return result;
    } 

    public void ShowInfo(){
        System.out.println(this.Name + "(" + this.Stations.size()+") : ");
        for(var station : this.Stations){
            station.ShowInfo();
        }
    }

    public void Save(DataOutputStream dos){        
        try {
            dos.writeUTF(this.Id.toString());
            dos.writeUTF(this.Name);
            dos.writeInt(this.MaxCapacity);
            dos.writeInt(this.SMSLifeSeconds);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            this.Id = UUID.fromString(dis.readUTF());
            this.Name = dis.readUTF();
            this.MaxCapacity = dis.readInt();
            this.SMSLifeSeconds = dis.readInt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
