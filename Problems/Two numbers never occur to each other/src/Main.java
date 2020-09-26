import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int length = scanner.nextInt();
        final int[] numbers = new int[length];
        for(int i = 0; i < numbers.length; ++i){
            numbers[i] = scanner.nextInt();
        }

        final int first = scanner.nextInt();
        final int next = scanner.nextInt();
        boolean yes = false;
        for(int i = 0; i < numbers.length - 1; ++i){
            if((numbers[i] == first && numbers[i+1] == next) || (numbers[i] == next && numbers[i+1] == first)){
                yes = true;
                break;
            }
        }
        if(yes){
            System.out.println("false");
        }else{
            System.out.println("true");
        }
    }
}