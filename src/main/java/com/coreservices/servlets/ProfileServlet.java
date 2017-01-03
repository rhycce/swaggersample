package com.coreservices.servlets;

import com.coreservices.Config;
import com.coreservices.Main;
import com.coreservices.dao.UserDao;
import com.coreservices.datatypes.UserDatatype;
import com.coreservices.utils.RpcLogTemplate;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 12/21/2016.
 */
@SwaggerDefinition(
        info = @Info(
                title = "User Profile Servlet",
                version = "1.0.0",
                description = "Servlet that handles basic CRUD operations to the user profile data source",
                contact = @Contact(name = "XYZ", email = "XYZ", url = "XYZ"),
                termsOfService = "XYZ",
                license = @License(name = "XYZ", url = "XYZ")
        ),
        basePath = "/",
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {@Tag(name = "users", description = "CRUD operations on user datatype")}
)
@Api(value = "/user", description = "performs CRUD operations on a user profile")
public class ProfileServlet extends HttpServlet {
    Logger logger = Logger.getLogger(ProfileServlet.class.getSimpleName());

    @ApiOperation(httpMethod = "GET", value = "Returns a [list of the] user profile datatype", notes = "", response = UserDatatype.class, nickname = "getUser", tags = ("User"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succssful retrieval of user profiles", response = UserDatatype.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "profile id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "firstname", value = "First name of user", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lastname", value = "Last name of user", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "phone number of user", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signup", value = "Sign up date of user, in dd-MM-yyyy format", required = false, dataType = "java.util.Date", paramType = "query")
    })
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RpcLogTemplate logTemplate = new RpcLogTemplate(req.getRemoteHost(),req.getParameter("client"), req.getParameter("clientapp"), Config.localhost, Config.SERVICE_INSTANCE, Config.SERVICE_APP, req.getParameterMap(), req.getRequestURI(),  new Date().getTime() );
        logger.debug("Received request: GET");
        logger.debug("Request URI: " + req.getRequestURI());
        logger.debug("Request servlet path: " + req.getServletPath());
        handleGet(req, resp, logTemplate);
        logTemplate.setResponseTimestamp(new Date().getTime());
        logTemplate.setResponseCode(Integer.toString(resp.getStatus()));
        Main.loggerService.addLog(logTemplate);
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response, RpcLogTemplate logTemplate) throws ServletException, IOException{
        Gson gson = new Gson();
        String param = null;
        param = request.getParameter("id");
        if(param!= null){
            logger.info("Query by ID received. All other params would be ignored");
            UserDatatype userDatatype = UserDao.INSTANCE.findById(param);
            if(userDatatype == null){
                response.setStatus(HttpServletResponse.SC_OK);
                logger.info("Null object returned");
                return;
            }else{
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter printWriter = response.getWriter();
                printWriter.write(gson.toJson(userDatatype, UserDatatype.class));
                printWriter.flush();
                printWriter.close();
            }
        }else{
            Map<String, String> queryString = new HashMap<>();
            //TODO: optimize this
            param = request.getParameter("firstname");
            if(param != null)
                queryString.put("firstname", param);
            param = request.getParameter("lastname");
            if(param != null)
                queryString.put("lastname", param);
            param = request.getParameter("phone");
            if(param != null)
                queryString.put("phone", param);
            param = request.getParameter("signup");
            if(param != null)
                queryString.put("signup", param);
            UserDatatype[] userDatatypes = UserDao.INSTANCE.findByParams(queryString);
            if(userDatatypes == null){
                response.setStatus(HttpServletResponse.SC_OK);
                logger.info("Null object returned");
                return;
            }else{
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter printWriter = response.getWriter();
                printWriter.write(gson.toJson(userDatatypes, UserDatatype[].class));
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
