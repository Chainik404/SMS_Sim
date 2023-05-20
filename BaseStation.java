import java.util.Queue;

import sources.SMSData;

import java.util.LinkedList;

public class BaseStation {
    private int MaxCapacity;
    private int SMSLifeSeconds;
    private Queue<SMSData> Items;
    
    
    public BaseStation(int maxCapacity, int smsLifeSeconds){
        this.MaxCapacity = maxCapacity;
        this.SMSLifeSeconds = smsLifeSeconds;
        this.Items = new LinkedList<>();
    }

    public Boolean Empty(){
        return this.Items.size() == 0;
    }

    public Boolean HasCapacity(){
        return this.Items.size() < this.MaxCapacity;
    }

    public SMSData Add(String address, String message){
        SMSData smsData = null;
        if(this.HasCapacity()){
            smsData = new SMSData(address, message, this.SMSLifeSeconds);
            this.Items.add(smsData);
        }
        return smsData;
    }

    public SMSData Get(){
        SMSData smsData = null;
        if(!this.Empty()){
            smsData = this.Items.peek();
            if(smsData.GoAway()){
                smsData = this.Items.poll();
            }
        }
        return smsData;
    }

    public List<SMSData> GetMany(){
        List<SMSData> list = new List<>();
        while(true){
            var smsData = this.Get();
            if(smsData == null){
                break;
            }
            list.Add(smsData);
        }
        return list;
    }
}

