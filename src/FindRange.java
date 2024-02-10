public class FindRange {

    /**
     * Error Digit Range A system reconstructs an integer that is input into it but by possibly misinterpreting any one of the digits (from 0-9) in the input.
     * For example, if the digit 1 is misinterpreted as 9, and the input being 11891, the system would reconstruct it as 99899. Given an input integer num,
     * find the difference between the maximum and minimum possible reconstructions. 
     * Note Any reconstruction cannot change the number of significant digits in the integer The first digit of the number
     * can't be interpreted to be 0. Function Description Complete the findRange function in the editor below. 
     * The function must return a long integer denoting the difference
     * between the maximum and minimum possible reconstructions findRange has the following parameter(s): num: 
     * An integer, which is the integer input to the system Constraints 1 num s
     * 10 Input Format For Custom Testing v Sample Case 0 Sample Input 0 123512 Sample Output 0 820082 Explanation 0 
     * The maximum possible reconstruction is 923592 when 1 is interpreted as 9 .
     * The minimum possible reconstruction is 103510 when 2 is interpreted as 0 Thus the difference is 820082
     */
    public static int findRange(int s) {

        char ZERO = '0';
        String NINE = String.valueOf('9');

        // convert current int to string to facilitate
        String integerFromStringValue = Integer.toString(s);
        String maximum_diff = integerFromStringValue;
        String minimum_diff = integerFromStringValue;

        for (int i = 0; i <= 9; i++) {
            String current_num = Integer.toString(i);
            // replace current number by nine
            String maximum_str = integerFromStringValue.replace(current_num, NINE);
            int maximum_int = Integer.parseInt(maximum_str);

            boolean max_start_with_zero = maximum_str.charAt(0) != ZERO;
            // check if the number starts with zero and if existent maximum is grater then current maximum
            if (max_start_with_zero && maximum_int > Integer.parseInt(maximum_diff)) {
                maximum_diff = maximum_str;
            }

            // replace current number by zero
            String minimum_str = integerFromStringValue.replace(current_num, String.valueOf(ZERO));
            int minimum_int = Integer.parseInt(minimum_str);

            boolean min_start_with_zero = minimum_str.charAt(0) != ZERO;
            // check if the number starts with zero  and existent minimum is less then current minimum
            if (min_start_with_zero && minimum_int < Integer.parseInt(minimum_diff)) {
                minimum_diff = minimum_str;
            }
        }

        // subtract maximum found by minimum found
        int i = Integer.parseInt(maximum_diff) - Integer.parseInt(minimum_diff);
        System.out.println(i);

        return i;
    }
}
