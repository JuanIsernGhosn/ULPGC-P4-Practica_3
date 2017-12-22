# P4: Practica 3 (Programación concurrente)

## Enunciado:

Práctica 3. Programación concurrente: Cola en las cajas de un hipermercado

## Organización

Se desea construir un sistema que simule el funcionamiento de las cajas de un hipermercado en el que existe una cola única de clientes. Los clientes se dirigen desde la cola a una de las cajas existentes (no hay una cola en cada caja). Además, las cajas dan cuenta a la contabilidad de los importes de las ventas.

## Implementación

### Clases:

En este sistema se han identificado las siguientes clases: Cliente, Cola, Caja y Contabilidad.

#### Clase Cliente
La clase Cliente se suministra implementada y se utiliza en la simulación para representar los clientes que interactúan en la cola y las cajas.

El constructor de Cliente no requiere parámetros. El cliente creado dispone de un nombre, accesible con .dameNombre(), el precio del carro que lleva, que se obtiene mediante .damePrecioCarro() y el método .toString(), que nos suministra toda esta información en forma de String.

#### Clase Cola
Los objetos de la clase Cola se encargan de almacenar los objetos Cliente que esperan ser atendidos. La salida de un cliente de la cola se produce por su principio.

Las operaciones que soportan la Cola son .añadirFinal(), que añade un nuevo cliente, .añadirPrincipio(), que añade a un cliente de una caja estropeda y .sacar(), que devuelve (y extrae) el cliente que esté al principio de la cola. Si la cola está abierta y no hay clientes se espera un máximo de 10 segundos, al cabo de los cuales, si no llegan clientes, devolvería null. La cola se puede cerrar llamando al método "cerrar()". Una cola cerrada no acepta nuevos clientes por el final (el método .añadirFinal() no hace nada) y cuando se pretende sacar un cliente, y no queda ninguno, .sacar() devuelve inmediatamente null. Además, con el método .tamañoMáximo(), la cola, nos devuelve el tamaño máximo que ha alcanzado durante su existencia

#### Clase Caja
La clase Caja se encarga de pedir un Cliente a la Cola, atenderlo y repetir el proceso.

Los objetos de la clase Caja se crean pasándole al constructor un objeto Cola y uno Contabilidad. Si la cola devuelve un cliente null, la caja se cierra. La caja tarda un tiempo en atender cada cliente, el tiempo de atenciÛn es el precio del carrito dividido por 10, en segundos. Cada caja tendrá un identificador único autogenerado por la clase (use atributos static) que se emplear· en el mostrado de su estado. Las cajas pueden cerrarse tambiÈn mediante el "interrupt()" de Thread. En este caso, si se está procesando un cliente Èste se volverá a introducir en la cola por el principio. El método "run()" de una caja termina al ser cerrada.

Cuando una caja se cierra, se actualiza el objeto Contabilidad con la suma de importes de las compras completadas en esa caja.

#### Clase Contabilidad
Los objetos de la clase Contabilidad disponen de dos métodos: .añadeSaldo(), que añade una cantidad al saldo, y .dameSaldo(), que devuelve la cantidad acumulada. Cuando se crean tienen saldo cero.

#### Clase DuendeAveria
La clase DuendeAveria, se suministra implementada (no es necesario ver el código). Un objeto de esta clase se encarga de averiar cajas aleatoriamente llamando al método "interrupt()" del hilo de la Caja pasada. La clase dispone de un constructor que acepta un array de objetos Caja o contenedores de objetos Caja. Las cajas pasadas al constructor deben haber sido iniciadas con "start()" previamente.

### Simulación
El programa principal se ha de encargar de pedir el número de cajas a usar y el número de clientes de la simulación. Se crearán y arrancarán los elementos que intervienen de forma que los clientes se irán añadiendo a la cola con una cadencia aleatoria de entre cero y 5 segundos. La cola se cerrará al cabo de 60 segundos. Al procesar el último cliente, o si no quedaran cajas abiertas, se detendrá el programa.

Se simulará asimismo el que las cajas pueden, por avería, dejar de funcionar. Para ello se suministra la clase DuendeAveria que ejecuta, en un hilo a parte, la acción de averiar las cajas aleatoriamente con interrupt(). Para que se produzca este proceso de averias es suficiente con crear un objeto de la clase DuendeAveria al que se le pase un array o Collection con las Cajas.

### VISUALIZACIÓN

Para tener un percepción de lo que ocurre se deben mostrar información que incluya instante (hora) y estado del objeto implicado (Ideal el estado de la cola y cada caja) en cada uno de los siguientes eventos:

```
Se añade un cliente a la cola.

Se saca un cliente de la cola.

Se inicia el procesamiento de un cliente en una caja.

Se finaliza el procesamiento de un cliente en una caja.

Se estropea una caja

Se añaden datos a la contabilidad.
```

La salida del programa es textual, pero es posible usar secuencias de escape ANSI, para poder dar formato a la información textual mostrada.

## Authors
* **Juan Isern** - *Initial work* - [JuanIsernGhosn](https://github.com/JuanIsernGhosn/)
