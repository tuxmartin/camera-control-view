Testovaci HTTP server
=====================

Nekde na disku spustit:

`$ python -m SimpleHTTPServer`

Sice to bude vypisovat chyby (neni implementovany POST), ale aspon nebude padat Android app.

Ukazkovy vystup SimpleHTTPServer:

> Serving HTTP on 0.0.0.0 port 8000 ...
> 192.168.1.93 - - [14/Apr/2015 01:07:17] code 501, message Unsupported method ('POST')
> 192.168.1.93 - - [14/Apr/2015 01:07:17] "POST / HTTP/1.1" 501 -

