package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import model.Doctor;
import service.InstitutionDoctorService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/institution-doctor")
@Log4j2
public class InstitutionDoctorServlet extends HttpServlet {

    private InstitutionDoctorService institutionDoctorService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public InstitutionDoctorServlet() {
        institutionDoctorService = new InstitutionDoctorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get doctors");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        try {
            int id = Integer.parseInt(idParameter);
            List<Doctor> doctors = institutionDoctorService.getAllById(id);
            objectMapper.writeValue(response.getWriter(), doctors);

        } catch (SQLException e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Create new relation");
        int institutionId = Integer.parseInt(req.getParameter("institutionId"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        try {
            institutionDoctorService.saveNewRelationship(institutionId, doctorId);
            resp.setStatus(201);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete relation");
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        try {
            institutionDoctorService.deleteRelation(doctorId);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}
