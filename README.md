# Proyecto: Mortal Tower

## 1. Integrantes del equipo
- Almonacid, Maximiliano Andrés
- Caseres Marcos David
- Leal Isaac
- Sandoval Molina, Jordan David

## 2. Dominio y alcance del sistema

### Descripción del problema
Se busca desarrollar una aplicación de escritorio de un videojuego de rol por turnos con estructura de torre. El jugador elige un personaje inicial y debe avanzar a través de cinco niveles de dificultad creciente, enfrentando enemigos con comportamientos diferentes. En cada combate podrá atacar, defenderse o utilizar habilidades que consumen maná. Si el jugador muere, pierde el progreso de la partida y debe comenzar nuevamente desde el primer nivel. Si supera el quinto nivel y derrota al jefe final, gana la partida.

### Objetivo del sistema
El objetivo del sistema es ofrecer un juego funcional, modular y extensible que permita aplicar de forma clara los conceptos de Programación Orientada a Objetos. El diseño buscará representar correctamente personajes, enemigos, habilidades y combate por turnos, favoreciendo la reutilización de código, el polimorfismo y la separación de responsabilidades.

### Alcance
La primera versión del sistema incluirá:
- Selección inicial de personaje.
- Combate por turnos contra enemigos definidos según el nivel.
- Cinco niveles de torre.
- Nivel 1 con dificultad introductoria.
- Jefe final en el último nivel.
- Elección entre recompensas al terminar cada combate.
- Sistema de habilidades con consumo de maná.
- Registro de usuarios y persistencia de logros.

Quedarán fuera de alcance en esta versión:
- Historia.
- Inventario amplio de objetos.
- Tienda.
- Animaciones avanzadas.

### Funcionalidades principales (Features)

- *Selección de personaje jugable*
  - El jugador podrá elegir un personaje al comenzar la partida.
  - Los personajes disponibles serán: Orco, Elfo, Mago y Caballero.
  - Cada uno tendrá atributos y estilo de juego diferentes.

- *Sistema de combate por turnos*
  - En cada turno el jugador y el enemigo podrán atacarse, defenderse o usar una habilidad.
  - El daño tendrá un componente aleatorio y posibilidad de golpe crítico.
  - La pelea termina cuando uno de los dos luchadores gana, es decir que por medio de sus ataques deja sin puntos de vida al otro.

- *Sistema de enemigos*
  - Los enemigos aparecerán según el nivel de la torre.
  - Existirán enemigos de tipo agresivo, defensivo.
  - Cada tipo de enemigo reaccionará de forma distinta ante el estado del combate.

- *Sistema de habilidades*
  - Cada clase tendrá habilidades exclusivas.
  - Al superar un nivel, el jugador podrá elegir una entre tres habilidades aleatorias o recuperar vida o maná.
  - Las habilidades consumirán maná.
  - El jugador podrá equipar hasta 4 habilidades ya sean para atacar o defenderse.
  - Si obtiene una nueva habilidad de una categoría completa, deberá decidir si conservar una anterior o reemplazarla.

- *Progresión*
  - La torre tendrá 5 niveles.
  - El primer nivel funcionará como introducción a las mecánicas.
  - Si el jugador muere, reiniciará desde el comienzo.
  - Si derrota al jefe final del nivel 5, ganará la partida.

- *Persistencia*
  - El sistema almacenará las habilidades correspondientes a cada personaje jugable .
  - Cada jugador registrará un usuario.
  - El sistema almacenará logros o trofeos asociados a ese usuario.
  - Los logros podrán consultarse posteriormente.

## 3. Arquitectura y diseño

### Arquitectura general

### Patrón de diseño adicional: Strategy
- *Nombre del patrón:* Strategy.
- *Justificación:* Se utilizará el patrón Strategy para modelar el comportamiento de combate de los enemigos.
Cada enemigo tendrá asociada una estrategia que definirá cómo actúa durante su turno,
por ejemplo, atacar agresivamente, defenderse.
Este patrón permitirá encapsular distintos algoritmos de decisión en clases separadas,
favoreciendo la reutilización, el polimorfismo y la posibilidad de agregar nuevos tipos
de comportamiento sin modificar la clase Enemigo.
- *Aplicación concreta:*
  - Enemigo agresivo: prioriza atacar.
  - Enemigo defensivo: prioriza defenderse o resistir.
  - Enemigo inteligente: decide según el contexto del combate, por ejemplo su vida actual, el estado del jugador y el maná disponible.

### Diagramas de diseño

### **Diagrama de clases UML (Conceptual)**




#### **Prototipo de la IGU**



## 4. Stack tecnológico
- **Lenguaje:** Java
- **IDE:** Visual Studio Code
- **Framework de IGU:** Java Swing
- *Base de datos:* PostgreSQL
- **Control de Versiones:** Git y GitHub
