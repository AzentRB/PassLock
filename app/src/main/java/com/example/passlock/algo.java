package com.example.passlock;

//import  aespackage.*;
//import caesarcipher.*;
import java.util.Scanner;
public class algo {
public static void main(String[] args) {
    Scanner input = new Scanner (System.in);
    System.out.println("Enter Password:");
    String password = input.nextLine();
    int shift = 5;
    final String key = "jigs1";
    StringBuffer f_enc = caesarCipher.encrypt(password,shift) ;
    System.out.println(f_enc);
    String final_enc = f_enc.toString();
    System.out.println(f_enc+"  "+final_enc);
    String enc1 = AES.encrypt(final_enc, key);
    System.out.println("Encrypted Text = "+ enc1);
    String final_dec = AES.decrypt(enc1,key);
    System.out.println(final_dec);
    String dec1 = caesarCipher.decrypt(final_dec,shift);
    
    System.out.println("Decrypted Text = "+ dec1);
    input.close();
}
}
    
    

