/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.parameter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.UsuarioBean;
import net.daw.helper.Contexto;
import net.daw.helper.FilterBean;

/**
 *
 * @author rafa
 */
public class ContextParam {

    private final HttpServletRequest request;

    public ContextParam(HttpServletRequest request) throws Exception {
        this.request = request;
    }

    public Contexto loadSession(Contexto oContexto) {
        oContexto.setHaySesion(request.getSession().getAttribute("usuarioBean") != null);
        if (oContexto.getHaySesion()) {
            oContexto.setUserBeanSession((UsuarioBean) request.getSession().getAttribute("usuarioBean"));
        } else {
            oContexto.setUserBeanSession(null);
        }
        return oContexto;
    }

    public Contexto load(Contexto oContexto) throws NumberFormatException {
        try {
            HashMap<String, String> parameterNames = new HashMap<>();
            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String parameterName = (String) enumeration.nextElement();
                parameterNames.put(parameterName, request.getParameter(parameterName));
            }
            oContexto.setParameters(parameterNames);

            if (!oContexto.getHaySesion()) {
                oContexto.setClase("usuario");
                if (!"login".equals(request.getParameter("method"))) {
                    oContexto.setMetodo("ocioso");
                }
            }

            if (request.getParameter("filter") == null) {
                oContexto.setAlFilter(null);
            } else {
                if (request.getParameter("filteroperator") == null) {
                    oContexto.setAlFilter(null);
                } else {
                    if (request.getParameter("filtervalue") == null) {
                        oContexto.setAlFilter(null);
                    } else {
                        ArrayList<FilterBean> alFilter = new ArrayList<>();
                        FilterBean oFilterBean = new FilterBean();
                        oFilterBean.setFilter(request.getParameter("filter"));
                        oFilterBean.setFilterOperator(request.getParameter("filteroperator"));
                        oFilterBean.setFilterValue(request.getParameter("filtervalue"));
                        oFilterBean.setFilterOrigin("user");
                        alFilter.add(oFilterBean);
                        oContexto.setAlFilter(alFilter);
                    }
                }
            }

            if (request.getParameter("systemfilter") != null) {
                if (request.getParameter("systemfilteroperator") != null) {
                    if (request.getParameter("systemfiltervalue") != null) {
                        ArrayList<FilterBean> alFilter = oContexto.getAlFilter();
                        FilterBean oFilterBean = new FilterBean();
                        oFilterBean.setFilter(request.getParameter("systemfilter"));
                        oFilterBean.setFilterOperator(request.getParameter("systemfilteroperator"));
                        oFilterBean.setFilterValue(request.getParameter("systemfiltervalue"));
                        oFilterBean.setFilterOrigin("system");
                        alFilter.add(oFilterBean);
                    }
                }
            }

            if (request.getParameter("order") == null) {
                oContexto.setHmOrder(null);
            } else {
                if (request.getParameter("ordervalue") == null) {
                    oContexto.setHmOrder(null);
                } else {
                    HashMap<String, String> hmOrder = new HashMap<>();
                    hmOrder.put(request.getParameter("order"), request.getParameter("ordervalue"));
                    oContexto.setHmOrder(hmOrder);
                }
            }

        } catch (NumberFormatException e) {
            throw new NumberFormatException("Controller: Error: load: Formato de datos en parámetros incorrecto " + e.getMessage());
        }
        return oContexto;
    }
}
