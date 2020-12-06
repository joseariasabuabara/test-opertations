# test-opertations

Para la disponibilidad de la aplicacion se usa una arquitectura soa (orientadas a servicios)
Se usa una BD de Datos DynamoDB que puede escalar facilmente, que permite grandes transacciones
permitiendo a la aplicacion una disponibilidad muy alta

 Crear las siguiente tablas en AWS en DynamoDB (se escogio esta BD NoSQL)
    appGate-dev-operations-sessions
    appGate-dev-operation
    appGate-dev-operand

 Crear los siguientes indexes
 
   Tabla: appGate-dev-operand
   nombre del Indice: sessionId-index
   primary-key: sessionId

   Tabla: appGate-dev-operation
   nombre del Indice:customerId-index
   primary-key: customerId