public class Test_02 {
   public static void main(String[] args) {
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
