
![Stable](https://img.shields.io/badge/Fascicolo_del_Cittadino-Active-brightgreen)
![Stable](https://img.shields.io/badge/Versione_1.25.3-Active-brightgreen)

<h1>Fascicolo del Cittadino del Comune di Genova</h1>



**Applicazione per la gestione dei servizi e delle pratiche amministrative comunali.**

---
# Il progetto

Il **Fascicolo del Cittadino** è una piattaforma digitale che consente una nuova forma di interazione tra il cittadino e la Pubblica Amministrazione, basata sulla disponibilità di un nuovo parco di strumenti, il cui accesso avviene attraverso un unico punto, e che risulta fruibile online, in modalità multicanale.
Permette al cittadino di fruire non solo dei servizi resi disponibili dalla Pubblica Amministrazione, quali ad esempio la richiesta di certificati e l'aggiornamento dell'anagrafica, ma anche di ricevere notifiche ufficiali e di pagare tasse o canoni online. Il sistema mira a ridurre i tempi di attesa e a migliorare l'efficienza dei processi burocratici. Il tutto garantendo la sicurezza dei dati. Inoltre, favorisce l'interoperabilità con altre piattaforme della pubblica amministrazione, ottimizzando lo scambio di informazioni tra enti.
I cittadini beneficiano di maggiore trasparenza, accesso più rapido ai servizi e riduzione degli spostamenti fisici, mentre gli enti pubblici traguardano una gestione più snella dei dati e una erogazione più fluida dei servizi.
Il Fascicolo del Cittadino rappresenta quindi un elemento portante verso la digitalizzazione dei servizi pubblici e verso l'innovazione amministrativa.


## Struttura del repository
- **logo-fdc.png** Il logo del progetto
- **LICENSE** il file della licenza EUPL-2.0
- **publiccode.yml** il file di documentazione Open Source 
- **database** contiene i file.sql per creare il database Oracle
- **frontend & backend** contengono rispettivamente per ognuno:
  - Il codice sorgente
  - I file di build di **ant**
  - La cartella **antlib** con dentro i jar (Java ARchive) utili al funzionamento dell'applicazione

---

## Tecnologie, Prerequisiti e Dipendenze

- **Java** è il linguaggio usato nel progetto, insieme a **Wicket**, un framework open-source per lo sviluppo di applicazioni web in Java, che adotta un approccio basato su componenti e orientato agli oggetti.

Per eseguire o sviluppare il progetto, assicurati di avere i seguenti prerequisiti:

- **Sistema operativo**: Linux
- **Framework**: [Apache Wicket](https://wicket.apache.org/)
- **Linguaggio**: Java
- **Database**: Oracle
- **Server**: Wildfly 12

---

## Istruzioni per l'installazione

### 1. Clona la repository

Clona il repository sul tuo computer locale con il comando:

`git clone https://github.com/ComuneDiGenova/fascicolo-del-cittadino.git`  
`cd fascicolo-del-cittadino`

### 2. Installa il tool **Ant**

Puoi farlo con:

`sudo apt-get install ant`

### 3. Come importare il progetto su **Eclipse**

1. Apri **Eclipse**.
2. Seleziona **File > Import**.
3. Scegli **General > Existing Projects into Workspace**.
4. Seleziona la cartella del progetto e clicca su **Finish**.

### 4. Build del progetto con **Ant**

Una volta installato Ant, esegui la build del progetto con:

Nel file **buildfiles/path** inserire il path della cartella standalone/deployments del proprio Server Wildfly

Creare un nuovo **"External tool configuration"** con il file **build_7.xml** e selezionare il Target Ant **deployLocale**

### 4. Avvia l'applicazione

Dopo aver importato il progetto in Eclipse, puoi eseguire l'applicazione direttamente da lì, importando il server locale. Assicurati che le dipendenze (come Java e Wicket) siano correttamente configurate.

---

## Contatti

- **Amministrazione proprietaria** : Comune di Genova
- **Contatto e-mail referente di progetto (per segnalazioni e richiesta informazioni)** : sales-el@liguriadigitale.it
  
---

## Licenza

Questo progetto è distribuito sotto la **EUPL © Unione europea 2007, 2016**. Vedi il file LICENSE per maggiori dettagli.

## Licenze esterne 

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)

[Jboss License](https://docs.jboss.org/jbossas/admindevel326/html/apa.html)

[Oracle JDK 8](https://openjdk.org/legal/gplv2+ce.html)

[Bootstrap MIT](https://raw.githubusercontent.com/twbs/bootstrap/refs/tags/v4.0.0/LICENSE)