# Fix: Error de Parámetros en Spring

## Problema

El error `IllegalArgumentException: Name for argument of type [java.lang.String] not specified` ocurría porque Spring no podía determinar el nombre del parámetro en el endpoint `/mail/send/{mail}`.

## Solución Aplicada

1. **Corregido el MailController**: Se agregó el nombre explícito al `@PathVariable`:
   ```java
   @PostMapping("/send/{mail}")
   public ResponseDTO sendMail(@PathVariable("mail") String mail, @RequestBody MailStructure mailStructure)
   ```

2. **Verificado build.gradle**: Ya tiene el flag `-parameters` configurado:
   ```gradle
   tasks.withType(JavaCompile) {
       options.compilerArgs << "-parameters"
   }
   ```

## Prevención Futura

Para evitar este problema en otros endpoints, se recomienda:

1. **Usar nombres explícitos en @PathVariable** (recomendado):
   ```java
   @GetMapping("/{id}")
   public ResponseDTO getById(@PathVariable("id") Long id)
   ```

2. **O asegurarse de que el flag -parameters esté activo** (ya configurado en build.gradle)

## Nota

El flag `-parameters` en `build.gradle` debería funcionar para todos los endpoints, pero usar nombres explícitos es más seguro y no depende de la configuración del compilador.

