package com.example.passlock;
import java.lang.Math;
public class caesarCipher {

    public static StringBuffer encrypt(String text, int s) 
    { 
        StringBuffer result= new StringBuffer(); 
  
        for (int i=0; i<text.length(); i++) 
        { 
            if (Character.isUpperCase(text.charAt(i))) 
            { 
                char ch = (char)(((int)text.charAt(i) + 
                                  s - 65) % 26 + 65); 
                result.append(ch); 
            } 
            else if (Character.isLowerCase(text.charAt(i)))
            { 
                char ch = (char)(((int)text.charAt(i) + 
                                  s - 97) % 26 + 97); 
                result.append(ch); 
            } 
            else if(Character.isDigit(text.charAt(i)))
            {
                char ch = text.charAt(i);
                int n = Integer.parseInt(String.valueOf(ch));
                if(n+s>9)
                n = (n+s)%9;
                else
                n=n+s;
                
                char c=Character.forDigit(n,10); 
                result.append(c); 
            }
            else{
                result.append(text.charAt(i));
            }
        } 
        return result; 
    } 

    public static String decrypt(String cipher, int shift) 
    {
        String message = cipher;
        String decryptedMessage = "";
        char ch;
        int key = shift;

        for(int i = 0; i < message.length(); ++i){
			ch = message.charAt(i);
			
			if(ch >= 'a' && ch <= 'z'){
	            ch = (char)(ch - key);
	            
	            if(ch < 'a'){
	                ch = (char)(ch + 'z' - 'a' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else if(ch >= 'A' && ch <= 'Z'){
	            ch = (char)(ch - key);
	            
	            if(ch < 'A'){
	                ch = (char)(ch + 'Z' - 'A' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
            else if(Character.isDigit(ch))
            {
                int num = Character.getNumericValue(ch);
                int diff = num-key;
                if(diff<0)
                {
                    diff = Math.abs(diff);
                }
                if(diff>9)
                num = diff%9;
                else
                num=diff;
                char c = Character.forDigit(num,10); 
                decryptedMessage += c;
            }
	        else {
	        	decryptedMessage += ch;
	        }
		}
        return decryptedMessage;
    }
  
    // Driver code 
/*     public static void main(String[] args) 
    { 
        String text = "qpnulzo$801"; 
        int s = 7; 
    
        String cipher = "qpnulzo$801";
        System.out.println("Text  : " + text); 
        System.out.println("Shift : " + s); 
    
        System.out.println("decrypted: "+ decrypt(cipher,s)); 
    }  */
} 
