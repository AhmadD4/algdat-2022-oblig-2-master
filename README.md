# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Navn Navnesen, S123456, s123456@oslomet.no
* ...

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Per har hatt hovedansvar for oppgave 1, 3, og 5. 
* Else har hatt hovedansvar for oppgave 2, 4, og 6. 
* Fatima har hatt hovedansvar for oppgave 7 og 8. 
* Vi har i fellesskap løst oppgave 10. 

# Oppgavebeskrivelse

I oppgave 1 Først nullstilte DobbeltLenketListe() metoden slik at testet kjøres riktig. Så skal den kjekke om listen er tom, har bare en verdi eller har flere og skal kjøres riktig for hode og hale, neste og forrige, og skal tas med bare som er ikke tomme vedier på listen...

I oppgave 2 Her blir leggInn metoden kjørt, den skal legge ny verdi til listen. Først sjekker at verdien som legges inn ikke er tom, så skal den sjekkes antall verdier til listen, om det er tom liste eller eksisterte noe verdier i listen. I tom listen skal både hode og hale peker til den nye veriden. Hvis det eksisterte noe verdier før skal hale endres. Antall og endriner endres.

I oppgave 3, 3a) laget ny finneNode metode, den skal finne posisjonen til en gitt verdi. Så hent-metoden, den skal finne verdien til en gitt indeks, og skal sjekkes indeksen ved hjelp av Liste. Så oppdater-metoden, den skal erstatte en verdi med en gitt nyverdi på en gitt indeks.
I 3b) Det ble tatt fratilKontroll-metoden fra Kompendiet og brukes på subListe-metode, for å sjekke fra og til. I subListe-metode returneres listen fra en gitt til en gitt intervall, ved hjelp både finnNode og leggInn- metoder. Tomt intervallet er lovlige...

I oppgave 4 Fikset metoden indeksTil som returnerer indeksen til den gitt verdien. ellers returnerer metoden -1. Lagt til metoden inneholder og sjekket om at alt er riktig i testen.

I oppgave 5 Her leggInn() metoden skal tas i mot en oppgitt indeks og en oppgitt verdi og skal legge den nye verdien på den gitte indeksen. Den skal først sjekkes verdi er (null) og indeksen er negativ eller større enn listens antall, og skal gi feilmelding. Så skal den går mot 4 tilfeller, at listen er tom så skal verdien like hode og hale. at verdien legges først, skal hode like verdien. at verdien legges bakerst, skal hale lik verdien. og at verdien legges mellom to andre verdier, så skal pekerne til de to nodene peker riktig på den nye noden. Og antall og endringer øker...

I oppgave 6, bruker vi to metoder. _fejern(indeks)_ Som tar imot en indeks og skal fjerne noden(verdien) og returnere verdien samtidig.
og metoden boolean _fjern(verdi)_ som sjekker om at verdien i tabellen blir slettet og returnerer true dersom det skjer, og returnerer fals ellers.
I fjern(indeks) hadde vi laget en variabel som tar inn indeksen og finner noden ved hjelp av finnNode(verdien) metoden som finner verdien til noden.
I løpet av hele koden sørget vi for å sjekke om at noden som skal slettes kommer først, midt- eller bakerst i tabllen.
på slutten plusser vi antall endringer og tar en minus fra antall elementene i tabellen. Veriden til _gittIndeks_ blir returnert til slutt. også får vi true.

I oppgave 7, Her brukte jeg først første måte, som det sier på oppgaven at den skal begynne fra hode og går videre ved hjelp av pekeren neste. den ble kjørt på 22ms tid. Så fikk vi 19 ms på tiden på andre måte, da vi kallet fjern(0) metode. Mindre enn 5 ms fra den første metode, vanlig nullstille...

I oppgave 8 ...

I oppgave 9 ...

I oppgave 100 ...

