# TicTacToe

TicTacToe è un programma basato sul concetto dei threads.
è composto da 3 threads (TIC,TAC,TOE) che vengono fatti partire contemporaneamente per stampare numeri (countdown partendo da 10) in concorrenza attendendo un tempo casuale da 100 e 300 millisecondi ad ogni numero avendo cosi risultati diversi ad ogni run del programma.
Alla fine viene stampato un punteggio che rappresenta il conteggio di quante volte è uscito TOE dopo di TAC. 
il punteggio viene stampato, però, quando tutti i thread hanno concluso l'esecuzione.

# Istruzioni per testare il programma
 - Scaricare il progetto da GitHub,
 - Importarlo in NetBeans o in un altro IDE 
 - Modificare il package (se da errore nella dichiarazione del package)
 - Cliccare su esegui
 
# Istruzioni per la modifica del codice

Il codice è composto da una classe MultiThread che contiene il main e che gestisce l'esecuzione dei thread lanciati e da una classe TicTacToe che rappresenta il thread

Si può modificare ad esempio il tempo random nel codice (contenuto nel run della classe TicTacToe) cambiando la seguente riga in questo modo

- long  RandomTime = (long) ((long) "inserire numero da cui partire" + (Math.random() * "inserire numero massimo")); 

Il tempo viene espresso in millisecondi, quindi 1 secondo sarà 1000 millisecondi.

si possono inoltre aggiungere altri thread al main per farli eseguire semplicemente dichiarando una nuova istanza della classe TicTacToe
