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
import java.sql.SQLException;

@WebServlet("/doctor")
@Log4j2
public class DoctorServlet extends HttpServlet {

    private DoctorService doctorService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public DoctorServlet() {
        doctorService = new DoctorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get doctor");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        try {
            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                Doctor doctor = doctorService.getOneByIndex(id);
                objectMapper.writeValue(response.getWriter(), doctor);
            } else {
                objectMapper.writeValue(response.getWriter(), doctorService.getAll());
            }
        } catch (SQLException e) {
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
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Update doctor");
        try {
            Doctor doctor = objectMapper.readValue(req.getReader(), Doctor.class);
            doctorService.update(doctor);
            resp.setStatus(200);
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete doctor");
        int id = Integer.parseInt(req.getParameter("doctorId"));
        try {
            doctorService.deleteOneById(id);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}