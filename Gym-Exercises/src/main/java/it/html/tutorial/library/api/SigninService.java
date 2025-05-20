package it.html.tutorial.library.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("signin")
public class SigninService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signin(Trainer newTrainer) {
        String sql = "INSERT INTO trainer (username, password) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // imposta i parametri
            stmt.setString(1, newTrainer.getUsername());
            stmt.setString(2, newTrainer.getPassword());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("Errore durante la registrazione.")
                               .build();
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newTrainer.setId(generatedKeys.getLong(1));
                }
            }

            return Response.status(Response.Status.CREATED).entity(newTrainer).build();

        } catch (SQLIntegrityConstraintViolationException e) {
            // caso in cui l'username sia già registrato (se UNIQUE)
            return Response.status(Response.Status.CONFLICT)
                           .entity("Username già registrato.")
                           .build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Errore nel database: " + e.getMessage())
                           .build();
        }
    }
}
