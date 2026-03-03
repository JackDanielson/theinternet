// ────────────────────────────────────────────────
// utils/Credentials.java
// ────────────────────────────────────────────────
package utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Credentials {

    private static final String FILE_PATH = "src/test/resources/credentials.json";

    // Record interno para cada conjunto de credenciales
    public record Credential(String username, String password) {}

    // Clase que mapea la estructura completa del JSON
    private record CredentialSets(
            Credential valid,
            Credential invalid_username,
            Credential invalid_password,
            Credential empty
    ) {}

    private static CredentialSets sets;

    static {
        try {
            String json = Files.readString(Paths.get(FILE_PATH));
            sets = new Gson().fromJson(json, CredentialSets.class);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer credentials.json", e);
        }
    }

    public static Credential valid()              { return sets.valid; }
    public static Credential invalidUsername()    { return sets.invalid_username; }
    public static Credential invalidPassword()    { return sets.invalid_password; }
    public static Credential empty()              { return sets.empty; }
}