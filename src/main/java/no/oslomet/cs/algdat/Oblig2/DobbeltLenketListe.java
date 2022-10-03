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
        if (a.length>0){
            int i;
            for (i = 0; i<a.length;i++){
                if (a[i] != null){
                    //Setter hode peker til den første...
                    hode = new Node<>(a[i]);
                    antall ++;
                    break;
                }
            }
            //hode og hale skal være like hvis listen har bare en eller null verdier...
            hale = hode;
            if (hode != null && a.length > 1){
                i++;
                for (;i<a.length;i++){
                    if (a[i] != null){
                        //Setter hale peker til den siste
                        hale.neste = new Node<>(a[i], hale, null);
                        //Setter neste også forrige riktige...
                        hale = hale.neste;
                        antall ++;
                    }
                }
            }
        }

    }

    //fraTilKontroll-metode hentet fra Kompendiet programkode 1.2.3 a)
    private void fratilKontroll(int antall, int fra, int til)
    {
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
        fratilKontroll(antall,fra,til);//sjekke fra og til...
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();//liste av en instans
                                                        // av klassen DobbeltLenketListe
        int lengde = til - fra;//lengden av liste, ingen (liste.lengde)...
        if (lengde <1) return liste;//tomt intervall er lovlig...
        Node<T> current = finnNode(fra);
        //Liste ut listen ved hjelp av leggInn-metoden...
        while (lengde >0){
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
        if (antall == 0){
            //her skal både hode og hale peker på den nye node...
            hode = enNode;
            hale = enNode;
            antall++;
            endringer++;
            return true;
        }
        //tilfelle 2) at listen ikke er tom...
        else{
            //her skal hale endre seg etter innleggingen...
            enNode.forrige=hale;//setter nodens forrige peker til hale...
            hale.neste=enNode;//setter hales neste peker til noden...
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
        if (indeks < 0 || indeks > antall){//sjekker hvis indeksen er negativ eller større enn antall noder i liste...
            throw new IndexOutOfBoundsException();
        }
        else{
            if (antall == 0){//tilfelle 1) at listen er tom...
                hode = hale = new Node<T>(verdi,null, null);
            }
            else if(indeks == 0){//tilfelle 2) at verdien skal legges først...
                hode = new Node<T>(verdi,null,hode);
                hode.neste.forrige=hode;//bytter hode sin plass til noden...
            }
            else if (indeks == antall){//tilfelle 3) at verdien skal legges bakerst...
                hale = new Node<T>(verdi,hale,null);
                hale.forrige.neste = hale;//bytter hale sin plass til noden...
            }
            else {//tilfelle 4) at verdien skal legges mellom to andre verdier i listen...
                Node<T> node = hode;

                for (int i = 0; i < indeks; i++) node = node.neste; {
                    node = new Node<T>(verdi, node.forrige, node);
                }
                node.neste.forrige = node.forrige.neste = node;
            }
            antall ++;
            endringer++;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        if (indeksTil(verdi) != -1) return true;
        else return false;
    }

    private Node<T> finnNode(int indeks){//Lages ny metode...
        Node<T> enNode;
        //sjekker indeksen for å bestemme hvordan skal den finnes...
        if (indeks < antall/2){
            enNode = hode;//starter fra hode og går mot høyre...
            for (int i = 0; i<indeks;i++){
                enNode = enNode.neste;
            }
            return enNode;
        }
        else{
            enNode = hale;//starter fra hale og går mot venstre...
            for (int i = antall-1;i>indeks;i-- ){
                enNode = enNode.forrige;
            }
            return enNode;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);//sjekkes indeksen ved hjelp av
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
        }
        else{
            return -1;
        }

    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        indeksKontroll(indeks,false);//sjekkes indeksen før oppdatering...
        Node<T> enNode = finnNode(indeks);
        T verdi = enNode.verdi;//den gamle verdi skal erstattes med den nyeverdi
        enNode.verdi = nyverdi;
        endringer++;
        return verdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
            Node<T> current = hode;
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            if (tom()) {
                sb.append("]");
                return sb.toString();
            } else {
                sb.append(current.verdi);
                current = current.neste;
                while (current != null) {
                    sb.append(", ");
                    sb.append(current.verdi);
                    current = current.neste;
                }
            }
            sb.append("]");

            return sb.toString();
    }

    public String omvendtString() {
            Node<T> current = hale;
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            if (tom()) {
                sb.append("]");
                return sb.toString();
            } else {
                sb.append(current.verdi);
                current = current.forrige;
                while (current != null) {
                    sb.append(", ");
                    sb.append(current.verdi);
                    current = current.forrige;
                }
            }
            sb.append("]");

            return sb.toString();
        }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


