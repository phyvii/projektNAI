import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class PhoneBookServerNB {
    Map<String, List<String>> log = new LinkedHashMap<>();

    private ServerSocketChannel ssc = null;//klasa która będzie przyjmować połącznia
    private Selector selector = null;//selector to jest taki nadzorca który stoi w tym
    //server socecie i wygląda czy ktoś nie chce się połączyć i wysyła informacje
    //o tym ServerSocektowi, lub wygląda również czy ktoś nie chce czegoś napisać
    //

    public PhoneBookServerNB( String host, int port ) {

        try {
            // Utworzenie kanału dla gniazda serwera
            ssc = ServerSocketChannel.open();

            // Tryb nieblokujący
            ssc.configureBlocking(false);//np metoda read nie będzie blokowała
            //serwera tylko wykona dalej

            // Ustalenie adresu (host+port) gniazda kanału
            ssc.socket().bind(new InetSocketAddress(host, port));

            // Utworzenie selektora
            selector = Selector.open();

            // Zarejestrowanie kanału do obsługi przez selektor
            // dla tego kanału interesuje nas tylko nawiązywanie połączeń
            // tryb - OP_ACCEPT
            ssc.register(selector,SelectionKey.OP_ACCEPT);//tutaj sprawdzanie
            //czy jakiś klient nie chce sie połączyć

        } catch(Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        System.out.println("Server started and ready for handling requests");
        serviceConnections();
    }

    private void serviceConnections() {
        boolean serverIsRunning = true;

        while(serverIsRunning) {
            try {
                // Wywołanie blokujące
                // czeka na zajście  zdarzenia związanego z kanałami
                // zarejestrowanymi do obslugi przez selektor
                selector.select();//otwiera okno wygląda i patrzy czy ktoś stoi pod oknem
                //i chce sie połączyć, napisać albo wykonać inną operację
                System.out.println("Selected");

                // Coś się wydarzyło na kanałach
                // Zbiór kluczy opisuje zdarzenia
                Set<SelectionKey> keys = selector.selectedKeys();//to co się wydarzyło pod oknem
                //selector zapoisuje jako klucze

                Iterator<SelectionKey>  iter = keys.iterator();
                while(iter.hasNext()) {   // dla każdego klucza

                    SelectionKey key =  iter.next(); // pobranie klucza
                    iter.remove();                                 // usuwamy, bo już
                    // go zaraz obsłużymy

                    if (key.isAcceptable()) { // jakiś klient chce się połączyć
                        //Czy klient chce się połączyć jeśli tak wykonujemy kod poniżej
                        //który łącz jakiegoś kilenta
                        System.out.println(" acceptable"+key);
                        // Uzyskanie kanału do komunikacji z klientem
                        // accept jest nieblokujące, bo już klient się zgłosił
                        SocketChannel cc = ssc.accept();

                        // Komunikacja z klientem - nieblokujące we/wy
                        cc.configureBlocking(false);

                        // rejestrujemy kanał komunikacji z klientem
                        // do obsługi przez selektor
                        // - typ zdarzenia - dane gotowe do czytania przez serwer
                        cc.register(selector, SelectionKey.OP_READ);
                        continue;
                    }

                    if (key.isReadable()) {  // któryś z kanałów gotowy do czytania
                        // Uzyskanie kanału na którym czekają dane do odczytania
                        System.out.println(" readable"+key);
                        SocketChannel cc = (SocketChannel) key.channel();
                        serviceRequest(cc);    // obsluga zlecenia
                        continue;
                    }
                }
            } catch(Exception exc) {
                exc.printStackTrace();
                continue;
            }
        }
    }

    private static Pattern reqPatt = Pattern.compile(" +", 3);

    private static String msg[] = { "Ok", "Invalid request", "Not found",
            "Couldn't add - entry already exists",
            "Couldn't replace non-existing entry",
    };

    // Strona kodowa do kodowania/dekodowania buforów
    private static Charset charset  = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;

    // Bufor bajtowy - do niego są wczytywane dane z kanału
    private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);

    // Tu będzie zlecenie do pezetworzenia
   private StringBuffer reqString = new StringBuffer();//string buffer jest w zasadzie
    //string builderem tylko zaprojektowanym tak aby obsługiwał wątki
    //ale skoro w tym przykładzie nie obsługujemy wielu wątków znacznie lepszą opcią
    //będzie użycie po prostu string buildera
//    private StringBuilder reqString = new StringBuilder();
    // Obsługa (JEDNEGO) zlecania
    private void serviceRequest(SocketChannel sc) {
        if (!sc.isOpen()) return; // jeżeli kanał zamknięty - nie ma nic do roboty

        // Odczytanie zlecenia
        reqString.setLength(0);
        bbuf.clear();
        try {
            readLoop:                    // Czytanie jest nieblokujące
            while (true) {               // kontynujemy je dopóki
                int n = sc.read(bbuf);     // nie natrafimy na koniec wiersza
                if (n > 0) {
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    while(cbuf.hasRemaining()) {
                        char c = cbuf.get();
                        if (c == '\r' || c == '\n') break readLoop;
                        reqString.append(c);
                    }
                } else{
                    return;
                }
            }

            if(reqString.toString().equals("bye")){

                System.out.println(sc.getRemoteAddress()+ " logged out");
                sc.close();
                sc.socket().close();
                return;
            }


            // Analiza zlecenia (jak poprzednio) i wołanie nowej metody
            // writeResp zapisującej odpowiedź do kanału
//            String[] req = reqPatt.split(reqString, 3);
//            String cmd = req[0];
            //uproszczenie z ćwiczeń
            System.out.println(sc.getRemoteAddress()+": "+ reqString);
            writeResp(sc, "Good Job!");
//            String[] req = reqPatt.split(reqString, 3);
//            String cmd = req[0];


//            if (cmd.equals("bye")) {             // koniec komunikacji
//                writeResp(sc, 0, null);          // - zamknięcie kanału
//                sc.close();                      // i gniazda
//                sc.socket().close();
//            }
//            else if (cmd.equals("get")) {
//                if (req.length != 2) writeResp(sc, 1, null);
//                else {
//                    String phNum = (String) pd.getPhoneNumber(req[1]);
//                    if (phNum == null) writeResp(sc, 2, null);
//                    else writeResp(sc, 0, phNum);
//                }
//            }
//            else if (cmd.equals("add"))  {
//                if (req.length != 3) writeResp(sc, 1, null);
//                else {
//                    boolean added = pd.addPhoneNumber(req[1], req[2]);
//                    if (added) writeResp(sc, 0, null);
//                    else writeResp(sc, 3, null);
//                }
//            }
//            else if (cmd.equals("replace"))  {
//                if (req.length != 3) writeResp(sc, 1, null);
//                else {
//                    boolean replaced = pd.replacePhoneNumber(req[1], req[2]);
//                    if (replaced) writeResp(sc, 0, null);
//                    else writeResp(sc, 4, null);
//                }
//            }
//            else writeResp(sc, 1, null);             // nieznane zlecenie


        } catch (Exception exc) {                  // przerwane polączenie?
            exc.printStackTrace();
            try { sc.close();
                sc.socket().close();
            } catch (Exception e) {}
        }
    }

    private StringBuffer remsg = new StringBuffer(); // Odpowiedź

    private void writeResp(SocketChannel sc,  String message) throws IOException {
        remsg.setLength(0);


            remsg.append(message);
            remsg.append(System.lineSeparator());

        ByteBuffer buf = charset.encode(CharBuffer.wrap(remsg));
        sc.write(buf);
    }

    public static void main(String[] args) {
        new PhoneBookServerNB("localhost",7474);
    }

}