package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.*;

@Path("exercises")
public class ExerciseGetService {

    @GET
    public List<Exercise> list() {
        List<Exercise> exercises = new ArrayList<>();

        String sql = "SELECT * FROM esercizi";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getLong("id"));
                exercise.setName(rs.getString("nome"));
                exercise.setDescription(rs.getString("descrizione"));
                exercise.setMuscleGroup(rs.getString("gruppo_muscolare"));
                exercises.add(exercise);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // puoi gestire meglio gli errori se vuoi
        }

        return exercises;
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        String sql = "SELECT * FROM esercizi WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Exercise exercise = new Exercise();
                    exercise.setId(rs.getLong("id"));
                    exercise.setName(rs.getString("nome"));
                    exercise.setDescription(rs.getString("descrizione"));
                    exercise.setMuscleGroup(rs.getString("gruppo_muscolare"));
                    return Response.ok(exercise).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                                   .entity("Esercizio non trovato con ID " + id)
                                   .build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Errore durante la lettura dal database: " + e.getMessage())
                           .build();
        }
    }
}
