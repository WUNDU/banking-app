package ao.com.wundu.util;

import java.util.Random;

public class AccountNumberGenerator {

    public static String generate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 17; i++) {
            sb.append(random.nextInt(10)); // número entre 0 e 9
        }
        return sb.toString();
    }
}
