

public class SMSStationContext {
    public VSDFactory vsdFactory; 
    public BaseStationFactory btsSenderFactory;

    public BaseStationFactory btsDestinFactory;

    public Boolean Exit;

    public SMSStationContext(){
        this.vsdFactory = new VSDFactory();
        this.btsSenderFactory = new BaseStationFactory("BTS-Sender", 5, 3);
        this.Exit = false;
    }

    public void ShowState(){
        System.out.println("----------------------------");
        System.out.println("SMS PDU Station");
        for(var vsd : this.vsdFactory.Items){
            vsd.ShowInfo();
        }
        this.btsSenderFactory.ShowInfo();
        System.out.println("-----------------------------------");
    }
}
