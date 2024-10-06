# hewi-nx-tools

Generatoren, die Angular-Code und nx- Konfigurationsfile generieren.

## ngrx-lib-generator

Generiert ein Starter-Set von 3 Libraries für ngrx-Files, wobei als Architektur-Pattern Model-Data-API verwendet wird.

Es werden folgende Libraries generiert:

+ api: enthält eine facade sowie die ngrx-Data-Provider für die Konfiguration in app.config.ts
+ data: enthält Standard-ngrx-Files mit echten actions, selectors, redurer und effects als Blaupause. Außerdem einen http.service
+ model: enthält ein model-Dto

Die libs passen zusammen. Die entsprechenden exports in den index.ts werden wie benötigt generiert.

### options

pending


