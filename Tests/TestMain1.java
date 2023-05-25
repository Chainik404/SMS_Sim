import java.lang.System;
import java.util.Scanner;
import java.io.File;

public class TestMain1 {
    public static void main(String[] args) throws InterruptedException {

        var smsData = new SMSData("1234567890","+380935430492","Hello!This is message!");
        var pduData = smsData.Encode();
        var sms2 = pduData.Decode();
        //
        var context = new SMSStationContext();

        var filePath = "smsstation.bin";
        File file = new File(filePath);

        if (file.exists()) {
            context.Load(filePath);
            context.ShowState();
        } else {
            var address1 = "111";
            var address2 = "222";
            
            // 
            var vbd1 = new VBD("VBD-1", 1, "123123", address1, "SMS Message-1");
            var vbd2 = new VBD("VBD-2", 2, "123123", address2, "SMS Message-2");
            context.vbdFactory.Add(vbd1);
            context.vbdFactory.Add(vbd2);
            //
            var bscFactory1 = new BaseStationFactory("BSC-1", 3, 1);
            context.bscFactory.Add(bscFactory1);
            var bscFactory2 = new BaseStationFactory("BSC-2", 3, 1);
            context.bscFactory.Add(bscFactory2);
            //
            var vrd1 = new VRD(address1);
            var vrd2 = new VRD(address1);
            context.vrdFactory.Add(vrd1);
            context.vrdFactory.Add(vrd2);
        //
        }

        // VSD -> BTS-SENDER POOL
        var vsdPool = runPDUTransfer(context, context.vbdFactory, context.btsSenderFactory, 2000);        
        // BTS-SENDER -> BSC FACTORIES POOL
        var btsSenderPool = runPDUTransfer(context, context.btsSenderFactory, context.bscFactory, 1000);  
        // BSC -> BTS-DESTIN
        var bscPool = runPDUTransfer(context, context.bscFactory, context.btsDestinFactory, 2000);  
        // BTS-DESTIN -> VRD
        var vrdPool = runPDUTransfer(context, context.btsDestinFactory, context.vrdFactory, 1000);  
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
        context.Save(filePath);
    }

    private static Thread runPDUTransfer(
        SMSStationContext context, 
        IPDUDataFactory source,
        IPDUDataFactory destin, long sleepTime)
    {
        var pool = new PDUDataTransferPool(context, source, destin, sleepTime);
        var result = new Thread(pool);
        result.start();
        return result;
    }
}