import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class SMSStationContext {

    public VBDFactory vbdFactory; 
    public BaseStationFactory btsSenderFactory;
    public BSCFactory bscFactory;
    public BaseStationFactory btsDestinFactory;
    public VRDFactory vrdFactory;

    public Boolean Exit;

    private List<IPDUDataFactory> Factories;
    private List<IDataSerializer> Storages;
    public SMSStationContext(){
        // setup factories
        this.vbdFactory = new VBDFactory();
        this.btsSenderFactory = new BaseStationFactory("BTS[S]", 5, 2);
        this.bscFactory = new BSCFactory("BSCF", 1);
        this.btsDestinFactory = new BaseStationFactory("BTS[D]", 5, 2);
        this.vrdFactory = new VRDFactory(2);
        
        this.Exit = false;
        //
        this.Factories = new ArrayList<>();
        this.Factories.add(this.vbdFactory);
        this.Factories.add(this.btsSenderFactory);
        this.Factories.add(this.bscFactory);
        this.Factories.add(this.btsDestinFactory);
        this.Factories.add(this.vrdFactory);
        //
        this.Storages = new ArrayList<>();
        this.Storages.add(vbdFactory);
        this.Storages.add(bscFactory);
        this.Storages.add(vrdFactory);
    }

    public void ShowState(){
        System.out.println("----------------------------");
        System.out.println("SMS PDU Station");
        for(var factory : this.Factories){
            factory.ShowInfo();
        }        
        System.out.println("-----------------------------------");
    }

    public void Save(String fileName){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            for(var storage : this.Storages){
                storage.Save(dos);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Load(String fileName){
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            for(var storage : this.Storages){
                storage.Load(dis);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
