package edsh.weblab3.bean;

import edsh.weblab3.db.DatabaseHelper;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

@Named
@SessionScoped
public class ResultsBean implements Serializable {
    private LinkedList<CheckHitBean> results;
    @Inject
    InputBean input;

    public void newResult() {
        initializeResults();
        CheckHitBean newHit = CheckHitBean.makeCheckHit(input);
        results.add(newHit);
        DatabaseHelper.get().addResult(getCurrentUuid(), newHit);
    }

    public void clearResults() {
        initializeResults();
        results.clear();
        DatabaseHelper.get().removeAllResults(getCurrentUuid());

    }

    public void drawResults() {
        initializeResults();
        for(CheckHitBean res : results) {
            res.drawPoint();
        }
    }

    public LinkedList<CheckHitBean> getResults() {
        initializeResults();
        return results;
    }

    private void initializeResults() {
        if(results != null) return;
        String uuid = getCurrentUuid();
        results = DatabaseHelper.get().getAllResults(uuid);
    }

    private String getCurrentUuid() {
        var context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookies = context.getRequestCookieMap();
        Cookie cookie = (Cookie) cookies.get("uuid");
        if(cookie == null) {
            cookie = new Cookie("uuid", UUID.randomUUID().toString());
            Map<String, Object> properties = new HashMap<>();
            properties.put("maxAge", 31536000);
            properties.put("path", "/");
            context.addResponseCookie("uuid", cookie.getValue(), properties);
        }
        return cookie.getValue();
    }

    public void setResults(LinkedList<CheckHitBean> results) {
        this.results = results;
    }
}
