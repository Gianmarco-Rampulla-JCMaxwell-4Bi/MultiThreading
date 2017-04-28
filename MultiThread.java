/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */
package multithread;

import java.util.concurrent.TimeUnit;
import java.util.Random;
/**
 *
 * @author Matteo Palitto, Gianmarco Rampulla
 */
public class MultiThread {

    /**
     * @param args the command line arguments
     */
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    
    //dichiaro variabile statica per il punteggio	
    public static int punteggio = 0;
	
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
      
        // Creo i THREAD
        Thread tic = new Thread (new TicTacToe("TIC"));
	Thread tac = new Thread(new TicTacToe("TAC"));
       	Thread toe = new Thread(new TicTacToe("TOE"));
	
	
	// faccio partire i thread
        tic.start();
	tac.start();
	toe.start();  
        
	try {
	    //aspetta la fine di ogni thread per continuare con l'esecuzione di quello principale		
            tic.join();
            tac.join();
            toe.join();
            
	     //stampa il messaggio solo se terminano
             System.out.println("Thread Terminati!");
                     
        } catch (InterruptedException ex) {
            System.out.println("Thread Interrotti!"); //se vengono interrotti (non fiscono il lavoro) viene scritto
        }
        
	 System.out.println("Punteggio: " + punteggio); //stampa il punteggio   
        
        
        long end = System.currentTimeMillis();
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    
     private static boolean isTacAlreadyHere = false; //variabile per capire se il thread precedente e di tipo TAC		
	
    // non essesndo "static" c'e' una copia delle seguenti variabili per ogni THREAD 
    private String t;
    private String msg;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s) {
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
	   
	long  RandomTime = (long) ((long) 100 + (Math.random() * 200)); //genero tempo casuale
	    
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> ";
            //System.out.print(msg);
            
            try {
                TimeUnit.MILLISECONDS.sleep(RandomTime); //aspetta tempo casuale
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
		
		//parte di codice per aggiornare il punteggio quando dopo il tac appare toc
		switch(t) //controllo il nome
            	{
                	case "TAC": //se e' TAC
                 		isTacAlreadyHere = true; //imposto a true var isTacAlreadyHere
                    		break; //fine
                	case "TOE": //se e' TOE
                    		if(isTacAlreadyHere == true) //se il thread precedente era di tipo TAC
                    		{
                         		isTacAlreadyHere = false; //azzera la var
                         		MultiThread.punteggio += 1; //aggiorna punteggio
                        
                    		}
                    		break; //fine
                	case "TIC": //Se e' TIC
                     		isTacAlreadyHere = false; //azzera variabile
                     		break;//fine
            	}
                
		
            msg += t + ": " + i;
            System.out.println(msg);
         
        }
    }
    
}
