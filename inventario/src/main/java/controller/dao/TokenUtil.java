package controller.dao;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 * Clase TokenUtil: Proporciona utilidades para generar y validar tokens JWT.
 * Los tokens se utilizan para la autenticación y autorización de usuarios.
 */
public class TokenUtil {

    private static final String SECRET_KEY = "my_secret_key";  // Clave secreta para firmar los tokens. Cambiar por una clave más segura en producción.

    /**
     * Genera un token JWT con información del usuario.
     * 
     * param idPersona El ID de la persona que se incluirá en el token.
     * param correo El correo electrónico del usuario que se usará como sujeto del token.
     * return Un token JWT firmado como cadena de texto.
     */
    public static String generateToken(Integer idPersona, String correo) {
        return Jwts.builder()
                .setSubject(correo) // Establece el correo como sujeto del token.
                .claim("idPersona", idPersona)  // Agrega una reclamación personalizada con el ID de la persona.
                .setIssuedAt(new Date())  // Fecha de emisión del token.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Fecha de expiración: 1 hora.
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Firma el token usando HS256 y la clave secreta.
                .compact();  // Compacta el token en una cadena de texto.
    }

    /**
     * Valida un token JWT verificando su firma y estructura.
     * 
     * param token El token JWT a validar.
     * return true si el token es válido, false en caso contrario.
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Establece la clave secreta para verificar el token.
                .parseClaimsJws(token);  // Verifica la firma y estructura del token.
            return true;  // El token es válido.
        } catch (Exception e) {
            return false;  // El token no es válido (firma incorrecta, expirado, etc.).
        }
    }
}
