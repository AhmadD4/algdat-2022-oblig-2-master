package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //Når vi kjører en test så får vi feilmelding på grunn av disse
        // variablene var ikke definerte på DobbeltLenketListe metode!
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        //Kastes en NullPointerException
        Objects.requireNonNull(a, "Tabellen a er null!");
        //Går gjennøm liste og ta med bare verdiene som ikke er tomme...
        if (a.length > 0) {
            int i;
            for (i = 0; i < a.length; i++) {
                if (a[i] != null) {
                    //Setter hode peker til den første...
                    hode = new Node<>(a[i]);
                    antall++;
                    break;
                }
            }
            //hode og hale skal være like hvis listen har bare en eller null verdier...
            hale = hode;
            if (hode != null && a.length > 1) {
                i++;
                for (; i < a.length; i++) {
                    if (a[i] != null) {
                        //Setter hale peker til den siste
                        hale.neste = new Node<>(a[i], hale, null);
                        //Setter neste også forrige riktige...
                        hale = hale.neste;
                        antall++;
                    }
                }
            }
        }

    }

    //fraTilKontroll-metode hentet fra Kompendiet programkode 1.2.3 a)
    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);//sjekke fra og til...
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();//liste av en instans
        // av klassen DobbeltLenketListe
        int lengde = til - fra;//lengden av liste, ingen (liste.lengde)...
        if (lengde < 1) return liste;//tomt intervall er lovlig...
        Node<T> current = finnNode(fra);
        //Liste ut listen ved hjelp av leggInn-metoden...
        while (lengde > 0) {
            liste.leggInn(current.verdi);
            current = current.neste;
            lengde--;
        }
        return liste;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if (antall == 0) return true;
        else return false;
    }

    @Override
    public boolean leggInn(T verdi) {
        //Nullverdier er ikke tillatt...
        Objects.requireNonNull(verdi);
        //en ny node med oppgitt verdi...
        Node<T> enNode = new Node<>(verdi);
        //tilfelle 1) at listen er tom...
        if (antall == 0) {
            //her skal både hode og hale peker på den nye node...
            hode = enNode;
            hale = enNode;
            antall++;
            endringer++;
            return true;
        }
        //tilfelle 2) at listen ikke er tom...
        else {
            //her skal hale endre seg etter innleggingen...
            enNode.forrige = hale;//setter nodens forrige peker til hale...
            hale.neste = enNode;//setter hales neste peker til noden...
            hale = enNode;//og verdiene fra forrige og neste noden i
            // halen halen verdiene lik den nye noden...
            antall++;
            endringer++;
            return true;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi);//sjekker verdien...
        if (indeks < 0 || indeks > antall) {//sjekker hvis indeksen er negativ eller større enn antall noder i liste...
            throw new IndexOutOfBoundsException();
        } else {
            if (antall == 0) {//tilfelle 1) at listen er tom...
                hode = hale = new Node<T>(verdi, null, null);
            } else if (indeks == 0) {//tilfelle 2) at verdien skal legges først...
                hode = new Node<T>(verdi, null, hode);
                hode.neste.forrige = hode;//bytter hode sin plass til noden...
            } else if (indeks == antall) {//tilfelle 3) at verdien skal legges bakerst...
                hale = new Node<T>(verdi, hale, null);
                hale.forrige.neste = hale;//bytter hale sin plass til noden...
            } else {//tilfelle 4) at verdien skal legges mellom to andre verdier i listen...
                Node<T> node = hode;

                for (int i = 0; i < indeks; i++) node = node.neste;
                {
                    node = new Node<T>(verdi, node.forrige, node);
                }
                node.neste.forrige = node.forrige.neste = node;
            }
            antall++;
            endringer++;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        if (indeksTil(verdi) != -1) return true;
        else return false;
    }

    private Node<T> finnNode(int indeks) {//Lages ny metode...
        Node<T> enNode;
        //sjekker indeksen for å bestemme hvordan skal den finnes...
        if (indeks < antall / 2) {
            enNode = hode;//starter fra hode og går mot høyre...
            for (int i = 0; i < indeks; i++) {
                enNode = enNode.neste;
            }
            return enNode;
        } else {
            enNode = hale;//starter fra hale og går mot venstre...
            for (int i = antall - 1; i > indeks; i--) {
                enNode = enNode.forrige;
            }
            return enNode;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);//sjekkes indeksen ved hjelp av
        // indeksKontroll som arves fra Liste...
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi != null) {  //Sjekker at alle noder ikke lik null
            for (int i = 0; i < antall; i++) { // Går inn i løkke og sjekker alle noder
                T sjekk = hent(i); // Kaller på hent-metoden for å sjekke verdien

                if (sjekk.equals(verdi)) return i; // if'n sjekker om verdien er lik den gitt verdi
                // og returnerer indeksen
            }
            // Returnerer -1 dersom verdien er null
            return -1;
        } else {
            return -1;
        }

    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        indeksKontroll(indeks, false);//sjekkes indeksen før oppdatering...
        Node<T> enNode = finnNode(indeks);
        T verdi = enNode.verdi;//den gamle verdi skal erstattes med den nyeverdi
        enNode.verdi = nyverdi;
        endringer++;
        return verdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null)
            return false;
        Node<T> gittIndeks = null;
        Node<T> testNode = hode;
        while (testNode != null) {
            if (testNode.verdi.equals(verdi)) {
                gittIndeks = testNode;
                break;
            } else {
                testNode = testNode.neste;
            }
        }
        if (gittIndeks == null)
            return false;
        gittIndeks.verdi = null;
        if (gittIndeks.forrige != null) {
            gittIndeks.forrige.neste = gittIndeks.neste;
        }
        if (gittIndeks.neste != null) {
            gittIndeks.neste.forrige = gittIndeks.forrige;
        }
        if (gittIndeks == hode && gittIndeks.neste != null) {
            hode = gittIndeks.neste;
        }
        if (gittIndeks == hale && gittIndeks.forrige != null) {
            hale = gittIndeks.forrige;
        }
        antall--;
        endringer++;
        return true;
    }

    //Oppgave6
    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig
        if (indeks < 0 || indeks > antall) {//sjekker hvis indeksen er negativ eller større enn antall noder i liste...
            throw new IndexOutOfBoundsException();
        }
        if (antall == 0) { // Sjekker om tabellen er tom 1) sjekkeliste
            throw new UnsupportedOperationException("Tabellen er tom!");
        }
        Node<T> gittIndeks = finnNode(indeks);
        if (gittIndeks == null)
            return null;
        //tilfelle 1) første skal fjernes
        if (gittIndeks == hode && gittIndeks.neste != null) {
            hode = gittIndeks.neste;
        }
        //tilfelle 2) siste skal fjernes
        if (gittIndeks == hale && gittIndeks.forrige != null) {
            hale = gittIndeks.forrige;
        }
        //tilfelle 3) verdien mellom to andre verdier skal fjernes
        if(gittIndeks.forrige != null){
            gittIndeks.forrige.neste = gittIndeks.neste;

        }
        if (gittIndeks.neste != null){
            gittIndeks.neste.forrige = gittIndeks.forrige;
        }

        antall--;
        endringer++;
        return gittIndeks.verdi;

    }

    @Override
    public void nullstill() {
        Node<T> slett; //legger en node som skal være hode, der vi skal begynne fra
        /*
        //første måte:
        for (slett = hode; slett != null; slett = null){//ved hjelp av for-løkke skal
                                            // slett går gjennom hele listen
                                             // og gjøre dette til (null), så lenge listen ikke tom.
            slett.verdi = null;
            slett.forrige = slett.neste = null;
        }
        //her fikk vi 24 ms på tiden
         */
        //andre måte:
        for (slett = hode; slett != null; slett = null){
            fjern(0);
        }
        //her fikk vi 19 ms på tiden, så er den mer effektiv enn første måte, og er derfor i brukt...
        antall =0;
        endringer ++;
    }

    @Override
    public String toString() {
            Node<T> current = hode;//begynner fra venstre (hode) til hale...
            StringBuilder bygg = new StringBuilder(); //Bygger opp tegnstrengen...
            bygg.append("[");//listen skal alltid begynne fra "["...

            if (tom()) {//kall på tom() metode i tilfelle listen er tom...
                bygg.append("]");
                return bygg.toString(); //metoden skal retunere [] hvis listen er tom
            } else {
                bygg.append(current.verdi);//definerer verdien til oppbygningen for hver node...
                current = current.neste;//går fra hode til hale ved hjelp av neste pekeren...
                while (current != null) {//gjør dette igjen så lenge listen er ikke tom...
                    bygg.append(", ");
                    bygg.append(current.verdi);
                    current = current.neste;
                }
            }
            bygg.append("]");//slutt

            return bygg.toString();
    }

    public String omvendtString() {
        //samme som i toString() metode men i omvendt, altså skal begynne fra sist, hale...
            Node<T> current = hale;// begynner fra halen
            StringBuilder bygg = new StringBuilder();
            bygg.append("[");

            if (tom()) {
                bygg.append("]");
                return bygg.toString();
            } else {
                bygg.append(current.verdi);
                current = current.forrige;//her går gjennom listen ved hjelp av forrige pekeren...
                while (current != null) {
                    bygg.append(", ");
                    bygg.append(current.verdi);
                    current = current.forrige;
                }
            }
            bygg.append("]");

            return bygg.toString();
        }

    @Override
    public Iterator<T> iterator() {
       return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks,false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);//ved hjelp av finnNode() metode, vil den oppgitte indeksen
                                     // hører til pekeren denne
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }  

        @Override
        public boolean hasNext() {

            return denne != null;
        }

        @Override
        // først sjekker vi om iteratorendringer er lik endringer, hvis ikke får vi feilmelding.
        public T next() {
            if(iteratorendringer!=endringer)
                throw new ConcurrentModificationException("iteratorendringer er ikke lik endringer");

            if(!hasNext()) throw new NoSuchElementException(" Ingen verdier");
            // hvis hasNext() ikke er sann, så settes vi fjernOk til sann
            fjernOK = true;
               T tempverdi = denne.verdi;
               denne = denne.neste;

            return tempverdi; // her returneres verdien til denne
        }

        @Override
        public void remove() {
            Node<T> p = (denne == null ? hale : denne.forrige);
            if (iteratorendringer != endringer) throw new
                    ConcurrentModificationException("Listen er endret!"); // Exception som blir kastes dersom endringer og iteratorendringer er forskjellige

            if (!fjernOK) throw
                    new IllegalStateException("Ulovlig kall!"); // Exception som blir sendt dersom det ikke er tillatt å kalle denne metoden

            fjernOK = false;  // hentet fra kmop. 3.2.5 e)

            if (p == hode) {
                if (antall == 1) { // den som skal fjernes er eneste verdi
                    hode = hale = null;
                }   else  { // den første skal fjernes
                    hode = hode.neste; hode.forrige = null;
                }
            }
            else if (p == hale){ hale = hale.forrige; hale.neste = null;}  // fjerner den siste
            else{
                p.forrige.neste = p.neste;
                p.neste.forrige = p.forrige;    // fjerner p
            }

            antall--;            // en mindre i listen
            iteratorendringer++;
            endringer++;         // en endring


        } // class DobbeltLenketListeIterator

    }
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        if (liste.tom()) return;
        for (int i = 0; i < liste.antall(); i++) {
            for (int j = 0; j < liste.antall(); j++) {
                if ((c.compare(liste.hent(i), liste.hent(j))) < 0)
                {
                    T tempVerdi = liste.hent(i);
                    liste.oppdater(i,liste.hent(j));
                    liste.oppdater(j,tempVerdi);
                }
            }
        }
    }

} // class DobbeltLenketListe


