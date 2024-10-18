import com.sun.security.jgss.GSSUtil;

import java.util.Arrays;
import java.util.Scanner;

public class CheckIBAN {

    private String iBan;
    private int calcCheckSum;

    public CheckIBAN() {
        this.calcCheckSum = -1;
    }

    public String getCountryCode(String iban){
        return iban.substring(0, 2);
    }

    public String getCountryCodeNumber(String iban){
        String countryCode = "";
        for(char a : getCountryCode(iban).toCharArray()){
            countryCode += ((int) a) - 55;
        }
        countryCode += "00";

        return countryCode;
    }

    public int getCheckSum(String iban){
        return Integer.valueOf(iban.substring(2,4));
    }

    public String getAccountNumber(String iban){
        return iban.substring(4);
    }

    public String getFullCode(String iban){
        return getAccountNumber(iban) + getCountryCodeNumber(iban);
    }

    public int[] buildArray(String iban){
        int[] num = new int[iban.length()];
        for(int i = 0; i < num.length; i++){
            num[i] = (iban.toCharArray()[i] - 48);
        }
        return num;
    }

    public int calcCheckSum(int[] arr){
       int help = 0;
        for(int i : arr){
            help = ((10 * help) + i) % 97;
       }
        return 98 - (help % 97);
    }

    public boolean isCorrectIBAN(String iban){
        int checkSum = getCheckSum(iban);
        int calcCheckSum = calcCheckSum(buildArray(getFullCode(iban)));

        return calcCheckSum == checkSum;

    }


    public static void main(String[] args) {
        CheckIBAN ciban = new CheckIBAN();
        System.out.print("Bitte IBAN eingeben: ");
        Scanner s = new Scanner(System.in);
        String iban = s.nextLine().toString().replace(" ", "");

        System.out.printf("Ländercode: %s (%s)\n", ciban.getCountryCode(iban), ciban.getCountryCodeNumber(iban));
        System.out.printf("Prüfsumme: %s\n", ciban.getCheckSum(iban));
        System.out.printf("Kontonummer: %s\n", ciban.getAccountNumber(iban));
        System.out.printf("Prüfzahl: %s\n", ciban.getFullCode(iban));

        int[] arr = ciban.buildArray(ciban.getFullCode(iban));
        System.out.println(Arrays.toString(arr));
        System.out.println(ciban.calcCheckSum(arr));

        System.out.println(ciban.isCorrectIBAN(iban));
    }

}
