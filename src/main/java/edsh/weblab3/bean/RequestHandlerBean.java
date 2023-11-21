package edsh.weblab3.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Named
@SessionScoped
public class RequestHandlerBean implements Serializable {
    @Inject
    private ResultsBean results;

    public void checkHit() {
        results.newResult();
    }

    public void clear() {
        results.clearResults();
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                .add("clearPoints();");
    }

    public void draw() {
        results.drawResults();
    }

    public void requestTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("+00:00"));
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                .add("syncTime(" + now + ");");
    }
}
