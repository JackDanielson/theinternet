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

    public record Credential(String username, String password) {}

    private record CredentialSets(
            Credential valid,
            Credential invalid_username,
            Credential invalid_password,
            Credential no_username,
            Credential no_password,
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
    public static Credential noUsername()    { return sets.no_username; }
    public static Credential noPassword()    { return sets.no_password; }
    public static Credential empty()              { return sets.empty; }

}