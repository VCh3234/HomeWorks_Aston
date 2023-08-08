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

@WebServlet("/patient")
@Log4j2
public class PatientServlet extends HttpServlet {

    private final PatientService patientService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PatientServlet() {
        patientService = new PatientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get patient");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        String doctorParameter = request.getParameter("show_doctor");
        try {
            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                Patient patient;
                if (doctorParameter != null && doctorParameter.equals("true")) {
                    patient = patientService.getOneByIndexWithDoctor(id);
                } else {
                    patient = patientService.getOneByIndex(id);
                }
                objectMapper.writeValue(response.getWriter(), patient);
            } else {
                objectMapper.writeValue(response.getWriter(), patientService.getAll());
            }
        } catch (Exception e) {
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
        } catch (JsonProcessingException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Update patient");
        String doctorIdParam = request.getParameter("doctorId");
        String patientIdParam = request.getParameter("patientId");
        try {
            if (doctorIdParam != null && patientIdParam != null) {
                int doctorId = Integer.parseInt(doctorIdParam);
                int patientId = Integer.parseInt(patientIdParam);
                patientService.addDoctor(patientId, doctorId);
            } else {
                Patient patient = objectMapper.readValue(request.getReader(), Patient.class);
                patientService.update(patient);
                resp.setStatus(200);
            }
        } catch (JsonProcessingException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete patient");
        String doctorIdParam = req.getParameter("doctorId");
        String patientIdParam = req.getParameter("patientId");
        int patientId = Integer.parseInt(patientIdParam);
        try {
            if (doctorIdParam != null) {
                int doctorId = Integer.parseInt(doctorIdParam);
                patientService.deleteDoctor(patientId, doctorId);
            } else {
                patientService.deleteOneById(patientId);
            }
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}