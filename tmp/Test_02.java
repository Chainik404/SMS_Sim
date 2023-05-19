public class Test_02 {
    public static void main(String[] args) {
        TestAddress("1234567890");
        TestAddress("+80935430492");
       }
    
       private static void TestAddress(String address){
    
        PDUAddress address1 = new PDUAddress(address);    
        String encoded = address1.Encode();
        PDUAddress address2 = new PDUAddress(encoded);    
        String decoded = address2.Decode();
        System.out.println("SRC = " + address + " DST = " + decoded);
    
       }

   public static void main2(String[] args) {
    String str = "852 90288000";
    System.out.println(encodeMessage(str));
   }
   private static String encodeMessage(String message){
        StringBuilder sBuilder = new StringBuilder();
        for(char c : message.toCharArray()){
            sBuilder.append(Integer.toHexString((int) c));
        }
        return sBuilder.toString();

    } 
}
