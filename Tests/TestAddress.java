public class TestAddress {
    public static void main(String[] args) {
        var msg1 = new PDUMessage("Hello! This is message!");
        var encoded = msg1.Encode();
        var msg2 = new PDUMessage(encoded);
        var decoded = msg2.Decode();
        System.out.println("Decoded = " + decoded);

        //TestAddress1("1234567890");
        //TestAddress1("+80935430492");
       }
    
       private static void TestAddress1(String address){
    
        PDUAddress address1 = new PDUAddress(address);    
        String encoded = address1.Encode();
        PDUAddress address2 = new PDUAddress(encoded);    
        String decoded = address2.Decode();
        System.out.println("SRC = " + address + "ENC = " + encoded + ". Decoded = " + decoded);
    
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
