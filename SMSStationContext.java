import java.util.List;
import java.util.ArrayList;

public class SMSStationContext {

    public VBDFactory vbdFactory; 
    public BaseStationFactory btsSenderFactory;
    public BSCFactory bscFactory;
    public BaseStationFactory btsDestinFactory;
    public VRDFactory vrdFactory;

    public Boolean Exit;

    private List<IPDUDataFactory> Factories;
    public SMSStationContext(){
        // setup factories
        this.vbdFactory = new VBDFactory();
        this.btsSenderFactory = new BaseStationFactory("BTS[S]", 5, 2);
        this.bscFactory = new BSCFactory("BSCF", 1);
        this.btsDestinFactory = new BaseStationFactory("BTS[D]", 5, 2);
        this.vrdFactory = new VRDFactory(2);
        
        this.Exit = false;
        this.Factories = new ArrayList<>();
        this.Factories.add(this.vbdFactory);
        this.Factories.add(this.btsSenderFactory);
        this.Factories.add(this.bscFactory);
        this.Factories.add(this.btsDestinFactory);
        this.Factories.add(this.vrdFactory);
    }

    public void ShowState(){
        System.out.println("----------------------------");
        System.out.println("SMS PDU Station");
        for(var factory : this.Factories){
            factory.ShowInfo();
        }        
        System.out.println("-----------------------------------");
    }
}
