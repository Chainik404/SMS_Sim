import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class VBDFactory implements IPDUDataFactory {
    
    public List<VBD> Items;
    
    public VBDFactory(){
        this.Items = new ArrayList<>();
    }

    public UUID GetID(){
        return UUID.randomUUID();
    }
    
    public boolean SupportEncoding(){
        return true;
    }

    public boolean SupportDecoding(){
        return false;
    }

    public List<IPDUData> GetData(){
        List<IPDUData> result = new ArrayList<>();
        for(var vbd : this.Items){
            int count = vbd.GetSMSToRender();
            if(count > 0){
                for(int i=0;i<count;++i){
                    var smsData = vbd.Create();
                    result.add(smsData);
                }
            }
        }
        return result;

    }
    public void PutData(List<IPDUData> dataList){

    }
    public long GetLifeTime(){
        return 0;
    }

    public void Add(VBD vsd){
        this.Items.add(vsd);
    }

    public void ShowInfo(){
        System.out.println("VBD-Factory - " + this.Items.size() + " items:");
        for(var vbd : this.Items){
            vbd.ShowInfo();
        }
    }
}