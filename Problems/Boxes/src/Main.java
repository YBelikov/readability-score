import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int numberOfSidesInBox = 3;
        final int[] firstBoxSidesLength = new int[numberOfSidesInBox];
        final int[] secondBoxSidesLength = new int[numberOfSidesInBox];
        for(int i  = 0; i  < numberOfSidesInBox; ++i){
            firstBoxSidesLength[i] = sc.nextInt();
        }
        for(int i  = 0; i  < numberOfSidesInBox; ++i){
            secondBoxSidesLength[i] = sc.nextInt();
        }
        Arrays.sort(firstBoxSidesLength);
        Arrays.sort(secondBoxSidesLength);
        if(firstBoxSidesLength[0] <= secondBoxSidesLength[0] && firstBoxSidesLength[numberOfSidesInBox - 1] <= secondBoxSidesLength[numberOfSidesInBox - 1]){
            System.out.println("Box 1 < Box 2");
        }else if(firstBoxSidesLength[0] >= secondBoxSidesLength[0] && firstBoxSidesLength[numberOfSidesInBox - 1] >= secondBoxSidesLength[numberOfSidesInBox - 1]) {
            System.out.println("Box 1 > Box 2");
        }else if(firstBoxSidesLength[0] == secondBoxSidesLength[0] && firstBoxSidesLength[numberOfSidesInBox - 1] == secondBoxSidesLength[numberOfSidesInBox - 1]){
            System.out.println("Box 1 = Box 2");
        }else{
            System.out.println("Incomparable");
        }
    }
}