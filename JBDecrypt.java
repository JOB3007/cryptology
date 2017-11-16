/*
 * Java program to decrypt message
 */
import javax.swing.JOptionPane;
//import java.util.*;

public class JBDecrypt {  
	public static void main(String[] args) {

		//input code text
		String codetext = "NHEJHDFWHWXHHMRC";
		do {
			codetext = JOptionPane.showInputDialog("Geben Sie eine Nachricht ein", codetext);	
		} while (codetext.matches(".*[^a-zA-Z].*")); //wiederhole solange Buchstaben außer a-zA-Z gefunden werden

		//input password
		String passwort = "CODE";
		do {
			passwort = JOptionPane.showInputDialog("Geben Sie ein Passwort ein", passwort);	
		} while (passwort.matches(".*[^a-zA-Z].*")); //wiederhole solange Buchstaben außer a-zA-Z gefunden werden

		
		/* 
		Entschlüsselung nach Substitutionsverfahren
		===========================================
		*/		
		//get markup of password for substitution
		char c;
		String q = "";
		int[] arrMarkupInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			c = passwort.charAt(i);
			q = Character.toString(passwort.charAt(i));
			
			// Nummer im Alphabet
			if (q.matches("[A-Z]")) {
				arrMarkupInt[i] = ((int) c)-64;
			}
			else {
				arrMarkupInt[i] = ((int) c)-96;
			}
			//System.out.println("i: "+i+" "+arrMarkupInt[i]);
		}
		
		/*
		System.out.print("Decode Substitutionsverfahren: "+passwort+" - ");
		for(int i=0; i<arrMarkupInt.length; i++) {System.out.print(arrMarkupInt[i]+" ");}
		System.out.println("\n");
		*/		
		
		//substitute plain text
		int a=0;
		String klartext = "";
		int j=0;
		for(int i = 0;i<codetext.length();i++) {
			c = codetext.charAt(i);
			q = Character.toString(codetext.charAt(i));

			// Nummer im Alphabet
			if (q.matches("[A-Z]")) {				
				a = ((int) c)-64;
			}
			else {
				a = ((int) c)-96;
			}
			//System.out.println("i: "+i+" "+a);
			
			//substitute letter
			if(a<arrMarkupInt[j]){
				a = a - arrMarkupInt[j]+26;		// Subtraktion
				} else {
				a = a - arrMarkupInt[j];
				}
			c = (char) (a+64);
			
			// write secret message
			klartext = klartext + c;
			//System.out.println(codetext.charAt(i)+" - "+passwort.charAt(j)+" = " +c);
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;}
		}
		//System.out.println("\n"+"Plain Message nach Substitutionsverfahren: " + klartext+"\n");	

		
		/* 
		Entschlüsselung nach Transpositionsverfahren
        ============================================
		*/
		//get order of password
		int[] arrOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			for(j=0; j<passwort.length(); j++) {
				if(passwort.charAt(i)>passwort.charAt(j)) {				// der größere Buchstabe rutscht nach hinten 
					arrOrderInt[i] = arrOrderInt[i] + 1;
				} else if(passwort.charAt(i)==passwort.charAt(j)) {     // bei gleichen Buchstaben ... 
					if(i<j) {											// ... rutscht der vordere nach hinten	
						arrOrderInt[i] = arrOrderInt[i] + 1;
						}
				}
					//System.out.println("i: "+i+" "+arrOrderInt[i]);
				
				}
			}
		
		//get reverse order of password
		int[] arrReverseOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			arrReverseOrderInt[arrOrderInt[i]] = i;
		}		
		/*
		System.out.print("\n"+"Decode Transpositionsverfahren: "+passwort+" - ");
		for(int i=0; i<arrReverseOrderInt.length; i++) {System.out.print(arrReverseOrderInt[i]+" ");}
		System.out.println("\n");
		*/
		
		//transpose code text 
		codetext = klartext;
		klartext = "";
		j=0;
		int k=0;
		q = "";
		//int l=passwort.length();
		for(int i = 0;i<codetext.length();i++) {

			//transpose letter
			//..
			c = codetext.charAt(k * passwort.length() + arrReverseOrderInt[j]);
			//System.out.println(i+" "+c+" "+k+" "+arrReverseOrderInt[j]); 	
			q = Character.toString(c);
			
			// write secret message
			klartext = klartext + c;
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;k=k+1;}
		}
		//System.out.println("\n"+"Plain Message Transpositionsverfahren: " + klartext+"\n");	
				
		
		// cut off klartext extension 
		for(int i = klartext.length()-1;i>(klartext.length()-passwort.length());i--) {
			c = klartext.charAt(i);
			q = Character.toString(c);
			if (q.equals("X")) {								// check place holder
				// cut klartext
				//System.out.println(klartext+" cut "+q);	
				klartext = klartext.substring(0, klartext.length() - 1);
			}
		}
		//System.out.println("\n"+"Plain Message Transpositionsverfahren: " + klartext+"\n");	
	
		
		//output secret message
		//callInputBox(klartext);
		klartext = JOptionPane.showInputDialog("Die entschluesselte Nachricht lautet ", klartext);	
		
		System.exit(0);	
	}
   
	/*
	public static void callInputBox(String text1){
		String text2 = JOptionPane.showInputDialog("Die Nachricht lautet", text1);
	}
	*/
}