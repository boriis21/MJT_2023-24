public class IPValidator {
    public static boolean validateIPv4Address(String str) {
        String[] numbers = str.split("\\.");
        if (numbers.length != 4) {
            return false;

        }
        int[] array = new int[4];

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].startsWith("0")){
                return false;
            }

            array[i] = Integer.parseInt(numbers[i]);

            if (array[i] < 0 || array[i] > 255) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(validateIPv4Address("192.168.1.01")); // false
        System.out.println(validateIPv4Address("255.255.255.255")); // true
        System.out.println(validateIPv4Address("256.100.100.100")); // false
        System.out.println(validateIPv4Address("123.456.78.90")); // false
        System.out.println(validateIPv4Address("192.168.1")); // false
    }
}