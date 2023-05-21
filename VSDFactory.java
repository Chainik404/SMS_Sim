import java.util.ArrayList;
import java.util.List;

public class VSDFactory implements IPDUDataTransfer {
    
    public List<VSD> Items;
    
    public VSDFactory(){
        this.Items = new ArrayList<>();
    }

    public boolean SupportEncoding(){
        return true;
    }

    public boolean SupportDecoding(){
        return false;
    }

    public List<IPDUData> GetData(){
        List<IPDUData> result = new ArrayList<>();
        for(var vsd : this.Items){
            int count = vsd.GetSMSToRender();
            if(count > 0){
                for(int i=0;i<count;++i){
                    var smsData = vsd.Create();
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

    public void Add(VSD vsd){
        this.Items.add(vsd);
    }
}