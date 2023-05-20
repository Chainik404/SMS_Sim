import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import sources.SMSData;

import java.util.LinkedList;

public class BaseStationFactory {
    private int MaxCapacity;
    private int SMSLifeSeconds;    
    private List<BaseStation> Stations;
    
    
    public BaseStationFactory(int maxCapacity, int smsLifeSeconds){
        this.MaxCapacity = maxCapacity;
        this.SMSLifeSeconds = smsLifeSeconds;        
        this.Stations = new ArrayList<>();
    }
    
    public SMSData Add(String address, String message){
        SMSData smsData = null;
        BaseStation station = FindAvailableStation();
        if(station != null){
            smsData = station.Add(address, message);
        }
        return smsData;
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
            result = new BaseStation(this.MaxCapacity, this.SMSLifeSeconds);
            this.Stations.add(result);
        }
        return result;
    }

    
}
