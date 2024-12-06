![Stable](https://img.shields.io/badge/Fascicolo_del_Cittadino-Active-brightgreen)
![Stable](https://img.shields.io/badge/Versione_1.25.3-Active-brightgreen)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![Apache Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)
![JSON](https://img.shields.io/badge/json-5E5C5C?style=for-the-badge&logo=json&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)


<div style="display: flex; flex-direction: row; align-items: center;">
    <img align="right" style="max-width: 50px;" src="https://raw.githubusercontent.com/ComuneDiGenova/FascicoloDelCittadino/7ef22ddff4f3841b908cc2da470d06c5cc953730/logo-fdc.png">
</div>
<h1>Fascicolo del Cittadino del Comune di Genova</h1>



**Applicazione per la gestione dei servizi e delle pratiche amministrative comunali.**

---
# Il progetto

Il **Fascicolo del Cittadino** è una piattaforma digitale che centralizza e semplifica la gestione delle informazioni personali e dei servizi pubblici, facilitando l'interazione tra cittadini e pubblica amministrazione. Consente al cittadino di accedere, consultare e aggiornare facilmente i propri dati anagrafici, richiedere certificati, ricevere notifiche ufficiali e pagare tasse o multe online. Il sistema mira a ridurre i tempi di attesa, migliorare l'efficienza dei processi burocratici e garantire la sicurezza dei dati tramite tecnologie avanzate di crittografia. Inoltre, favorisce l'interoperabilità con altre piattaforme della pubblica amministrazione, ottimizzando lo scambio di informazioni tra enti. I benefici includono una maggiore trasparenza, accesso più rapido ai servizi e riduzione degli spostamenti fisici per i cittadini, mentre per gli enti pubblici significa una gestione più snella dei dati e una migliore erogazione dei servizi. Il progetto si sviluppa in fasi, partendo dalla progettazione e test della piattaforma, passando per un avvio pilota, fino al rilascio completo e a continui miglioramenti basati sul feedback degli utenti. Il Fascicolo del Cittadino rappresenta un passo importante verso la digitalizzazione dei servizi pubblici e l'innovazione amministrativa.


## 💻 Struttura del repository
- **logo-fdc.png** Il logo del progetto
- **LICENSE** il file della licenza EUPL-2.0
- **publiccode.yml** il file di documentazione Open Source 
- **database** contiene i file.sql per creare il database Oracle
- **frontend & backend** contengono rispettivamente per ognuno:
  - Il codice sorgente
  - I file di build di **ant**
  - La cartella **antlib** con dentro i jar (Java ARchive) utili al funzionamento dell'applicazione

---

## 🛠 Tecnologie, Prerequisiti e Dipendenze

- **Java** è il linguaggio usato nel progetto, insieme a **Wicket**, un framework open-source per lo sviluppo di applicazioni web in Java, che adotta un approccio basato su componenti e orientato agli oggetti.

Per eseguire o sviluppare il progetto, assicurati di avere i seguenti prerequisiti:

- **Sistema operativo**: Linux
- **Framework**: [Apache Wicket](https://wicket.apache.org/)
- **Linguaggio**: Java
- **Database**: Oracle
- **Server**: Wildfly 12

---

## 💻 Istruzioni per l'installazione

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

## 🏷 Contatti

- **Amminisrazione propietaria** : Comune di Genova
- **Soggetti manutentori** : Liguria Digitale spa
- **Contatto e-mail referente di progetto** (per segnalazioni e richiesta informazioni) : d.stellardo@liguriadigitale.it
- **Contatto e-mail presso l'amministrazione** : p.mancini@liguriadigitale.it
- **Gestione segnalazioni** : tramite issues github
  
---

## 📄 Licenza

Questo progetto è distribuito sotto la **EUPL © Unione europea 2007, 2016**. Vedi il file LICENSE per maggiori dettagli.

## Licenze esterne 

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)

[Jboss License](https://docs.jboss.org/jbossas/admindevel326/html/apa.html)

[Oracle JDK 8](https://openjdk.org/legal/gplv2+ce.html)

[Bootstrap MIT](https://raw.githubusercontent.com/twbs/bootstrap/refs/tags/v4.0.0/LICENSE)
