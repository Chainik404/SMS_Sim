import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class BaseStation {
    private String Name;
    private int MaxCapacity;
    private Queue<IPDUData> Items;
    
    public BaseStation(String name, int maxCapacity){
        this.Name = name;
        this.MaxCapacity = maxCapacity;
        this.Items = new LinkedList<>();
    }

    public Boolean Empty(){
        return this.Items.size() == 0;
    }

    public Boolean HasCapacity(){
        return this.Items.size() < this.MaxCapacity;
    }

    public Boolean Add(IPDUData pduData){
        var result = this.HasCapacity();
        if(result){
            this.Items.add(pduData);
        }
        return result;
    }

    public IPDUData Get(){
        IPDUData pduData = null;
        if(!this.Empty()){
            pduData = this.Items.peek();
            if(pduData.OutOfLifeTime()){
                pduData = this.Items.poll();
            }
        }
        return pduData;
    }

    public List<IPDUData> GetMany(){
        List<IPDUData> list = new ArrayList<>();
        while(true){
            var smsData = this.Get();
            if(smsData == null){
                break;
            }
            list.add(smsData);
        }
        return list;
    }

    public List<IPDUData> Extract(){
        List<IPDUData> list = new ArrayList<>();
        while(this.Items.size()>0){
            var smsData = this.Items.poll();
            if(smsData == null){
                break;
            }
            list.add(smsData);
        }
        return list;
    }

    public void ShowInfo(){
        System.out.print(this.Name + "[ " + this.Items.size()+" / " + this.MaxCapacity + "]");
    }
}