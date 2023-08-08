package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import model.Doctor;
import service.DoctorService;

import java.io.IOException;

@WebServlet("/doctor")
@Log4j2
public class DoctorServlet extends HttpServlet {

    private final DoctorService doctorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DoctorServlet() {
        doctorService = new DoctorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get doctor");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("doctor_id");
        String patientParameter = request.getParameter("show_patient");

        try {
            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                Doctor doctor;
                if (patientParameter != null && patientParameter.equals("true")) {
                    doctor = doctorService.getOneByIndexWithPatient(id);
                } else {
                    doctor = doctorService.getOneByIndex(id);
                }
                objectMapper.writeValue(response.getWriter(), doctor);
            } else {
                objectMapper.writeValue(response.getWriter(), doctorService.getAll());
            }
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Create new doctor");
        try {
            Doctor doctor = objectMapper.readValue(req.getReader(), Doctor.class);
            doctorService.addNew(doctor);
            resp.setStatus(201);
        } catch (JsonProcessingException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Update doctor");
        String institutionId = req.getParameter("institution_id");
        try {
            Doctor doctor = objectMapper.readValue(req.getReader(), Doctor.class);
            if (institutionId != null) {
                doctorService.changeRelationInstitution(doctor.getId(), Integer.parseInt(institutionId));
            } else {
                doctorService.update(doctor);
            }
            resp.setStatus(200);
        } catch (JsonProcessingException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete doctor");
        int id = Integer.parseInt(req.getParameter("doctor_id"));
        String institutionDelete = req.getParameter("institution_delete");

        try {
            if (institutionDelete != null && institutionDelete.equals("true")) {
                doctorService.deleteRelationWithInstitution(id);
            } else {
                doctorService.deleteOneById(id);
            }
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}