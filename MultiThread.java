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
	
    public static void main(String[] args) {
       
	MonitoredOperations Mon = new MonitoredOperations(); //istanzia un oggetto della classe monitor per la gestione degli accessi dei thread alle risorse condivise e per passarlo poi al costruttore, in quanto per regolare gli accessi tutti i thread creati devono essere gestiti dallo stesso oggetto monitor           
        System.out.println("Main Thread iniziata..."); //stampo messaggio
        long start = System.currentTimeMillis(); //ottengo i millisecondi correnti
      
        // Creo i THREAD e passo sia il nome che il riferimento al Monitor per evitare che eseguano tutti insieme la procedura di countdown
        Thread tic = new Thread (new TicTacToe("TIC", Mon));
        tic.start(); // faccio partire il thread
	Thread tac = new Thread(new TicTacToe("TAC", Mon));
	tac.start();    // faccio partire il thread
       	Thread toe = new Thread(new TicTacToe("TOE", Mon));
	toe.start();  // faccio partire il thread
	
	
	
        
        
	try {
	    //aspetta la fine di ogni thread per continuare con l'esecuzione di quello principale		
            tic.join();
            tac.join();
            toe.join();
            
	     //stampa il messaggio solo se terminano
             System.out.println("Thread Terminati!");
                     
        } catch (InterruptedException ex) {
            System.out.println("Thread Interrotto!"); //se il thread da cui si e' invocata la proc join viene interrotto viene scritto
        }
        
	 System.out.println("Punteggio: " + Mon.getPoints()); //stampa il punteggio   
        
        
        long end = System.currentTimeMillis(); //ottengo i millisecondi correnti
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms"); //stampo quanto tempo ci è voluto per eseguire il programma
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    
      
      private  MonitoredOperations op; //variabile della classe Monitor per gestire l'accesso ai thread	al metodo per il countdown	
	
    // non essendo "static" c'e' una copia delle seguenti variabili per ogni THREAD 
    private String t;
    private String msg;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s, MonitoredOperations op2) { //passiamo il nome da stampare nel countdown e l'istanza del monitor in modo da controllare gli accessi del thread creato
        this.t = s;
	this.op = op2;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
	   
	 for (int i = 10; i > 0; i--) { //ciclo for per il countdown
             msg = "<" + t + "> " + t + ":" + i; //creo il messaggio gia' qui in quanto se lo facessi nel metodo CountDown bloccherei l'accesso al metodo agli altri thread
             op.CountDown(msg, t); //invoco il metodo sincronizzato nel thread corrente, se è libero esegue le operazioni se no aspetta il suo turno (assegnazione lock)
            
        }
    }
    
}

class MonitoredOperations{
    
    
    private boolean isTacAlreadyHere = false; //variabile per capire se il thread precedente è di tipo TAC	
  
    private int punteggio = 0; //variabile per il punteggio 
    
  


    public int getPoints() //ritorna il punteggio, prima era gestito da una variabile statica, ma adesso con synchronized lo si mette come attributo della classe che verrà modificato dai thread in modo sequenziale e non contemporaneamente (in quanto causa di conflitti)
    {
        return punteggio; //ritorno il valore della variabile sopra
    }
    
    public synchronized void CountDown(String msg, String t) //metodo che solo un thread alla volta può eseguire
    {
         long  RandomTime = (long) ((long) 100 + (Math.random() * 300)); //genero tempo casuale da 100 a 300
           
		
		//parte di codice per aggiornare il punteggio quando dopo il tac appare toc
		switch(t) //controllo il nome
            	{
                	case "TAC": //se è TAC
                 		isTacAlreadyHere = true; //imposto a true var isTacAlreadyHere
                    		break; //fine
                	case "TOE": //se è TOE
                    		if(isTacAlreadyHere == true) //se il thread precedente era di tipo TAC
                    		{
                         		isTacAlreadyHere = false; //azzera la var
                         		punteggio += 1; //aggiorna punteggio
                                        msg += " Punteggio +1"; //aggiungo stringa per segnalare l'aumento del punteggio
                        
                    		}
                    		break; //fine
                	case "TIC": //Se è TIC
                     		isTacAlreadyHere = false; //azzera variabile
                     		break;//fine
            	}
                
                     try {
                TimeUnit.MILLISECONDS.sleep(RandomTime); //aspetta tempo casuale
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
        
            System.out.println(msg); //stampa il numero
		
         
        
    }
}
