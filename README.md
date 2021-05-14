# Monedero

## Contexto

Este repositorio contiene el código de un _monedero virtual_, al que podemos agregarle y quitarle dinero, a través 
de los métodos `Monedero.sacar` y `Monedero.poner`, respectivamente. 
Pero hay algunos problemas: por un lado el código no está muy bien testeado, y por el otro, hay numeros _code smells_. 

## Consigna

Tenés seis tareas: 

 1. :fork_and_knife: Hacé un _fork_ de este repositorio (presionando desde Github el botón Fork)
 2. :arrow_down: Descargalo y construí el proyecto, utilizando `maven`
 2. :nose: Identificá y anotá todos los _code smells_ que encuentres 
 3. :test_tube: Agregá los tests faltantes y mejorá los existentes. 
     * :eyes: Ojo: ¡un test sin ningún tipo de aserción está incompleto!
 4. :rescue_worker_helmet: Corregí smells, de a un commit por vez. 
 5. :arrow_up: Subí todos los cambios a tu _fork_

## Tecnologías usadas

* Java 8.
* JUnit 5. :warning: La versión 5 de JUnit es la más nueva del framework y presenta algunas diferencias respecto a la versión "clásica" (JUnit 4). Para mayores detalles, ver:
    *  [Apunte de herramientas](https://docs.google.com/document/d/1VYBey56M0UU6C0689hAClAvF9ILE6E7nKIuOqrRJnWQ/edit#heading=h.dnwhvummp994)
    *  [Entrada de Blog (en inglés)](https://www.baeldung.com/junit-5-migration)
    *  [Entrada de Blog (en español)](https://www.paradigmadigital.com/dev/nos-espera-junit-5/)
* Maven 3.3 o superior

## Code Smells
### Cuenta
- Cuenta con un constructor que estable el saldo en 0. Podria hacerse uso del constructor que recibe como parametro el valor de saldo.
- Metodo setMovimientos inutil porque jamas se usa ademas de que, como los movimientos son un historial de cosas, no tiene sentido mutarlos
- Poner y sacar tinen logica similar, realizan validaciones sobre la operacion y luego agregan un movimiento. Podria utilizarse patron Guard
- CodeSmells de poner:
 - La validacion del valor de la variable cuanto se realiza tambien en sacar
 - La validacion de operaciones diarias podria delegarse dado que complejiza la lectura del codigo
 - La validacion de operaciones utiliza un getter para una propiedad de la clase
 - Delega el agregado de un movimiento, a la lista de movimientos, al propio movimiento. Este ultimo llama a clase Cuenta para agregar un movimiento.
- CodeSmells de sacar:
 - La validacion del valor de la variable cuanto se realiza tambien en poner
 - La validacion de monto diario retirado podria delegarse dado que complejiza la lectura del codigo
 - La validacion de operaciones utiliza un getter para una propiedad de la clase
- En getMontoExtraidoA falta delegar responsabilidad al movimiento para que nos diga si es deposito y si es del dia de hoy

### Movimiento
- fueDepositado jamas utilizado
- fueExtraido jamas utilizado
- agregateA esta realizando algo que deberia ser responsabilidad de Cuenta