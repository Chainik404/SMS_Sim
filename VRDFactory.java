import java.util.ArrayList;
import java.util.List;

public class VRDFactory implements IPDUDataTransfer {
    
    private long LifeTime;
    private List<VRD> Items;

    public VRDFactory(long lifeTime){
        this.LifeTime = lifeTime;
        this.Items = new ArrayList<>();
    }

    public boolean SupportEncoding(){
        return false;
    }

    public boolean SupportDecoding(){
        return true;
    }

    public List<IPDUData> GetData(){
        List<IPDUData> result = new ArrayList<>();
        return result;
    }

    public void PutData(List<IPDUData> dataList){
        for(var pduData : dataList){
            var address = pduData.GetAddress();
            var vrd = FindByAddress(address);
            if(vrd != null){
                vrd.Add(pduData);
            }
            else{
                // ??
            }
        }

    }

    private VRD FindByAddress(String address){
        VRD result = null;
        for(var vrd : this.Items){
            if(vrd.Address == address){
                result = vrd;
                break;
            }
        }
        return result;
    }

    public long GetLifeTime(){
        return this.LifeTime;
    }

    public void Add(VRD vrd){
        this.Items.add(vrd);
    }
}