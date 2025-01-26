package Cryptography.Lab1;

public class Problem7PlayFairEncryption {
    public static void main(String[] args) {
        String plainText ="wearediscoveredsaveyourself";
        plainText = plainText.toLowerCase().replace('j','i').replace(" ","");

        char[][] keyMatrix = {
                {'s','r','m','a','p'},
                {'u','n','i','v','e'},
                {'t','y','b','c','d'},
                {'f','g','h','k','l'},
                {'o','q','w','x','z'}
        };

        String Encrypted = encrypt(plainText,keyMatrix);
        System.out.println("Encrypted Message : " + Encrypted);

    }

    public static String encrypt(String plainText, char[][] keyMatrix) {

        StringBuilder pt = new StringBuilder();

        for(int i=0;i<plainText.length();i+=2) {
            char f = plainText.charAt(i);
            char s = i+1<plainText.length()?plainText.charAt(i+1):'x';

            if(f==s){
                pt.append(f).append('x');
                i--;
            }else {
                pt.append(f).append(s);
            }
        }

        System.out.println("Processed Text : " + pt.toString());

        StringBuilder et = new StringBuilder();

        for(int i=0;i<pt.length();i+=2){
            int[] fc=findIndex(pt.charAt(i),keyMatrix);
            int[] sc=findIndex(pt.charAt(i+1),keyMatrix);

            if(fc[0]==sc[0]){ //same row

                et.append(keyMatrix[fc[0]][(fc[1]+1)%5]).append(keyMatrix[sc[0]][(sc[1]+1)%5]);

            } else if (fc[1]==sc[1]) { //same col

                et.append(keyMatrix[(fc[0]+1)%5][fc[1]]).append(keyMatrix[(sc[0]+1)%5][sc[1]]);

            }else{ //rectangle rule

                et.append(keyMatrix[fc[0]][sc[1]]).append(keyMatrix[sc[0]][fc[1]]);

            }
        }


        return  et.toString();
    }

    public static int[] findIndex(char c, char[][] keyMatrix) {
        for(int i=0;i<keyMatrix.length;i++) {
            for(int j=0;j<keyMatrix[i].length;j++) {
                if(c==keyMatrix[i][j]) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
}
