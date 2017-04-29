# TicTacToe

**TicTacToe** è un programma composto da **3 threads** (TIC,TAC,TOE) che vengono fatti partire **quasi contemporaneamente** per stampare un countdown, partendo da 10, **in concorrenza.** 
Per stampare ogni numero attendono un tempo casuale da 100 e 300 millisecondi in modo da variare l'ordine di stampa dei thread ad ogni run del programma.
Alla fine viene **stampato un punteggio** che rappresenta il conteggio di quante volte è uscito **TOE dopo di TAC**. 
il punteggio viene stampato, però, quando **tutti i thread hanno concluso l'esecuzione.**

# Istruzioni per testare il programma
 - Scaricare il progetto da GitHub cliccando sul tasto "Clone/Download",
 - Importare il file "MultiThread.java" in NetBeans o in un altro IDE 
 - Modificare il package (se da errore nella dichiarazione del package)
 - Cliccare su Esegui
 
# Istruzioni per la modifica del codice

Il codice è composto da una classe **MultiThread** che contiene **il main** e che **gestisce l'esecuzione dei thread lanciati** e da una classe **TicTacToe** che **rappresenta il thread che esegue il countdown**

Si può modificare ad esempio **il tempo di stampa random** nel codice (contenuto nel **run()** della classe TicTacToe) cambiando la seguente riga in questo modo

- long  RandomTime = (long) ((long) "inserire numero da cui partire" + (Math.random() * "inserire numero massimo"));

Dove sia **"inserire numero da cui partire"** che **  "inserire numero massimo"** possono essere o una **variabile** o un valore già definito

Il tempo viene espresso in millisecondi, quindi **1 secondo** sarà **1000 millisecondi.**

si possono inoltre aggiungere altri **thread al main** per farli eseguire semplicemente dichiarando **una nuova istanza della classe TicTacToe** nel main all'interno della **classe Multithread**

Il costruttore TicTacToe richiede solo, **come parametro**, il nome da stampare al momento dell'output del numero

**Esempio:** TicTacToe ThreadGitHub = new TicTacToe("nomeThread");

# Autore

- Gianmarco Rampulla

# Ringraziamenti

- Al prof per il codice iniziale
