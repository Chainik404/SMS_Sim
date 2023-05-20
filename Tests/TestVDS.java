import java.lang.System;
import java.util.Scanner;

public class TestVDS {
    public static void main(String[] args) {
        var vsd = new VSD(20,"","","");
        vsd.SetFrequency(50);
        Scanner sc = new Scanner(System.in);
        while(true){
            var ch = sc.nextInt();
            if(ch == 0){
                break;
            }
            int sms = vsd.GetSMSToRender();
            System.out.println("SMS to send = " +  sms);
        }
        sc.close();
    }
}
