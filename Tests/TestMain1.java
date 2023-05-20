import java.lang.System;
import java.util.Scanner;

public class TestMain1 {
    public static void main(String[] args) throws InterruptedException {
        var context = new SMSStationContext();
        // vsd
        var vsd1 = new VSD(1, "123123", "123123123", "SMS Message");
        context.vsdFactory.Add(vsd1);
        //var vsd2 = new VSD(5, "123123", "123123123", "SMS Message");
        //context.vsdFactory.Add(vsd2);
        //
        var vsdPool = new Thread(new VSDFactoryPool(context));
        vsdPool.start();
        var btsSenderPool = new Thread(new BTSSenderFactoryPool(context));
        btsSenderPool.start();
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
}