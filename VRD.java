import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class VRD implements IDataSerializer{
    public String Address;
    private List<IPDUData> Inbox;
    
    public VRD(String address){
        this.Address = address;
        this.Inbox = new ArrayList<>();
    }

    public void Clear(){
        this.Inbox.clear();
    }

    public void Add(IPDUData data){
        this.Inbox.add(data);
    }

    public void ShowInfo(){
        var cnt = this.Inbox.size();
        System.out.print("VRD: [" + this.Address + "]. Inbox: " + cnt + " sms(s)");
    }

    public void Save(DataOutputStream dos){        
        try {
            dos.writeUTF(this.Address);
            int cnt = this.Inbox.size();
            dos.writeInt(cnt);
            for(var sms : this.Inbox){
                var temp = (Object)sms;
                var strg = (IDataSerializer)temp;
                if(strg != null){
                    strg.Save(dos);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void Load(DataInputStream dis){
        try {
            this.Address = dis.readUTF();
            int cnt = dis.readInt();
            for(int i=0;i<cnt;++i){
                var sms = new SMSData("1", this.Address, "");
                sms.Load(dis);
                this.Inbox.add(sms);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
