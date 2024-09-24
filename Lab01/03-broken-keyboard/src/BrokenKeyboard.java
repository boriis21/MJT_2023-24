public class BrokenKeyboard {
    public static int calculateFullyTypedWords(String message, String brokenKeys) {
        String[] words = message.split(" ");
        int counter = words.length;
        char[] charArray = brokenKeys.toCharArray();

        for (String word : words) {
            for (char letter : charArray) {
                if (word.isBlank() || word.contains(Character.toString(letter))) {
                    counter--;
                    break;
                }
            }
        }

        return counter;
    }

    public static void main(String[] args) {
        System.out.println(calculateFullyTypedWords("i love mjt", "qsf3o"));
        System.out.println(calculateFullyTypedWords("secret      message info      ", "sms"));
        System.out.println(calculateFullyTypedWords("dve po 2 4isto novi beli kecove", "o2sf"));
        System.out.println(calculateFullyTypedWords("     ", "asd"));
        System.out.println(calculateFullyTypedWords(" - 1 @ - 4", "s"));
    }
}