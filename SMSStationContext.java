

public class SMSStationContext {

    public VSDFactory vsdFactory; 
    public BaseStationFactory btsSenderFactory;
    public BSCFactory bscFactory;
    public BaseStationFactory btsDestinFactory;
    public VRDFactory vrdFactory;

    public Boolean Exit;

    public SMSStationContext(){
        this.vsdFactory = new VSDFactory();
        this.btsSenderFactory = new BaseStationFactory("BTS[S]", 5, 2, false, false);
        this.bscFactory = new BSCFactory(null, 0, 0, false, false)
        this.btsDestinFactory = new BaseStationFactory("BTS[D]", 5, 2, false, false);
        this.vrdFactory = new VRDFactory();
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
