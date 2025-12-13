import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Script simple para generar hash BCrypt de contraseñas
 * Compatible con el sistema Oasis
 * 
 * Uso:
 * 1. Compilar: javac -cp "ruta/a/spring-security-crypto.jar" GenerarHashPassword.java
 * 2. Ejecutar: java -cp ".:ruta/a/spring-security-crypto.jar" GenerarHashPassword
 * 
 * O mejor: Ejecutar desde el proyecto Spring Boot
 */
public class GenerarHashPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Contraseña del admin de seguridad
        String password = "Seguridad@2024Oasis!";
        String salt = "Aqm,24Dla";
        String passwordWithSalt = password + salt;
        
        // Generar hash
        String hash = encoder.encode(passwordWithSalt);
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       GENERADOR DE HASH BCrypt - OASIS                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Contraseña: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Texto completo: " + passwordWithSalt);
        System.out.println();
        System.out.println("Hash BCrypt generado:");
        System.out.println(hash);
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  Copia este hash y úsalo en datos_completo.sql           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}

