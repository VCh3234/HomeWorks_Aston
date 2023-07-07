package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import model.Patient;
import service.PatientService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/patient")
@Log4j2
public class PatientServlet extends HttpServlet {

    private PatientService patientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public PatientServlet() {
        patientService = new PatientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get patient");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        try {
            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                Patient patient = patientService.getOneByIndex(id);
                objectMapper.writeValue(response.getWriter(), patient);
            } else {
                objectMapper.writeValue(response.getWriter(), patientService.getAll());
            }
        } catch (SQLException e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Create new patient");
        try {
            Patient patient = objectMapper.readValue(req.getReader(), Patient.class);
            patientService.addNew(patient);
            resp.setStatus(201);
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Update patient");
        try {
            Patient patient = objectMapper.readValue(req.getReader(), Patient.class);
            patientService.update(patient);
            resp.setStatus(200);
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete patient");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            patientService.deleteOneById(id);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}