import java.util.List;
import java.lang.Thread;
import java.util.ArrayList;

public class PDUDataTransferPool implements Runnable {

    private SMSStationContext Context;
    private IPDUDataFactory Source;
    private IPDUDataFactory Destin;
    private long SleepTime;
    
    public PDUDataTransferPool(
        SMSStationContext context, 
        IPDUDataFactory source,  
        IPDUDataFactory destin,
        long sleepTime){
        this.Context = context;
        this.Source = source;
        this.Destin = destin;
        this.SleepTime = sleepTime;
    }

    public void run() {
        while(true){
            synchronized (Context) {
                if(Context.Exit){
                    return;
                }
                // get the original data from the source transfer factory
                List<IPDUData> pduDataList = this.Source.GetData();
                
                // check if source factory supports encoding - processing with encoding
                pduDataList = processEncoding(this.Source, pduDataList);
                // check if source  factory supports decoding - processing with decoding
                pduDataList = processDecoding(this.Source, pduDataList);
                // check if destin factory supports encoding - processing with encoding
                pduDataList = processEncoding(this.Destin, pduDataList);
                // check if destin factory supports decoding - processing with decoding
                pduDataList = processDecoding(this.Destin, pduDataList);                
                // reset the transferred PDU data list unit elements
                for(var pduData : pduDataList){
                    long lifeTime = this.Destin.GetLifeTime();
                    pduData.Reset(lifeTime);
                }
                // processing the data list to Destin
                this.Destin.PutData(pduDataList);
                //Context.ShowState();
            }
            try {
                Thread.sleep(this.SleepTime);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }

    private List<IPDUData> processEncoding(IPDUDataFactory factory, List<IPDUData> dataList){
        List<IPDUData> result = dataList;
        if(factory.SupportEncoding()){
            List<IPDUData> tempDataList = new ArrayList<>();
            for(var pduData : dataList){
                var tempData = pduData.Encode();
                tempDataList.add(tempData);
            }
            // clear the original list
            dataList.clear();
            // re-assign the original list
            result = tempDataList;
        }
        return result;
    }

    private List<IPDUData> processDecoding(IPDUDataFactory factory, List<IPDUData> dataList){
        List<IPDUData> result = dataList;
        if(factory.SupportDecoding()){
            List<IPDUData> tempDataList = new ArrayList<>();
            for(var pduData : dataList){
                var tempData = pduData.Decode();
                tempDataList.add(tempData);
            }
            // clear the original list
            dataList.clear();
            // re-assign the original list
            result = tempDataList;
        }
        return result;
    }
}