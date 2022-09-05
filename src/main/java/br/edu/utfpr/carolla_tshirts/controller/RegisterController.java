package br.edu.utfpr.carolla_tshirts.controller;

import br.edu.utfpr.carolla_tshirts.util.Constants;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import br.edu.utfpr.carolla_tshirts.util.Constants;


@WebServlet(name = "RegisterServlet", value = "/cadastro")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("estado");
        if (param == null){
            request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
        }else {
            request.getRequestDispatcher("/WEB-INF/view/listing.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String descricao = request.getParameter("descricao");
        String cor = request.getParameter("cor");
        String tamanho = request.getParameter("tamanho");

        String descricaoT = descricao(descricao);
        request.setAttribute("flash.descricaoT", descricaoT);
        String corT = descricao(cor);
        request.setAttribute("flash.corT", corT);
        String tamanhoT = descricao(tamanho);
        request.setAttribute("flash.tamanhoT", tamanhoT);

        process(request, response);


        response.sendRedirect("cadastro?estado=sucesso");

    }

    private String descricao(String descricao){
        return descricao;
    }

    private String cor(String cor){
        return cor;
    }

    private String tamanho(String tamanho){
        return tamanho;
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Escopo de aplicaçao
        Integer counterApplication = (Integer) getServletContext().getAttribute(Constants.COUNTER_APPLICATION);
        if(counterApplication == null){
            counterApplication = 0;
        }
        counterApplication++;
        getServletContext().setAttribute(Constants.COUNTER_APPLICATION, counterApplication);

        //Escopo de sessao
        Integer counterSession = (Integer) request.getSession(true).getAttribute(Constants.COUNTER_SESSION);
        if(counterSession == null){
            counterSession = 0;
        }
        counterSession++;
        request.getSession(true).setAttribute(Constants.COUNTER_SESSION, counterSession);

        //Cookies
        Cookie cookie = new Cookie("counter", String.valueOf(counterSession));
        cookie.setMaxAge(60 * 60 * 24);// 1 dia

        //temporario
        Cookie cookies = new Cookie("counter", String.valueOf(counterSession));
            cookies.setMaxAge(-1);// fecha navegador, ele apaga

        response.addCookie(cookie);
    }
}
