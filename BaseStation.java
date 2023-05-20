import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class BaseStation {
    private String Name;
    private int MaxCapacity;
    private int SMSLifeSeconds;
    private Queue<SMSData> Items;
    
    public BaseStation(String name, int maxCapacity, int smsLifeSeconds){
        this.Name = name;
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

    public Boolean Add(SMSData smsData){
        var result = this.HasCapacity();
        if(result){
            this.Items.add(smsData);
        }
        return result;
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
        List<SMSData> list = new ArrayList<>();
        while(true){
            var smsData = this.Get();
            if(smsData == null){
                break;
            }
            list.add(smsData);
        }
        return list;
    }

    public void ShowInfo(){
        System.out.println(this.Name + " : size =  " + this.Items.size());
    }
}

