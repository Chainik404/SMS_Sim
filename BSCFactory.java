import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BSCFactory implements IPDUDataFactory, IDataSerializer{
    private String Name;    
    private int SMSLifeSeconds;    
    private List<BaseStationFactory> Factories;
    
    public BSCFactory(String name,int smsLifeSeconds){
        this.Name = name;
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
    //#region IPDUDataFactory implementation

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
        // move data from one factory to another        
        int cnt = this.Factories.size()-1;
        if(cnt > 0){
            for(int i=0;i<cnt;++i){
                var src = this.Factories.get(i);
                var dst = this.Factories.get(i+1);
                var list = src.GetData();
                dst.PutData(list);
            }
        }
        if(cnt > -1){
            var lastFactory = this.Factories.get(cnt);
            result = lastFactory.GetData();
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
        System.out.println(this.Name + "(" + this.Factories.size()+") : ");
        for(var factory : this.Factories){
            factory.ShowInfo();
        }
    }

    public void Save(DataOutputStream dos){        
        try {
            dos.writeUTF(this.Name);
            dos.writeInt(this.SMSLifeSeconds);           
            int cnt = this.Factories.size();
            dos.writeInt(cnt);
            for(var factory : this.Factories){
                factory.Save(dos);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            this.Name = dis.readUTF();
            this.SMSLifeSeconds = dis.readInt();
            int cnt = dis.readInt();
            for(int i=0;i<cnt;++i){
                var factory = new BaseStationFactory("temp",1,1);
                factory.Load(dis);
                this.Add(factory);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}