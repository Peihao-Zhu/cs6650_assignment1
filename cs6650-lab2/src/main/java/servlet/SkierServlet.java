package servlet;

import com.google.gson.Gson;
import servlet.VO.ErrorVO;
import servlet.VO.ResortVerticalVO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "SkierServlet", value = "/skiers")
public class SkierServlet extends HttpServlet {

    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();
        if(Validation.checkSkiersVerticalForSpecificDayGet(urlPath) ) {
            // todo process /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}


        } else if(Validation.checkSkiersVerticalGet(urlPath, req)) {
            // todo process  /skiers/{skierID}/vertical
            String json = gson.toJson(new ResortVerticalVO());
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);

        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write(gson.toJson(ErrorVO.builder().message("missing paramterers").build()));
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get the request body
        String body = GetPostBody.getPostBody(req);

        String urlPath = req.getPathInfo();

        // check we have a URL!
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)
        if(!Validation.checkSkiersNewLiftPost(urlPath)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write(gson.toJson(ErrorVO.builder().message("missing paramterers").build()));
            return;
        } else {
            res.setStatus(HttpServletResponse.SC_CREATED);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`


            // res.getWriter().write(body);
        }

    }
}
