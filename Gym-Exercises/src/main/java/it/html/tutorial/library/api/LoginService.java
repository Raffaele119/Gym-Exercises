package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.Base64;

@Path("login")
public class LoginService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Trainer credentials) {
        String sql = "SELECT * FROM trainer WHERE username = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, credentials.getUsername());
            stmt.setString(2, credentials.getPassword());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Trainer trainer = new Trainer();
                    trainer.setId(rs.getLong("id"));
                    trainer.setUsername(rs.getString("username"));
                    trainer.setPassword(null); // NON restituire password

                    // genera un "token" semplice codificato base64
                    String token = generateToken(trainer.getUsername());

                    // risposta con username e token
                    String json = String.format("{\"username\": \"%s\", \"token\": \"%s\"}",
                                                trainer.getUsername(), token);

                    return Response.ok(json, MediaType.APPLICATION_JSON).build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED)
                                   .entity("{\"error\": \"Credenziali non valide\"}")
                                   .build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"Errore nel database\"}")
                           .build();
        }
    }

    private String generateToken(String username) {
        // qui puoi mettere data + username, crittografia ecc.
        String raw = username + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }
}
