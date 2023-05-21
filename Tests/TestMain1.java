import java.lang.System;
import java.util.Scanner;

public class TestMain1 {
    public static void main(String[] args) throws InterruptedException {

        var context = new SMSStationContext();
        // 
        var vsd1 = new VSD(1, "123123", "123123123", "SMS Message");
        context.vsdFactory.Add(vsd1);
        //var vsd2 = new VSD(5, "123123", "123123123", "SMS Message");
        //context.vsdFactory.Add(vsd2);
        //

        // VSD -> BTS-SENDER POOL
        var vsdPool = runPDUTransfer(context, context.vsdFactory, context.btsSenderFactory, 2000);        
        // BTS-SENDER -> BSC FACTORIES POOL
        var btsSenderPool = runPDUTransfer(context, context.btsSenderFactory, context.bscFactory, 2000);  
        // BSC -> BTS-DESTIN
        var bscPool = runPDUTransfer(context, context.bscFactory, context.btsDestinFactory, 2000);  
        // BTS-DESTIN -> VRD
        var vrdPool = runPDUTransfer(context, context.btsDestinFactory, context.vrdFactory, 2000);  
        //
        
        Scanner sc = new Scanner(System.in);
        while(true){
            var ch = sc.nextInt();
            if(ch == 0){
                context.Exit = true;                
                break;
            }
            if(ch == 1){
                synchronized(context){
                    context.ShowState();
                }
            }
            Thread.sleep(300);
        }
        sc.close();
    }

    private static Thread runPDUTransfer(
        SMSStationContext context, 
        IPDUDataFactory source,
        IPDUDataFactory destin, long sleepTime)
    {
        var pool = new PDUDataTransferPool(context, source, destin, sleepTime);
        var result = new Thread(pool));
        result.start();
        return result;
    }
}