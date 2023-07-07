package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import model.Doctor;
import service.PatientDoctorService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/patient-doctor")
@Log4j2
public class PatientDoctorServlet extends HttpServlet {

    private PatientDoctorService patientDoctorService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public PatientDoctorServlet() {
        patientDoctorService = new PatientDoctorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get all relations patient-doctor");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String patientId = request.getParameter("patientId");
        try {
            int id = Integer.parseInt(patientId);
            List<Doctor> patientList = patientDoctorService.getAllById(id);
            objectMapper.writeValue(response.getWriter(), patientList);
        } catch (SQLException e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Create patient-doctor relationship");
        int patientId = Integer.parseInt(req.getParameter("patientId"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        try {
            patientDoctorService.addRelation(patientId, doctorId);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(201);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete patient-doctor relationship");
        int patientId = Integer.parseInt(req.getParameter("patientId"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        try {
            patientDoctorService.deleteRelation(patientId, doctorId);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}
