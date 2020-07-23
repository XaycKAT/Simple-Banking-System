package banking.other;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static Integer LuhnCheck(String cardNumber) {
        List<Integer> list = Arrays.stream(cardNumber.split("")).map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < list.size(); i += 2) {
            int num = list.get(i) * 2;
            if (num > 9)
                num = num / 10 + num % 10;
            list.set(i, num);
        }
        return (10 - list.stream().mapToInt(Integer::intValue).sum() % 10) % 10;
    }
}
