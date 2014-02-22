#ImgBook
=======

**Autor:** Szymon Rękawek 

ImgBook jest to aplikacja webowa, która ma być javowym odpowiednikiem stron typu `kwejk`. 

Funkcjonalności to:
* Rejestracja
* Logowanie
* Podział na stronę główną oraz poczekalnie
* Dodawanie obrazków(po zalogowaniu)
* Dodawanie komentarzy(po zalogowaniu)
* Ocenianie obrazków(+, -)(po zalogowaniu)
* Generowanie linku do pojedynczego obrazka(image.xhtml), oraz do poszczególnych stron strony głównej(page.xhtml) oraz poczekalni(pageQueue.xhtml)

Do narzędzi administratorskich należą:
* Promocja obrazków z poczekalni na stronę główną
* Usuwanie obrazków
* Degradacja obrazków ze strony głównej do poczekalni

Aplikacja korzysta z następujących technologii:
* JSF
* JPA
* EJB 3.1
* MySQL za pomocą jdbc
* CDI, BeanValidations
* Primefaces

W folderze Screenshots znajdują się screeny z aplikacji.

Używany serwer to:
* Glassfish 4.0
