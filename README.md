# Programming-Langueges-IIIsem
Jezyki programowania - laby <br>
Polecenia: <br>

LAB 2

Podczas laboratorium należy rozwiązać problem podziału napojów między uczestników towarzystkiego spotkania przy zadanych ograniczeniach. Zakładamy, że znany jest stan magazynu z napojami. Napoje, o różnych smakach, zmagazynowane są w dzbankach, przy czym smak i objętość napoju w danym dzbanku jest znana. Stan ten można zapisać w postaci:
Identyfikator dzbanka; Identyfikator smaku napoju; Objętość napoju (w mililitrach); <br>
```1; 1; 400
2; 1; 800
3; 2; 1700
...
```
Zakładamy, że liczba osób uczestniczących w spotkaniu towarzyskim jest znana. Zakładamy też, że znane są preferowane smaki każdej z tych osób w postaci listy uporządkowanej od najbardziej ulubionego smaku po najmniej ulubiony (lista ta nigdy nie jest pusta). Dane te można zapisać w postaci:
Identyfikator osoby; Lista identyfikatorów preferowanych smaków
```1;1,2
2;1
3;3,2,1
...
```
Jak widać zestawy danych mają postać tabelaryczną. Pierwsze wiersze zawierają nazwy kolumn, kolejne niosą w sobie dane. Średnik pełni rolę separatora poszczególnych komórek w tabelach, przecinek zaś - separatora elementów listy w obrębie jednej komórki. Załóżmy ponadto, że mamy da się ocenić "zadowolenie" danej osoby z dostarczonych jej napojów. Zadowolenie i-tej osoby:
```
zi=∑nik=1w(k)∗vik
```
gdzie
vik - objętość napoju dostarczonego osobie i, o smaku występującym na liście preferowanych smaków tej osoby w pozycji k <br>
ni - długość listy preferowanych smaków osoby i <br>
w(k) - waga smaku, uzależniona od pozycji na liście preferowanych smaków (można przedstawić ją np. wzorem w(k)=ni−k+1 ). <br>
Niezadowolenie i-tej osoby:
```
bi=max(400−vi1,0)
```
gdzie
vi1 - objętość napoju dostarczonego osobie i, o smaku występującym na pierwszej pozycji na liście preferowanych smaków tej osoby. <br>
Jak widać, niezadowolenie zdefiniowano tak, że ono zaistnieje, jeśli dana osoba nie otrzyma przynajmniej 400 ml napoju o najbardziej ulubionym przez nią smaku.
Przy tak określonych danych należy dokonać podziału napojów według kryterium: <br>
największe całościowe "zadowolenie" (należy tak podzielić dostępne napoje, by suma wszystkich zi była największa), przy najmniejszym całościowym "niezadowoleniu" (należy napoje tak podzielić, by każdy jakiś napój otrzymał)
Znalezione rozwiązanie, tj. rezultat podziału napojów między osoby należy wypisać na ekranie w postaci tabelarycznej
Identyfikator osoby;Lista par (identyfikator napoju, przydzielona objętość napoju).

LAB 3

Podczas laboratorium należy zbudować "mały system", pozwalający na interakcje z użytkownikami z poziomu konsoli, umożliwiający wykonywanie operacji CRUD (od ang. create, read, update and delete; pol. utwórz, odczytaj, aktualizuj i usuń) na przetwarzanych danych. Dane powinny być w jakiś sposób utrwalane (mogą być zapisywne w plikach).
Wymagane jest, by logika biznesowa systemu była oddzielona od interfejsu użytkownika (by dało się bez problemu podmienić interfejs konsolowy na interfejs graficzny). Ponadto należy zaprojektować odpowiednie struktury danych (do czego można wykorzystać kolekcje i mapy) oraz obsłużyć wyjątki (oprócz wyjątków generowanych przez wykorzystywane metody Java API należy zaproponować obsługę własnych wyjątków).
Budowany system wspierać ma proces obsługi reklamacji zakupionych towarów w jakimś hipotetycznym sklepie (markecie budowalnym, sklepie AGD itp.). Oczywiście system ten będzie jedynie "przybliżeniem" rzeczywistości. Aby dało się go zaimplementować przyjmujemu znaczące uproszczenia.
Zakładamy, że w procesie biorą udział następujący aktorzy: klient (zgłaszający reklamację), pracownik (sklepu), producent (reklamowanego towaru).
Klient: reklamuje towar, sprawdza status reklamacji (zgłoszona, zweryfikowana, do odbioru, zakończona), odbiera towar.
Pracownik: przyjmuje reklamacje od klienta, komunikuje się z producentem (przekazuje mu reklamowany towar wnosząc o uznanie reklamacji, przyjmuje odpowiedź producenta, przyjmuje zwrotnie reklamowany towar), przekazuje klientom decyzje w sprawie uznania/nieuznania reklamacji, informuje klienta o termin odbioru reklamowanego towaru, kończy obsługę reklamacji.
Producent: przyjmuje reklamowany towar wraz z wnioskiem o uznanie reklamacji od pracownika, odpowiada na ten wniosek, przekazuje zwrotnie pracownikowi reklamowany towar.
Wymienieni aktorzy uzyskują dostęp do systemu za pośrednictwem osobnych aplikacji: KlientApp (oferującej interfejs klienta), PracownikApp (oferującej interfejs pracownika), ProducentApp (oferującej interfejs producenta). <br> <br>
Zakładamy, że:
reklamacja dotyczy jednego towaru, <br>
klientów może być wiele, <br>
pracownik jest jeden, <br>
producentów może być wiele. <br> <br>
Synchronizacja pomiędzy poszczególnymi częściami systemu (uruchomionymi instancjami wymienionych aplikacji) powinna odbywać się poprzez współdzielenie utrwalanych gdzieś danych. W przypadku zapisywania danych w systemie plików może pojawić się kłopot - system operacyjny może zablokować możliwość zapisu do danego pliku, jeśli aktualnie jest on otwarty w innej aplikacji. Wtedy może przydać się właśnie obsługa wyjątków. Generalnie - implementacja wielodostępu to bardzo trudny temat. Na potrzeby laboratorium mocno go upraszczamy (nie ma potrzeby budowania tytaj jakichś bardzo złożonych mechanizmów).
Nawiasem mówiąc niezły tutorial o operacjach na plikach można znaleźć pod adresem: https://www.marcobehler.com/guides/java-files Aby przetestować działanie systemu powinno dać się uruchamiać osobno: przynajmniej dwie instancje KlientApp, jedną instancję PracownikApp, przynajmniej dwie instancje ProducentApp.
Funkcje przypisane użytkownikom (aktorom, patrz wyżej) to funkcje ogólne. W trakcie ich implementacji mogą pojawić się wymagania na funkcje szczegółowe, jak np. wyszukiwanie, filtrowanie, sortowanie danych na odpowiednich interfejsach. <br> <br>
Zakładamy, że aplikacja będzie komunikować się z użytkownikami z poziomu konsoli (czyli, że interfejs użytkownika będzie tekstowy - jeśli ktoś chciałby zaimplementować interfejs graficzny, to oczywiście może to zrobić, jednak nie jest to wymagane).
Model danych może być uproszczony. Wystarczy, że będzie on uwzględniał: <br>
dane dotyczące reklamacji: identyfikator reklamacji, identyfikator klienta, identyfikator towaru status, opis reklamacji, data zgłoszenia pracownikowi, data przekazania producentowi, data odpowiedzi producenta, data odbioru, data zamknięcia reklamacji; <br>
dane dotyczące towarów: identyfikator towaru, nazwa towaru; <br>
dane dotyczące klientów: identyfikator klienta, nazwa klienta; <br>
dane dotyczące producentów: identyfikator producenta, nazwa producenta. <br>
Proszę zastanowić się, jak będą wyglądały struktury do przechowywane w pamięci i jak będzie wyglądał ich zapis w plikach. <br> <br>
Proszę też zastanowić się, jak poradzić sobie symulowaniem osi czasu. W rzeczywistych systemach aplikacje czytają zegar systemowy. Trudno jednak wymagać, by w trakcie testów na laboratorium opierać się na zegarze systemowym. Zamiast tego można przyjąć, że bieżący czas jest zapisywany we współdzielonym pliku, a aplikacje odczytują go na żądanie (na zasadzie "odświeżenia"). Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.

LAB 4

Podczas laboratorium należy zbudować aplikację o przyjaznym, graficznym interfejsie użytkownika. Interfejs ten powstać ma w oparciu o klasy SWING lub klasy JavaFX (opcjonalnie).
Budowana aplikacja służyć ma do wizualizacji działania prostej maszyny jak na załączonym rysunku. <br>

Maszynę umieszczono w układzie współrzędnych x,y. Maszyna składa się z dwóch przegubowo połączonych ramion, z których jedno jest napędzane (kąt jego obrotu to α, oś jego obrotu jest umieszczona w początku układu współrzędnych), zaś ruch drugiego ramienia wynikać ma z nałożonych ograniczeń. To drugie ramię przechodzi przez przegubowo umocowany suwak. Geometria maszyny parametryzowana jest wartościami: l1 – długość pierwszego ramienia, l2 – długość drugiego ramienia, d i h – odpowiednio, odległość w poziomie i w pionie suwaka od początku układu współrzędnych. Jak widać środek suwaka, i zarazem jego oś obrotu, położone są w punkcie ps=(d,h).
Aplikacja powinna umożliwiać wprowadzenie parametrów maszyny oraz pozwalać na uruchomienie symulacji (polegającej na wykonaniu pełnego obrotu przez ramię l1). Na interfejsie użytkownika powinny pojawić się wykresy składowych prędkości w poziomie i pionie punktu p2 (wykresy składowych vx oraz vy). Prędkości te należy wyliczyć poprzez całkowanie położenia tego punktu. Można to zrobić stosując pewne uproszczenie. Wystarczy, by podczas symulacji w kolejnych krokach wyliczać różnice pomiędzy bieżącym a poprzednim położeniem punktu p2. Prędkość będzie proporcjonalna do tej różnicy. Oczywiście przyrosty kąta α powinny być odpowiednio małe (np. pół stopnia).
Budowana aplikacja ma być modularna. W związku z tym w jej deskryptorze module-info.java powinny pojawić się odpowiednie wpisy dotyczące zależności. W przypadku użycia klas SWING będzie to:
```
module okienka {
	requires java.desktop;
}
```
zaś w przypadku użycia klas JavaFX będzie to:
```
module SimpleFX {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
}
```
Ponieważ klasy SWING należą do standardowej dystrybucji JDK komenda uruchamiająca aplikację nie wymaga korygowania ścieżki modułowej. W przypadku umieszczenia skompilowanych klas aplikacji w katalogu "cośtam\Okienka\bin" komenda ta przybierze postać:
```
> java.exe -p "cośtam\Okienka\bin" -m okienka/app.Ramka
Jeśli zbuduje się jara z aplikacją, to tego jara dokłada się do ścieżki modułowej:
> java.exe -p okienka.jar -m okienka/app.Ramka
JavaFX to osobny runtime wymagający osobnej instalacji. W związku z tym komenda uruchamiająca aplikację musi uwzględniać położenie modułów dostarczanych przez ten runtime. Komenda ta przybiera zwykle postać:
> java -p "cośtam\SimpleFX\bin;E:\Java\javafx-sdk-17.0.2\lib\javafx.base.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.controls.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.fxml.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.graphics.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.media.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.swing.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx.web.jar;E:\Java\javafx-sdk-17.0.2\lib\javafx-swt.jar" -m SimpleFX/application.Main
W przykładzie powyżej uwzględniono ścieżki do wszystkich modułów. Faktycznie wystarczyłoby wskazać ścieżki tylko do modułów wymaganych. Podobnie jak w poprzednim przypadku zamiast ścieżki do skompilowanych klas aplikacji można byłoby wskazać zbudowanego jara.
Pracując z modułami Java nie da się tak swobodnie korzystać z bibliotek skompilowanych klas jak to było wcześniej. Normalnie nie da się zrobić fatjara, zamieszczając w nim w jednym korzeniu kody bajtowe klas pochodzące z różnych bibliotek. Teraz bowiem mamy do czynienia z modułami, z których każdy ma własny deskryptor module-info. A zgodnie ze specyfikacją (https://docs.oracle.com/javase/9/docs/specs/jar/jar.html#Modular) modularny jar może zawierać tylko jeden deskryptor modułu (plik module-info.class) umieszczony w głównym katalogu archiwum. Tak wiec normalnie nie da się w jednym jarze umieścić wielu modułów. Istnieją obejścia tego problemu - poprzez implementację własnego ładowacza klas, który będzie "wyciągał" moduły spakowane w jakąś strukturę wewnątrz jara. Ale to trochę skomplikowana sprawa.
Zwykle jeden jar zawiera jeden moduł. Wybrane moduły można zlinkować tworząc własne środowisko uruchomieniowe. Linkowanie odbywa się za pomocą narzędzia jlink (patrz: https://livebook.manning.com/book/the-java-9-module-system/chapter-4/76 https://www.developer.com/design/how-modules-are-packaged-in-java-9/ ). Można to zrobić tak (z odpowiednim atrybutami do opcji -p, --add-modules)
> jlink -p . --add-modules javafx.controls,javafx.base,javafx.fxml,javafx.graphics,myfxdemo --launcher runapp=myfxdemo/org.openjfx.MyApp --output app
```
W efekcie powstanie katalog, a w nim wszystko co potrzeba do uruchomienia wskazanej klasy (bynajmniej nie jest to jeden plik). Jednak uwaga - w przypadku bibliotek natywnych trzeba jeszcze skopiować do tego środowiska zależności w postaci bibliotek ładowane dynamicznie. Tak więc jeśli buduje się aplikację w całości modularnie, to nie da się zrobić jednego fatjara.
Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.

LAB 5

Podczas laboratorium należy zbudować aplikację, w której dojdzie do synchronizacji wielu wątków. Aplikacja powinna pozwalać na uruchamianie tych wątków i obserwowanie ich stanów. Aplikacja powinna być parametryzowana (wyjaśnienie znajduje się dalej).
Zakładamy, że aplikacja będzie pełnić rolę symulatora laboratorium, w którym hodowane są jakieś organizmy. W laboratorium tym mają pracować laboranci, którzy przemieszczają się wzdłuż laboratoryjnych półek. Ich zadaniem jest dokarmianie hodowanych organizmów umieszczonych w pojemnikach na tych półkach. Laboratorium jest dość ciasne. Laboranci przemieszczając się wzdłuż półek nie mogą się wyminąć. Aby jakoś zapanować nad ruchem wprowadzono następującą reguły: <br>
laboranci przemieszczają się wzdłuż półek tam i z powrotem, <br>
laborantów nie może być więcej niż półek, <br>
jeśli dany laborant w pewnym momencie nie będzie mógł kontynuować ruchu w obranym kierunku (bo w miejscu, do którego chciał przejść znajduje się inny laborant), to wtedy zmienia kierunek ruchu (oczywiście „kierunek” tutaj ma znaczenie „w prawo” i „w lewo”, a nie cechy wektora znanej z fizyki) <br>
laboranci mają dostęp do wspólnego dystrybutora z każdego miejsca (w każdej chwili mogą sięgnąć do dystrybutora, ale muszą to robić rozłącznie) <br>
Laboranci mają przy sobie własne zbiorniczki z pokarmem o tej samej pojemności (np. 50). Napełniają je z jednego, współdzielonego dystrybutora (w którym pokarm się nie kończy). Podczas dokarmiania ujmują odpowiednią ilość pokarmu z własnych zbiorniczków i wstrzykują je do pojemników z dokarmianymi organizmami. Każdy laborant w danej chwili może dokarmiać tylko organizm znajdujący się w pojemniku na półce, przed którą aktualnie stoi. <br>
Pojemniki z organizmami mają czujniki, tak że można odczytać stan znajdującego się w nich pożywienia jak i organizmu. Zakładamy, że stan pożywienia nie powinien przekroczyć poziomu 10. Pożywienie należy uzupełnić jedynie wtedy, gdy stan będzie niższy niż 5. Jeśli jakiś organizm pozostanie przez dłuższy czas bez pożywienia, to ten organizm ginie. <br>
Stan organizmu można oznaczyć wartościami od 0 do 5. Wartość 0 oznacza, że organizm zginął, zaś wartość 5 – że jest w pełni odżywiony. Wartości pośrednie mają oznaczać stan organizmu przy braku pożywienia w pojemniku – im dłużej w pojemniku brakować będzie pożywienia, tym niższa będzie wartość odpowiadająca stanowi. <br>
Chyba najprostszym sposobem wizualizacji tego, co się w laboratorium dzieje, jest użycie etykiet tekstowych jak na schemacie poniżej (w kolumnie distributor wyświetla się informacja o tym, który laborant aktualnie napełnia swój zbiorniczek; kolumna assistant reprezentuje laborantów ze zbiorniczkami; kolumna nurishment reprezentuje stan pożywienia w pojemnikach; kolumna stamina reprezentuje stan organizmów). <br>
```
distributor   asssistant  nourishment  stamina
               |a_40|      | 10 |     | 5 |
               |____|      | 08 |     | 5 |
| b |          |____|      | 05 |     | 5 |
               |c_50|      | 10 |     | 5 |
               |d_00|      | 00 |     | 3 |
               |____|      | 00 |     | 0 |
```
Jak widać laborantom przypisano litery alfabetu celem ich rozróżnienia (można się zastanowić, czy laborant napełniający zbiorniczek musi znikać z kolumny asssistant, czy też może w niej zostać). Można też wymyśleć inny sposób wizualizacji.
Każdy laborant powinien być wątkiem. Każdy organizm powinien być wątkiem. Pomiędzy wątkami powinno dojść do wzajemnego wykluczania (w szczególności – laboranci powinni wykluczać się przy próbie dostępu do dystrybutora oraz przy próbach przemieszczania się wzdłuż półek). Do zastanowienia jest, czy i jak powinny synchronizować się wątki laborantów z wątkami organizmów (przy próbie odczytu stanu?). Żeby sprawę budowy interfejsu graficznego nieco uprościć można przyjąć, że istnieje jakieś ograniczenie na maksymalną liczbę uruchamianych wątków. Pozwoli to zaplanować wielkość interfejsu. Proszę też uwzględnić w budowanych aplikacjach możliwość zmiany prędkości działania wątków.
Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.

LAB 6

Podczas laboratorium należy zbudować aplikację działającą w środowisku rozproszonym, wykorzystującą do komunikacji gniazda TCP/IP (klasy ServerSocket, Socket). Dokładniej - należy zaimplementować mały systemu, w którego skład wejdą instancje klas uruchamianych równolegle (na jednym lub na kilku różnych komputerach). System ten ma być symulatorem gry „Wyspa skarbów”.
Specyfikacja problemu: <br>
W grze biorą udział "gracze" (może ich być kilku) komunikujący się z "zarządcą" (który jest tylko jeden). <br>
Gra przebiega na prostokątnej planszy o zadanej wysokości i szerokości (iloczyn wysokości i szerokości definiuje liczbę pól planszy) <br>
Każde pole planszy może zawierać: nic, przeszkodę, skarb, gracza (przy czym gracz nie może wejść na pole z przeszkodą, za to może wejść na pole ze skarbem lub pole puste)
„zarządca” posiada całą planszę oraz odpowiada za przekazywanie na żądanie informacji o niej do graczy, jak również zmienia stan posiadanej planszy na postawie żądań przychodzących od graczy. <br>
Gracze poruszają się po planszy, przechodząc z jednego jej pola do pola sąsiedniego (sąsiednich pól jest 8) oraz wykonują na niej akcje,
aby zaszła jakaś zmiana dany "gracz" musi przesłać „zarządcy” odpowiednie polecenie
dany gracz może wysłać do „zarządcy” następujące polecenia: <br>
```
zapytanie o otoczenie (komenda „see”), w odpowiedzi otrzymują stan swojego otoczenia (dokładniej - każdy gracz będąc w danej pozycji może "zobaczyć" jedynie swoje najbliższe otoczenie, tj. osiem sąsiednich pól) <br>
wykonania ruchu (komenda „move”), w odpowiedzi otrzymują informację o statusie wykonanego ruchu (ruch został wykonany lub ruch nie został wykonany), <br>
wzięcia skarbu (komenda „take”), w odpowiedzi otrzymują infomację o statusie wykonanego ruchu (otrzymują skarb lub informację, że skarbu nie dało się podnieść) <br>
```
Celem każdego gracza jest zebranie jak największej liczby skarbów w jak najkrótszym czasie <br>
Wzięcie skarbu nie jest obowiązkowe, bo każdy skarb po wzięciu należy jeszcze "rozpakować", a to zajmuje czas <br>
"Rozpakowanie" skarbu można zasymulować przez umieszczenie w jego opisie czasu oczekiwania <br>
Zakładamy, że "gracz" przez czas "rozpakowywania" uczciwie poczaka, ewentualne oszukiwanie w tej kwestii można byłoby wyeliminować przesyłająć w opisie skarbu jakieś zadanie obliczeniowe do wykonania - ale to jest zbyt dużo jak na jedne laboratorium <br>
Na początku gry gracze ustawiani są w losowo wybranych miejscach znanych tylko "zarządcy" <br>
W trakcie gry gracze powinni budować sobie własny "model planszy" by podczas ruchów explorować coraz to nowe miejsca <br>
Polecenia wysłane do "zarządcy" trafiają do kolejki, którą "zarządca" przetwarza w kolejności zgłoszeń <br> <br>
Choć kodowanie przesyłanych informacji może być dowolne, to jednak zalecane jest kodowanie tekstowe (ciąg znaków, a nie serializowany obiekt)
"gracz" zna host i port, na który "zarządca" nasłuchuje na przychodzące polecenia (osobny port jest otwierany dla każdego "gracza"), "zarządca" zna host i port, na którym "gracz" nasłuchuje na przychodzące odpowiedzi (host i port jest unikalny dla danego "gracza")
sposób przekazania parametrów hostów i portów "graczom" oraz "zarządcy" jest dowolny, danych tych jednak nie można wpisać na twardo w kodzie źródłowym
zarówno "gracz", jak i "zarządca" posiadają graficzny interfejs, na którym można obserwować stan planszy (odpowiednio - eksplorowanej przez "gracza" oraz zarządzanej przez "zarządcę").
Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.

LAB 7

Podczas laboratorium należy zbudować mały rozproszony system wykorzystując technologię RMI. W skład tego systemu mają wejść instancje klas uruchamiane równolegle (na jednym lub na kilku różnych komputerach). System powinien umożliwić zasymulowanie działania sklepu oferującego gadżety z nadrukami reklamowymi. Jego architekturę przedstawiono na rysunku poniżej.

Elementami systemu są: <br>
ClientApp - aplikacja z interfejsem graficznym, umożliwiająca użytkownikowi przeglądanie oferty sklepu, składanie zamówień, obserwację stanu zamówień. Korzysta z interfejsu zdalnego Registry by pozyskać referencję do obiektu oferującego interfejs zdalny IShop. Korzysta z interfejsu IShop by zarejestrować się w sklepie, a potem przeglądać ofertę, składać zamówienia, zgłaszać własną instancję implementującą IStatusListenet celem pozyskiwania powiadomień o statusie zmian zamówień oraz usuwać to zgłoszenie. Może też sprawdzać status zamówienia na własne żądanie. <br>
ShopApp - aplikacja z interfejsem graficznym służąca za repozytorium. Sklep posiada instancję Registry, tj. instancję rejestru RMI, w którym rejestruje własną instancję implementującą IShop. Metody tej instancji uruchamiane są zdalnie przez ClientApp oraz SellerApp. W szczególności przy zmianie statusu zamówienia powinna poinformować właściwą ClientApp, jeśli jest zasubskrybowana w celu otrzymywania powiadomień, o zaszłej zmianie. <br>
SellerApp - aplikacja z interfejse graficznym pozwalająca obsłużyć zamówienia. Korzysta z interefejsu zdalnego Registry by pozyskać referencję do obiektu oferującego interfejs zdalny IShop. Korzysta z interfejsu IShop by pozyskiwać informacje o zamówieniach oraz zmieniać ich statusy. <br> <br>
W trakcie implementacji należy wykorzystać klasy z dostarczonego modułu gadgets.jar.
Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.
