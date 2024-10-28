package lab7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StringPermutations {

    // Main function to prompt user for input and generate permutations
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string to generate its permutations: ");
        String input = scanner.nextLine();
        
        // Error handling for empty input
        if (input == null || input.isEmpty()) {
            System.out.println("Error: Input string cannot be empty.");
            return;
        }

        System.out.print("Do you want to exclude duplicate permutations? (yes/no): ");
        String excludeDuplicates = scanner.nextLine().trim().toLowerCase();

        List<String> permutations;
        if (excludeDuplicates.equals("yes")) {
            permutations = generatePermutations(input, true);
        } else {
            permutations = generatePermutations(input, false);
        }

        System.out.println("Generated Permutations:");
        for (String permutation : permutations) {
            System.out.println(permutation);
        }
        
        scanner.close();
    }

    /**
     * Recursive function to generate all permutations of a string
     *
     * @param input the input string
     * @param excludeDuplicates flag to exclude duplicate permutations
     * @return list of all permutations of the input string
     */
    public static List<String> generatePermutations(String input, boolean excludeDuplicates) {
        List<String> result = new ArrayList<>();
        if (excludeDuplicates) {
            permuteUnique(input.toCharArray(), 0, result, new HashSet<>());
        } else {
            permute(input.toCharArray(), 0, result);
        }
        return result;
    }

    /**
     * Helper recursive function to generate permutations, allowing duplicates
     *
     * @param chars  the character array of the input string
     * @param index  current index for swapping
     * @param result list to store permutations
     */
    private static void permute(char[] chars, int index, List<String> result) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            permute(chars, index + 1, result);
            swap(chars, index, i); // backtrack
        }
    }

    /**
     * Helper recursive function to generate permutations, excluding duplicates
     *
     * @param chars  the character array of the input string
     * @param index  current index for swapping
     * @param result list to store unique permutations
     * @param seen   set to track generated permutations
     */
    private static void permuteUnique(char[] chars, int index, List<String> result, Set<String> seen) {
        if (index == chars.length - 1) {
            String permutation = new String(chars);
            if (!seen.contains(permutation)) {
                seen.add(permutation);
                result.add(permutation);
            }
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            permuteUnique(chars, index + 1, result, seen);
            swap(chars, index, i); // backtrack
        }
    }

    /**
     * Utility function to swap characters at two positions in a character array
     *
     * @param chars the character array
     * @param i     the first position
     * @param j     the second position
     */
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
