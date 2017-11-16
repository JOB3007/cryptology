/*
 * Java program to encrypt message
 */
import javax.swing.JOptionPane;
//import java.util.*;

public class JBEncrypt {  
	public static void main(String[] args) {

		//input plain text
		String klartext = "KAESEBROETCHEN";
		do {
			klartext = JOptionPane.showInputDialog("Geben Sie eine Nachricht ein", klartext);	
		} while (klartext.matches(".*[^a-zA-Z].*")); //wiederhole solange Buchstaben außer a-zA-Z gefunden werden

		//input password
		String passwort = "CODE";
		do {
			passwort = JOptionPane.showInputDialog("Geben Sie ein Passwort ein", passwort);	
		} while (passwort.matches(".*[^a-zA-Z].*")); //wiederhole solange Buchstaben außer a-zA-Z gefunden werden

		
		//extend klartext as a multiple of password
		int t=klartext.length()/passwort.length();
		int r=klartext.length()-t*passwort.length();
		//System.out.println(klartext.length()+" / "+passwort.length()+" = "+t+" Rest "+r+"\n");		
		if (r>0){
			for(int i = 0;i<(passwort.length()-r);i++) {
				klartext = klartext + "X";
			}
		}
		
		
		/* 
		Verschlüsselung nach Transpositionsverfahren
        ============================================
		*/
		//get order of password
		int[] arrOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			for(int j=0; j<passwort.length(); j++) {
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
		/*	
		System.out.print("\n"+"Encode Transpositionsverfahren: "+passwort+" - ");
		for(int i=0; i<arrOrderInt.length; i++) {System.out.print(arrOrderInt[i]+" ");}
		System.out.println("\n");
		*/
		
		//transpose plain text 
		int a=0;
		char c;
		String codetext = "";
		int j=0;
		int k=0;
		String q = "";
		//int l=passwort.length();
		for(int i = 0;i<klartext.length();i++) {

			//transpose letter
			c = klartext.charAt(k * passwort.length() + arrOrderInt[j]);
			//System.out.println(i+" "+c+" "+k+" "+arrOrderInt[j]); 	
			q = Character.toString(c);
			
			// write secret message
			codetext = codetext + c;
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;k=k+1;}
		}
		//System.out.println("\n"+"Secret Message Transpositionsverfahren: " + codetext+"\n");	
				
		
		/* 
		Verschlüsselung nach Substitutionsverfahren
		===========================================
		*/
		//get markup of password for substitution
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
		System.out.print("Encode Substitutionsverfahren: "+passwort+" - ");
		for(int i=0; i<arrMarkupInt.length; i++) {System.out.print(arrMarkupInt[i]+" ");}
		System.out.println("\n");
		*/		
		
		//substitute plain text
		a=0;
		klartext = codetext;
		codetext = "";
		j=0;
		for(int i = 0;i<klartext.length();i++) {
			c = klartext.charAt(i);
			q = Character.toString(klartext.charAt(i));

			// Nummer im Alphabet
			if (q.matches("[A-Z]")) {				
				a = ((int) c)-64;
			}
			else {
				a = ((int) c)-96;
			}
			//System.out.println("i: "+i+" "+a);
			
			//substitute letter
			if(a + arrMarkupInt[j]>26) {
				a = a + arrMarkupInt[j]-26;		// Addition
				} else {
				a = a + arrMarkupInt[j];
				}
			c = (char) (a+64);
			
			// write secret message
			codetext = codetext + c;
			//System.out.println(klartext.charAt(i)+" + "+passwort.charAt(j)+" = " +c);
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;}
		}
		//System.out.println("\n"+"Secret Message nach Substitutionsverfahren: " + codetext+"\n");	

		//output secret message
		//callInputBox(codetext);
		codetext = JOptionPane.showInputDialog("Die verschluesselte Nachricht lautet ", codetext);	
		
		System.exit(0);	
	}
   
	/*
	
	public static void callInputBox(String text1){
		String text2 = JOptionPane.showInputDialog("Die Nachricht lautet", text1);
	}
	*/
	
}