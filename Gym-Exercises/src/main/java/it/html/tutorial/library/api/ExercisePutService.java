package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("put")
public class ExercisePutService {

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, Exercise updatedExercise) {
        String sql = "UPDATE esercizi SET nome = ?, descrizione = ?, gruppo_muscolare = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedExercise.getName());
            stmt.setString(2, updatedExercise.getDescription());
            stmt.setString(3, updatedExercise.getMuscleGroup());
            stmt.setLong(4, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Nessun esercizio trovato con ID " + id)
                               .build();
            }

            return Response.noContent().build(); // HTTP 204 No Content

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Errore durante l'aggiornamento: " + e.getMessage())
                           .build();
        }
    }
}
