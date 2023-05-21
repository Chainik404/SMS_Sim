import java.util.ArrayList;
import java.util.List;

public class VRD {
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
        System.out.println("\tVRD: [" + this.Address + "]. Inbox: " + cnt + " sms(s)");
    }
}
