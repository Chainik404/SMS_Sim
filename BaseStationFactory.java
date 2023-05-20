import java.util.ArrayList;
import java.util.List;

public class BaseStationFactory {
    private String Name;
    private int MaxCapacity;
    private int SMSLifeSeconds;    
    private List<BaseStation> Stations;
    
    
    public BaseStationFactory(String name,int maxCapacity, int smsLifeSeconds){
        this.Name = name;
        this.MaxCapacity = maxCapacity;
        this.SMSLifeSeconds = smsLifeSeconds;        
        this.Stations = new ArrayList<>();
    }
    
    public boolean Add(SMSData smsData){
        BaseStation station = FindAvailableStation();
        var result =  station != null;
        if(result){
            smsData.Init(this.SMSLifeSeconds);
            station.Add(smsData);
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
            String name = "BTS-" + index ;
            result = new BaseStation(name, this.MaxCapacity, this.SMSLifeSeconds);
            this.Stations.add(result);
        }
        return result;
    }

    public List<SMSData> GetMany(){
        List<SMSData> list = new ArrayList<>();
        List<BaseStation> removeList = new ArrayList<>();
        for(var station : this.Stations){
            var temp = station.GetMany();
            list.addAll(temp);
            if(station.Empty()){
                removeList.add(station);
            }       
        }
        // remove empty stations
        for(var station : removeList){
            this.Stations.remove(station);       
        }
        removeList.clear();
        return list;
    }

    public void ShowInfo(){

        System.out.println(this.Name + " : " + this.Stations.size());
        for(var station : this.Stations){
            station.ShowInfo();
        }
    }
}
