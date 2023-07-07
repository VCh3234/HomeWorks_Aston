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
import model.PaidClinic;
import model.PublicClinic;
import service.HealthCareInstitutionService;

import java.io.IOException;
import java.util.List;

@WebServlet("/institution")
@Log4j2
public class HealthCareInstitutionServlet extends HttpServlet {

    private final HealthCareInstitutionService healthCareInstitutionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public HealthCareInstitutionServlet() {
        healthCareInstitutionService = new HealthCareInstitutionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get institution");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("institution_id");
        String institutionTypeParameter = request.getParameter("institution_type");
        String isShowDoctors = request.getParameter("show_doctors");

        try {
            HealthCareInstitution healthCareInstitution;

            if (idParameter != null) {
                int id = Integer.parseInt(idParameter);
                if (isShowDoctors != null && isShowDoctors.equals("true")) {
                    healthCareInstitution = healthCareInstitutionService.getOneByIndexWithDoctor(id);
                } else {
                    healthCareInstitution = healthCareInstitutionService.getOneByIndex(id);
                }
                objectMapper.writeValue(response.getWriter(), healthCareInstitution);
            } else {
                if (institutionTypeParameter != null && institutionTypeParameter.equals("paid")) {
                    List<PaidClinic> list;
                    list = healthCareInstitutionService.getAllPaidClinic();
                    objectMapper.writeValue(response.getWriter(), list);
                } else if (institutionTypeParameter != null && institutionTypeParameter.equals("public")) {
                    List<PublicClinic> list;
                    list = healthCareInstitutionService.getAllPublicClinic();
                    objectMapper.writeValue(response.getWriter(), list);
                } else {
                    List<HealthCareInstitution> list;
                    list = healthCareInstitutionService.getAll();
                    objectMapper.writeValue(response.getWriter(), list);
                }
            }
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Create new institution");
        String typeOfInstitution = req.getParameter("institution_type");
        try {
            if (typeOfInstitution.equals("paid")) {
                HealthCareInstitution newHealthCareInstitution = objectMapper.readValue(req.getReader(), PaidClinic.class);
                healthCareInstitutionService.addNew(newHealthCareInstitution);
            } else if (typeOfInstitution.equals("public")) {
                HealthCareInstitution newHealthCareInstitution = objectMapper.readValue(req.getReader(), PublicClinic.class);
                healthCareInstitutionService.addNew(newHealthCareInstitution);
            } else {
                resp.sendError(400, "Wrong parameter institution_type");
            }
            resp.setStatus(201);
        } catch (JsonProcessingException e) {
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
        } catch (JsonProcessingException e) {
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
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
        resp.setStatus(204);
    }
}