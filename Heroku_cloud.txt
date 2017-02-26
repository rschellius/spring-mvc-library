
Draai je applicatie in de Cloud!

De IVH5 Spring Bibliotheek is een Java applicatie, gebouwd op het Spring Boot framework. Je kunt die applicatie
prima lokaal op je eigen machine runnen, maar het leukste is toch om hem ook online te kunnen draaien. Dat kan!

Er zijn een aantal leveranciers van clouddiensten die het mogelijk maken alles wat je op applicatiegebied zou 
willen runnen 'ergens' op het Internet te draaien. Ergens in een datacentrum, in de Cloud.

Leveranciers zijn bijvoorbeeld:
- CloudFoundry 		https://www.cloudfoundry.org/ 
- Google Cloud 		https://cloud.google.com/
- Heroku			https://www.heroku.com

De meeste diensten zijn gratis voor niet-professionele applicaties, al moet je vaak wel creditcard gegevens invoeren. Een gratis omgeving is wel wat trager - en gaat na 20 minuten in slaapstand - maar is prima voor het draaien van een demo omgeving.

De IVH5 Spring Bieb app draait ook in de cloud. Daarbij gebruiken we twee clouddiensten:
- de applicatie zelf draait op Heroku. Die biedt de gemakkelijkste deploymenttechniek aan.
- de MySql database draait op [JawsDB](https://jawsdb.com/), een 'MaaS' (MySQL-as-a-Service) provider.

Wat nodig is:
- Maak een gratis account op Heroku. Je kunt hier GitHub koppelen als repository.
  Je kunt hier ook instellen dat iedere push naar GitHub automatisch een nieuwe versie van je app start.
- Op Heroku heb je geen vast IP-adres - je app gaat 'in slaap' als hij een half uur niet gebruikt wordt, en kan op een ander adres opnieuw gestart worden.

Je app draait nu dus zowel lokaal in je 'ontwikkelomgeving', als in de cloud in je 'productie omgeving'. Je hebt hiervoor
in Spring twee configuraties nodig. Dat kun je doen door de configuratie in het juiste bestand in de config folder aan te passen.

Om te zorgen dat Heroku van je productie-settings gebruik maakt maak je een 'Procfile' in de root van je project. Je geeft hiervoor een propertie aan Java mee bij het runnen van de jar file.
Kijk in het voorbeeld om te zien hoe dat er uit ziet. Er staat eigenlijk maar één regel in.