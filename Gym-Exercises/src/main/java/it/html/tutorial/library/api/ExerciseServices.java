package it.html.tutorial.library.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("exercises")
public class ExerciseServices {

    private Exercise mapExercise(ResultSet rs) throws SQLException {
        Exercise exercise = new Exercise();
        exercise.setId(rs.getLong("id"));
        exercise.setName(rs.getString("nome"));
        exercise.setDescription(rs.getString("descrizione"));
        exercise.setMuscleGroup(rs.getString("gruppo_muscolare"));
        return exercise;
    }

    @GET
    public List<Exercise> list() {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM esercizi")) {

            while (rs.next()) {
                exercises.add(mapExercise(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        Exercise exercise = null;
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM esercizi WHERE id = ?")) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                exercise = mapExercise(rs);
                return Response.ok(exercise).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    public Response add(Exercise exercise) throws URISyntaxException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO esercizi (nome, descrizione, gruppo_muscolare) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getDescription());
            stmt.setString(3, exercise.getMuscleGroup());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);
                    exercise.setId(generatedId);
                    return Response.created(new URI("api/exercises/" + generatedId)).build();
                }
            }
            return Response.status(Response.Status.BAD_REQUEST).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, Exercise updatedExercise) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE esercizi SET nome = ?, descrizione = ?, gruppo_muscolare = ? WHERE id = ?")) {

            stmt.setString(1, updatedExercise.getName());
            stmt.setString(2, updatedExercise.getDescription());
            stmt.setString(3, updatedExercise.getMuscleGroup());
            stmt.setLong(4, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM esercizi WHERE id = ?")) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
