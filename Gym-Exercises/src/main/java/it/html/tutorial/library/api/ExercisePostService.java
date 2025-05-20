package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import java.util.Base64;

@Path("post")
public class ExercisePostService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@HeaderParam("Authorization") String authHeader, Exercise exercise) {
        // 1. Controlla se il token è presente
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"error\":\"Token mancante o malformato\"}")
                           .build();
        }

        // 2. Estrai il token
        String token = authHeader.substring("Bearer ".length()).trim();

        // 3. (FACOLTATIVO) Verifica se il token è valido (es: contiene "username:timestamp")
        if (!isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"error\":\"Token non valido\"}")
                           .build();
        }

        // 4. Procedi con l'inserimento
        String sql = "INSERT INTO esercizi (nome, descrizione, gruppo_muscolare) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getDescription());
            stmt.setString(3, exercise.getMuscleGroup());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("{\"error\":\"Errore nell'inserimento\"}").build();
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exercise.setId(generatedKeys.getLong(1));
                }
            }

            return Response.status(Response.Status.CREATED).entity(exercise).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"Errore SQL: " + e.getMessage() + "\"}")
                           .build();
        }
    }

    // Funzione base per validare il token
    private boolean isTokenValid(String token) {
        try {
            // Decode base64
            String decoded = new String(Base64.getDecoder().decode(token));
            // Esempio semplice: deve contenere "qualcosa:" (es. username:timestamp)
            return decoded.contains(":");
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
