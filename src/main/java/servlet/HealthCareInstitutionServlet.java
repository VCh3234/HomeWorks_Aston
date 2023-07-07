package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import model.HealthCareInstitution;
import service.HealthCareInstitutionService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/institution")
@Log4j2
public class HealthCareInstitutionServlet extends HttpServlet {

    private HealthCareInstitutionService healthCareInstitutionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public HealthCareInstitutionServlet() {
        healthCareInstitutionService = new HealthCareInstitutionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get institution");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        try {
            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                HealthCareInstitution healthCareInstitution = healthCareInstitutionService.getOneByIndex(id);
                objectMapper.writeValue(response.getWriter(), healthCareInstitution);
            } else {
                objectMapper.writeValue(response.getWriter(), healthCareInstitutionService.getAll());
            }
        } catch (SQLException e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Create new institution");
        try {
            HealthCareInstitution newHealthCareInstitution = objectMapper.readValue(req.getReader(), HealthCareInstitution.class);
            healthCareInstitutionService.addNew(newHealthCareInstitution);
            resp.setStatus(201);
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Update institution");
        try {
            HealthCareInstitution healthCareInstitution = objectMapper.readValue(req.getReader(), HealthCareInstitution.class);
            healthCareInstitutionService.update(healthCareInstitution);
            resp.setStatus(200);
        } catch (JsonProcessingException | SQLException e) {
            resp.sendError(400, e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Delete institution");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            healthCareInstitutionService.deleteOneById(id);
        } catch (SQLException e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}