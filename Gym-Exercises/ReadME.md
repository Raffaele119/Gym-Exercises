Gym-Exercises

Istruzioni su come usare il progetto 

per utilizzare il progetto bisogna avviare il progetto con maven e fare la build inserendo come goal il servlet jetty comando: jetty:run una volta avviato il web server digitare sul browser il seguente indirizzo: http://localhost:8080/api/exercises 
e potremmo visualizzare una pagina in formato Json con tutti gli esercizi disponibili nel DataBase 
da linea di comando possiamo utilizzare i metodi http per aggiungere un esercizio, modifcare un esercizio, rimuovere, selezionare tramite id. 
Per l’elenco di tutti gli esercizi: curl -X GET http://localhost:8080/api/exercises 

Ottenere un esercizio tramite id: curl -X GET http://localhost:8080/api/exercises/{id} 

Aggiungere un esercizio: 
 curl -X POST http://localhost:8080/api/exercises \ 
     -H "Content-Type: application/json" \ 
     -d '{"name": "Deadlift", "muscleGroup": "Back", "description": "descrizione esercizio"}' 
     
Aggiornare un sesercizio: 
curl -X PUT http://localhost:8080/api/exercises/{id} \ 
     -H "Content-Type: application/json" \ 
     -d '{"name": "Deadlift", "muscleGroup": "Back", "description": "descrizione esercizio"}' 
     
Eliminare un esercizio: curl -X DELETE http://localhost:8080/api/exercises/{id} 
