public class HolyShitKeith {
    public static void main(String[] args) {
        // 10)    Write a Java program to find the common elements between two arrays (string values)
        Scanner scan = new Scanner(System.in); //I did it with human input values
        String[] array = new String[5];
        String[] array2 = new String[5];

        int correctValues = 0;
        int k = 0;

        System.out.println("Input five words (one at a time): ");

        for (int i = 0; i < 5; i++) {
            array[i] = scan.next();
        }

        System.out.println("Input the next set of five words: ");

        for (int i = 0; i < 5; i++) {
            array2[i] = scan.next();
        }

        scan.close();
        
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (array[i].equalsIgnoreCase(array2[j])) {
                    correctValues++;
                }
            }
        }

        if (correctValues > 0) {
            String[] results = new String[correctValues];
            
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array2.length; j++) {
                    if (array[i].equalsIgnoreCase(array2[j])) {
                        results[k] = array2[j];
                        k++;
                    }
                }
            }

            for (int i = 0; i < results.length; i++) {
                if (results[i] != null) {
                    System.out.print(results[i] + "\n"); 
                }
            }
        } else {
            System.out.println("Nothing matched");
        }
    }
}
