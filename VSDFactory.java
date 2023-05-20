import java.util.ArrayList;
import java.util.List;

public class VSDFactory {
    
    public List<VSD> Items;
    
    public VSDFactory(){
        this.Items = new ArrayList<>();
    }

    public List<SMSData> GetSMSData(){
        List<SMSData> result = new ArrayList<>();
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

    public void Add(VSD vsd){
        this.Items.add(vsd);
    }
}