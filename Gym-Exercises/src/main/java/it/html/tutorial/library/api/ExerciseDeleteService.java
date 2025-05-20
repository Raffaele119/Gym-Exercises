package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("delete")
public class ExerciseDeleteService {

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        String sql = "DELETE FROM esercizi WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
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
                           .entity("Errore SQL: " + e.getMessage())
                           .build();
        }
    }
}
