Draai je app op Google Cloud

Om deze of een andere app op Google Cloud te draaien doe je het volgende:
1. Maak een account aan op https://cloud.google.com/. Je moet daarvoor wel 
betaalgegevens (creditcard) ingeven. Je krijgt een budget voor 300 euro gratis.

2. gcloud SDK op je laptop installeren
Hiermee kun je lokaal de instellingen op je cloud environment instellen.
Op de https://cloud.google.com/ site vind je meer info, of zoek in Google.

3. Maak een nieuw project. Als je een account hebt doe je dit online.
Let op: je project moet in US-central draaien; nergens anders. Anders kun je
je app later niet deployen (we maken gebruik van beta versies van GCloud; sept 2016).

Op je lokale machine:
4. pom.xml aanpassen zodat hij google engine herkent. Zie Spring Bieb voorbeeld.
5. Juiste mappen aanmaken en app.yaml toevoegen in de juiste map. Zie Spring Bieb voor locatie en inhoud.
6. Zet je Dockerfile in de map [project]/dockerfile.
Hierin hoef je geen rekening te houden met compileren en downloaden van benodigde
software, wat je normaal bij een dockerfile wel moet doen. Alleen de juiste 
OS image, een goede naam voor je app, en de startup commands. Zie voorbeeld Bieb.

7. Vanuit een command line venster:
mvn clean install appengine:deploy

Hiermee installeer je je app.jar bestand en het Dockerfile op Google Cloud. Kijk goed naar evt. foutmeldingen.


Het draaien van je app in de cloud vraagt om het instellen van verschillende profiles 
in Spring. Je hebt nl. settings nodig voor de cloud-omgeving die waarschijnlijk afwijken
van je localhost omgeving - zoals database host en portnr. Zie daarvoor ook de Bieb app, 
en de verschillende resources/application-[].profile bestanden.
