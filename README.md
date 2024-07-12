🎟️ Event Management Backend API 🎟️
Benvenuti nel backend del progetto di gestione eventi! Questo progetto fornisce un'API per la gestione di eventi, artisti, categorie e utenti. Utilizza Spring Boot per il backend e si integra con Cloudinary per la gestione delle immagini.

🚀 Tecnologie Utilizzate
Spring Boot: Framework principale per il backend.
Spring Security: Per la gestione dell'autenticazione e autorizzazione.
Cloudinary: Per la gestione e il caricamento delle immagini.
ModelMapper: Per la mappatura degli oggetti.
PostgreSQL: Database relazionale utilizzato.
JWT (JSON Web Tokens): Per l'autenticazione basata su token.
📦 Struttura del Progetto
Controller: Gestisce le richieste HTTP.
Service: Contiene la logica di business.
Repository: Interagisce con il database.
Model: Contiene le entità del database.
📚 Endpoint Principali
User
POST /users: Registra un nuovo utente.
POST /users/login: Effettua il login di un utente.
POST /users/{username}/avatar: Carica un avatar per un utente.
Event
GET /api/events: Recupera tutti gli eventi.
POST /api/events: Crea un nuovo evento.
GET /api/events/{id}: Recupera un evento per ID.
PUT /api/events/{id}: Aggiorna un evento.
DELETE /api/events/{id}: Elimina un evento.
Category
GET /api/categories: Recupera tutte le categorie.
POST /api/categories: Crea una nuova categoria.
GET /api/categories/{id}: Recupera una categoria per ID.
PUT /api/categories/{id}: Aggiorna una categoria.
DELETE /api/categories/{id}: Elimina una categoria.
🛠️ Configurazione
Prerequisiti
Java 17
Maven
PostgreSQL
Installazione
Clonare la repository:

git clone https://github.com/tuo-username/event-management-backend.git
cd event-management-backend
Configurare il database in application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/tuo_database
spring.datasource.username=tuo_username
spring.datasource.password=tuo_password
Configurare Cloudinary in application.properties:


cloudinary.cloud_name=tuo_cloud_name
cloudinary.api_key=tuo_api_key
cloudinary.api_secret=tuo_api_secret
Avviare l'applicazione:
mvn spring-boot:run


🛡️ Sicurezza
Questo progetto utilizza JWT per l'autenticazione. Assicurati di configurare correttamente le chiavi segrete e di proteggere gli endpoint sensibili.

Link repository front-end: https://github.com/Cristur/LoveTicketFE.git
