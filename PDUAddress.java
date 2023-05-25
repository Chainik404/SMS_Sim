public class PDUAddress {

    public static final int ADDRESS_TYPE_MASK          = 0x70; // 01110000
	public static final int ADDRESS_TYPE_UNKNOWN       = 0x00;
	public static final int ADDRESS_TYPE_INTERNATIONAL = 0x10; // 00010000
    public static final int ADDRESS_TYPE_NATIONAL      = 0x20; // 00100000
	public static final int ADDRESS_TYPE_ALPHANUMERIC  = 0x50;  // 01110000
    public static final String ADDRESS_INTR_HEADER = "91";
    private String Number;
    private Boolean International;

    public PDUAddress(String address){

        this.Number = address;
        this.International = address.startsWith("+");
        if (this.International)
		{
			this.Number = address.substring(1);
		}
    }

    public String Value(){
        return this.Number;
    }
    
    public String Encode(){
        // Convert the number to ASCII
        String result = Long.toHexString(Long.parseLong(this.Number));

        // Add the "91" prefix which indicates an international format number
        if(this.International){
            result = ADDRESS_INTR_HEADER + result;
        }

        // lengh byte
        var len = result.length() / 2;
        String lengthByte = Integer.toHexString(len);
        if(lengthByte.length()<2){
            lengthByte = "0"+lengthByte;
        }
        // Swap the characters
        String swapped = swapCharacters(result);

        result = lengthByte + swapped;
        return result;
    }

    public String Decode(){
        var lengthStr = this.Number.substring(0,2);
        var length = 2 * Integer.parseInt(lengthStr, 16);

        // skip the length
        String address = this.Number.substring(2,2+length);

        String result = swapCharacters(address);
        // check if international
        this.International = result.startsWith(ADDRESS_INTR_HEADER);
        if(this.International ){
            result = result.substring(ADDRESS_INTR_HEADER.length());
        }
        result = String.valueOf(Long.parseLong(result, 16));
        if(this.International){
            result = "+" + result;
        }
        return result;
    }

    private String swapCharacters(String address) {
        StringBuilder swapped = new StringBuilder();

        for (int i = 0; i < address.length() - 1; i += 2) {
            swapped.append(address.charAt(i + 1)).append(address.charAt(i));
        }

        return swapped.toString();
    }
}