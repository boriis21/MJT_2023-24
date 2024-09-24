public class JumpGame {
    public static boolean canWin(int[] array) {
        int maxReach = 0;

        for (int i = 0; i < array.length; i++) {
            if (i > maxReach) {
                return false;
            }

            maxReach = Math.max(maxReach, i + array[i]);
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(canWin(new int[]{2, 3, 1, 1, 0}));
        System.out.println(canWin(new int[]{3, 2, 1, 0, 0}));
        System.out.println(canWin(new int[]{3, 3, 0, 0, 0}));
    }
}