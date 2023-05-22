import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class VRDFactory implements IPDUDataFactory, IDataSerializer {
    
    private long LifeTime;
    private List<VRD> Items;

    public VRDFactory(long lifeTime){
        this.LifeTime = lifeTime;
        this.Items = new ArrayList<>();
    }

    public UUID GetID(){
        return UUID.randomUUID();
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
    public void ShowInfo(){
        System.out.println("VRD-Factory - " + this.Items.size() + " items:");
        for(var vrd : this.Items){
            vrd.ShowInfo();
        }
    }

    public void Save(DataOutputStream dos){        
        try {            
            int cnt = this.Items.size();
            dos.writeInt(cnt);
            for(var vbd : this.Items){
                vbd.Save(dos);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            int cnt = dis.readInt();
            for(int i=0;i<cnt;++i){
                var vrd = new VRD("");
                vrd.Load(dis);
                this.Add(vrd);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}