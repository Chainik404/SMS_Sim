import java.lang.Thread;

public class BTSSenderFactoryPool implements Runnable {

    private SMSStationContext Context;
    
    public BTSSenderFactoryPool(SMSStationContext context){
        this.Context = context;
    }

    public void run() {
        while(true){
            synchronized (Context) {
                if(Context.Exit){
                    break;
                }
                // do job
                var smsDataList = this.Context.btsSenderFactory.GetMany();
                System.out.println("SMS to remove : " + smsDataList.size());
                for(var smsData : smsDataList){
                    //smsData.ShowInfo();
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