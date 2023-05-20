import java.lang.Thread;

public class VSDFactoryPool implements Runnable {

    private SMSStationContext Context;
    
    public VSDFactoryPool(SMSStationContext context){
        this.Context = context;
    }

    public void run() {
        while(true){
            synchronized (Context) {
                if(Context.Exit){
                    break;
                }
                // do job
                var smsDataList = this.Context.vsdFactory.GetSMSData();
                for(var smsData : smsDataList){
                    this.Context.btsSenderFactory.Add(smsData);
                }
                Context.ShowState();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }
}